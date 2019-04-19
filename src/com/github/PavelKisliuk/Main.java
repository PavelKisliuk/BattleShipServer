package com.github.PavelKisliuk;


import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Cell;
import com.github.PavelKisliuk.model.logic.GameVsComputer;
import com.github.PavelKisliuk.util.RandomAreaArranger;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static final boolean isTest = true;

	private static final Logger logger;

	static {
		logger = Logger.getLogger(GameVsComputer.class);
	}

	public static void main(String[] args) {
		if (isTest) {
			int port = 2101;
			Area areaServer = new Area();
			Area areaPlayer = new Area();
			GameVsComputer game = new GameVsComputer();
			RandomAreaArranger.INSTANCE.arrangeRandomArea(areaServer);
			try (ServerSocket server = new ServerSocket(port)) {
				Socket player = server.accept();
				ObjectOutputStream output = new ObjectOutputStream(player.getOutputStream());
				output.flush();
				ObjectInputStream input = new ObjectInputStream(player.getInputStream());

				areaPlayer = (Area) input.readObject();
				output.writeObject(areaServer);

				output.writeObject(Boolean.TRUE);

				while (true) {
					while(true) {
						input.readObject();
						input.readObject();
						if(!(Boolean)input.readObject()) {
							break;
						}
					}

					while(true) {
						Random random = new SecureRandom();
						List<Cell> boundList = scan(areaPlayer);
						Cell randomCell = boundList.get(random.nextInt(boundList.size()));
						int row = randomCell.getI();
						int column = randomCell.getJ();
						game.playerGo(areaPlayer, row, column);
						output.writeObject(row);
						output.writeObject(column);
						if(!(Boolean)input.readObject()) {
							break;
						}
					}
				}

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<Cell> scan(Area area) {
		List<Cell> cellsList = new ArrayList<>();
		List<Cell> huntinglist = new ArrayList<>();


		for (int i = 0; i < area.length(); i++) {
			for (int j = 0; j < area.width(); j++) {
				if (area.getCell(i, j) == Area.CellsType.BEATEN) {
					logger.debug("Scan: beaten cell found (" + i + ", " + j + "), creating hunting list");

					if (i < Area.AREA_SIZE - 2 && area.getCell(i + 1, j) == Area.CellsType.BEATEN
							&& area.getCell(i + 2, j) == Area.CellsType.BEATEN) {
						if (i > 0 && area.getCell(i - 1, j) != Area.CellsType.MISS
								&& area.getCell(i - 1, j) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i - 1, j, area.getCell(i - 1, j)));
						}
						if (i < Area.AREA_SIZE - 3 && area.getCell(i + 3, j) != Area.CellsType.MISS
								&& area.getCell(i + 3, j) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i + 3, j, area.getCell(i + 3, j)));
						}

					} else if (j < Area.AREA_SIZE - 2 && area.getCell(i, j + 1) == Area.CellsType.BEATEN
							&& area.getCell(i, j + 2) == Area.CellsType.BEATEN) {
						if (j > 0 && area.getCell(i, j - 1) != Area.CellsType.MISS
								&& area.getCell(i, j - 1) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i, j - 1, area.getCell(i, j - 1)));
						}
						if (j < Area.AREA_SIZE - 3 && area.getCell(i, j + 3) != Area.CellsType.MISS
								&& area.getCell(i, j + 3) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i, j + 3, area.getCell(i, j + 3)));
						}


					} else if (i < Area.AREA_SIZE - 1 && area.getCell(i + 1, j) == Area.CellsType.BEATEN) {
						if (i > 0 && area.getCell(i - 1, j) != Area.CellsType.MISS
								&& area.getCell(i - 1, j) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i - 1, j, area.getCell(i - 1, j)));
						}
						if (i < Area.AREA_SIZE - 2 && area.getCell(i + 2, j) != Area.CellsType.MISS
								&& area.getCell(i + 2, j) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i + 2, j, area.getCell(i + 2, j)));
						}

					} else if (j < Area.AREA_SIZE - 1 && area.getCell(i, j + 1) == Area.CellsType.BEATEN) {
						if (j > 0 && area.getCell(i, j - 1) != Area.CellsType.MISS
								&& area.getCell(i, j - 1) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i, j - 1, area.getCell(i, j - 1)));
						}
						if (j < Area.AREA_SIZE - 2 && area.getCell(i, j + 2) != Area.CellsType.MISS
								&& area.getCell(i, j + 2) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i, j + 2, area.getCell(i, j + 2)));
						}


					} else {

						if (i < Area.AREA_SIZE - 1 && area.getCell(i + 1, j) != Area.CellsType.BEATEN
								&& area.getCell(i + 1, j) != Area.CellsType.KILLED
								&& area.getCell(i + 1, j) != Area.CellsType.MISS
								&& area.getCell(i + 1, j) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i + 1, j, area.getCell(i + 1, j)));
						}

						if (i > 0 && area.getCell(i - 1, j) != Area.CellsType.BEATEN
								&& area.getCell(i - 1, j) != Area.CellsType.KILLED
								&& area.getCell(i - 1, j) != Area.CellsType.MISS
								&& area.getCell(i - 1, j) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i - 1, j, area.getCell(i - 1, j)));
						}

						if (j < Area.AREA_SIZE - 1 && area.getCell(i, j + 1) != Area.CellsType.BEATEN
								&& area.getCell(i, j + 1) != Area.CellsType.KILLED
								&& area.getCell(i, j + 1) != Area.CellsType.MISS
								&& area.getCell(i, j + 1) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i, j + 1, area.getCell(i, j + 1)));
						}

						if (j > 0 && area.getCell(i, j - 1) != Area.CellsType.BEATEN
								&& area.getCell(i, j - 1) != Area.CellsType.KILLED
								&& area.getCell(i, j - 1) != Area.CellsType.MISS
								&& area.getCell(i, j - 1) != Area.CellsType.LAST) {
							huntinglist.add(new Cell(i, j - 1, area.getCell(i, j - 1)));
						}
					}

					logger.debug("hunting list: " + huntinglist);
					return huntinglist;
				}

				if (area.getCell(i, j) != Area.CellsType.BEATEN && area.getCell(i, j) != Area.CellsType.KILLED
						&& area.getCell(i, j) != Area.CellsType.MISS && area.getCell(i, j) != Area.CellsType.NEIGHBOR
						&& area.getCell(i, j) != Area.CellsType.LAST) {
					cellsList.add(new Cell(i, j, area.getCell(i, j)));
				}
			}
		}
		logger.debug("cells list: " + cellsList);
		return cellsList;
	}
}
