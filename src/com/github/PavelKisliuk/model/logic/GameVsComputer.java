package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Cell;
import com.github.PavelKisliuk.util.RandomAreaArranger;
import org.apache.log4j.Logger;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameVsComputer extends AbstractGame{

    private static final Logger logger;

    static {
        logger = Logger.getLogger(GameVsComputer.class);
    }

    @Override
    public Area getOpponentArea() {
        Area area = new Area();
        boolean isRightArea = RandomAreaArranger.INSTANCE.arrangeRandomArea(area);
        while (!isRightArea) {
            isRightArea = RandomAreaArranger.INSTANCE.arrangeRandomArea(area);
        }
        return area;
    }

    @Override
    public boolean playerGoFirst() {
        SecureRandom r = new SecureRandom();
        return r.nextBoolean();
    }

    @Override
    public boolean opponentGo(Area area) {
        Random random = new SecureRandom();

        List<Cell> boundList = scan(area);

        Cell randomCell = boundList.get(random.nextInt(boundList.size()));

        return playerGo(area, randomCell.getI(), randomCell.getJ());
    }


    private List<Cell> scan(Area area) {
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