package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.AreaArrangementException;
import org.apache.log4j.Logger;

/**
 * @author dzmitryplatonov on 2019-04-05.
 * @version 0.0.1
 */
public enum AreaArranger {

	INSTANCE;

	private static final Logger logger;

	static {
		logger = Logger.getLogger(AreaArranger.class);
	}

	public void arrangeFewShips(Area area, Ship[] ships) {
		if (area == null || ships == null) {
			throw new AreaArrangementException("Parameter is null");
		}
		for (Ship ship : ships) {
			if (isSuited(area, ship)) {
				arrangeShip(area, ship);
				arrangeNeighbors(area, Area.CellsType.SHIP);
			} else {
				logger.debug(ship + " not arranged");
			}
		}
		logger.debug(area.getShips().length + " ships arranged successfully on \n" + area);
	}

	public void arrangeShip(Area area, Ship ship) {
		if (area == null || ship == null) {
			throw new AreaArrangementException("Parameter is null");
		}
		if (isSuited(area, ship)) {
			area.addShip(ship);
			logger.debug(ship + " arranged successfully");
		}
		arrangeNeighbors(area, Area.CellsType.SHIP);
	}

	public void changeCelltype(Area area, Area.CellsType cellsTypeOut, Area.CellsType cellsTypeIn) {
		for (int i = 0; i < area.length(); i++) {
			for (int j = 0; j < area.width(); j++) {
				if (area.getCell(i, j) == cellsTypeOut) {
					area.setCell(i, j, cellsTypeIn);
					//logger.debug("(" + i + ", " + j + ")" + " changed to " + cellsTypeIn);
				}
			}
		}
		logger.debug(Area.CellsType.NEIGHBOR + "'s changed to " + Area.CellsType.EMPTY + "'s");
	}

	private boolean isSuited(Area area, Ship ship) {
		boolean result = true;
		for (int i = 0; i < ship.getHealth(); i++) {
			if (area.getCell(ship.getRow()[i], ship.getColumn()[i]) == Area.CellsType.EMPTY) {
				result = true;
			} else {
				result = false;
				logger.debug(ship + " not arranged, cell [" + ship.getRow()[i] + ", " + ship.getColumn()[i]
						+ "] isn't empty (" + area.getCell(ship.getRow()[i], ship.getColumn()[i]).toString() + ")");
				break;
			}
		}
		return result;
	}


	public void arrangeNeighbors(Area area, Area.CellsType contactCellsType) {
		for (int i = 0; i < area.length(); i++) {
			for (int j = 0; j < area.width(); j++) {
				if (isCellContactWithCellType(area, i, j, contactCellsType) && area.getCell(i, j) != Area.CellsType.SHIP) {
					area.setCell(i, j, Area.CellsType.NEIGHBOR);
				}
			}
		}
	}

	public boolean isCellContactWithCellType(Area area, int i, int j, Area.CellsType cellsType) {
		boolean result = false;
		if (i > 0 && i < area.length() - 1 && j > 0 && j < area.width() - 1
				&& (checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType))) {
			result = true;
		}
		if (i == 0 && j > 0 && j < area.width() - 1 && ((checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType)))) {
			result = true;
		}
		if (i == area.length() - 1 && j > 0 && j < area.width() - 1 && (checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == 0 && i > 0 && i < area.length() - 1 && (checkSouth(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == area.width() - 1 && i > 0 && i < area.length() - 1 && (checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType))) {
			result = true;
		}
		if (j == 0 && i == 0 && (checkSouth(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType)
				|| checkSouthEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == 0 && i == area.length() - 1 && (checkNorth(area, i, j, cellsType)
				|| checkNorthEast(area, i, j, cellsType)
				|| checkEast(area, i, j, cellsType))) {
			result = true;
		}
		if (j == area.width() - 1 && i == 0 && (checkSouth(area, i, j, cellsType)
				|| checkSouthWest(area, i, j, cellsType)
				|| checkWest(area, i, j, cellsType))) {
			result = true;
		}
		if (j == area.width() - 1 && i == area.length() - 1 && (checkWest(area, i, j, cellsType)
				|| checkNorthWest(area, i, j, cellsType)
				|| checkNorth(area, i, j, cellsType))) {
			result = true;
		}
		return result;
	}

	private boolean checkSouth(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i + 1, j) == cellsType;
	}

	private boolean checkSouthWest(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i + 1, j - 1) == cellsType;
	}

	private boolean checkWest(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i, j - 1) == cellsType;
	}

	private boolean checkNorthWest(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i - 1, j - 1) == cellsType;
	}

	private boolean checkNorth(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i - 1, j) == cellsType;
	}

	private boolean checkNorthEast(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i - 1, j + 1) == cellsType;
	}

	private boolean checkEast(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i, j + 1) == cellsType;
	}

	private boolean checkSouthEast(Area area, int i, int j, Area.CellsType cellsType) {
		return area.getCell(i + 1, j + 1) == cellsType;
	}


}
