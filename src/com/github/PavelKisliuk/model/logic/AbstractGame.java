package com.github.PavelKisliuk.model.logic;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.AreaArranger;
import org.apache.log4j.Logger;

public abstract class AbstractGame {

    private static final Logger logger;

    static {
        logger = Logger.getLogger(AbstractGame.class);
    }

    public abstract Area getOpponentArea();

    public abstract boolean playerGoFirst();

    public abstract boolean opponentGo(Area area);

    public boolean playerGo(Area area, int row, int column) {
        boolean result = false;
        AreaArranger arranger = AreaArranger.INSTANCE;

        for (int i = 0; i < area.length(); i++) {
            for (int j = 0; j < area.width(); j++) {
                if (area.getCell(i, j) == Area.CellsType.LAST) {
                    area.setCell(i, j, Area.CellsType.MISS);
                }
            }
        }

        shoot(area, row, column);

        if (area.getCell(row, column) == Area.CellsType.BEATEN) {
            ringBeatenDiagonalsWithMiss(area);
            for (Ship ship : area.getShips()) {
                if (isKilled(area, ship)) {
                    for (int i = 0; i < ship.getHealth(); i++) {
                        area.setCell(ship.getRow()[i], ship.getColumn()[i], Area.CellsType.KILLED);
                    }
                }
            }
            result = true;
        }

        for (int i = 0; i < area.length(); i++) {
            for (int j = 0; j < area.width(); j++) {
                if (arranger.isCellContactWithCellType(area, i, j, Area.CellsType.KILLED)
                        && area.getCell(i, j) != Area.CellsType.KILLED) {
                    area.setCell(i, j, Area.CellsType.MISS);
                }
            }
        }

        return result;
    }

    public boolean isWin(Area area) {
        int counterKilled = 20;

        for (int i = 0; i < area.length(); i++) {
            for (int j = 0; j < area.width(); j++) {
                if (area.getCell(i, j) == Area.CellsType.KILLED) {
                    counterKilled--;
                }
            }
        }
        return counterKilled == 0;
    }

    private void shoot(Area area, int i, int j) {
        if (area.getCell(i, j) == Area.CellsType.EMPTY) {
            logger.debug("shoot in cell (" + i + ", " + j + ") LAST_MISSED");
            area.setCell(i, j, Area.CellsType.LAST);
        } else if (area.getCell(i, j) == Area.CellsType.SHIP) {
            logger.debug("shoot in cell (" + i + ", " + j + ") BEATEN");
            area.setCell(i, j, Area.CellsType.BEATEN);
        }
    }

    private boolean isKilled(Area area, Ship ship) {
        boolean result = true;
        for (int i = 0; i < ship.getRow().length; i++) {
            if (area.getCell(ship.getRow()[i], ship.getColumn()[i]) == Area.CellsType.BEATEN) {
                //logger.debug("cell (" + ship.getRow()[i] + ", " + ship.getColumn()[i] + ") beaten");
                result = true;
            } else {
                //logger.debug("cell (" + ship.getRow()[i] + ", " + ship.getColumn()[i] + ") not Beaten");
                result = false;
                break;
            }
        }
        if (result) {
            logger.debug(ship + " KILLED");
        }
        return result;
    }

    private void ringBeatenDiagonalsWithMiss(Area area) {
        for (int i = 0; i < area.length(); i++) {
            for (int j = 0; j < area.width(); j++) {
                if (area.getCell(i, j) == Area.CellsType.BEATEN) {
                    if (i > 0 && i < Area.AREA_SIZE - 1 && j > 0 && j < Area.AREA_SIZE - 1) {
                        area.setCell(i + 1, j + 1, Area.CellsType.MISS);
                        area.setCell(i + 1, j - 1, Area.CellsType.MISS);
                        area.setCell(i - 1, j - 1, Area.CellsType.MISS);
                        area.setCell(i - 1, j + 1, Area.CellsType.MISS);
                    }

                    if (i == 0 && j > 0 && j < Area.AREA_SIZE - 1) {
                        area.setCell(i + 1, j + 1, Area.CellsType.MISS);
                        area.setCell(i + 1, j - 1, Area.CellsType.MISS);
                    }

                    if (i == Area.AREA_SIZE - 1 && j > 0 && j < Area.AREA_SIZE - 1) {
                        area.setCell(i - 1, j - 1, Area.CellsType.MISS);
                        area.setCell(i - 1, j + 1, Area.CellsType.MISS);
                    }

                    if (i > 0 && i < Area.AREA_SIZE - 1 && j == 0) {
                        area.setCell(i + 1, j + 1, Area.CellsType.MISS);
                        area.setCell(i - 1, j + 1, Area.CellsType.MISS);
                    }

                    if (i > 0 && i < Area.AREA_SIZE - 1 && j == Area.AREA_SIZE - 1) {
                        area.setCell(i + 1, j - 1, Area.CellsType.MISS);
                        area.setCell(i - 1, j - 1, Area.CellsType.MISS);
                    }

                    if (i == 0 && j == 0) {
                        area.setCell(i + 1, j + 1, Area.CellsType.MISS);
                    }

                    if (i == Area.AREA_SIZE - 1 && j == 0) {
                        area.setCell(i - 1, j + 1, Area.CellsType.MISS);
                    }

                    if (i == 0 && j == Area.AREA_SIZE - 1) {
                        area.setCell(i + 1, j - 1, Area.CellsType.MISS);
                    }

                    if (i == Area.AREA_SIZE - 1 && j == Area.AREA_SIZE - 1) {
                        area.setCell(i - 1, j - 1, Area.CellsType.MISS);
                    }

                    if (i  == Area.AREA_SIZE - 1 && j > 0 && j < Area.AREA_SIZE - 1) {
                        area.setCell(i - 1, j - 1, Area.CellsType.MISS);
                        area.setCell(i - 1, j - 1, Area.CellsType.MISS);
                    }
                }
            }
        }
    }
}
