package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.ShipHealthException;
import org.apache.log4j.Logger;

public enum  Checker {

    INSTANCE;

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Checker.class);
    }

    private static final int INSERTION_SHIFT = 1;
    private static final int FOUR_CELLS_SHIPS_AMOUNT = 1;
    private static final int THREE_CELLS_SHIPS_AMOUNT = 2;
    private static final int TWO_CELLS_SHIPS_AMOUNT = 3;
    private static final int ONE_CELLS_SHIPS_AMOUNT = 4;


    public boolean isRightArrangement(Area area) {
        boolean result = true;
        if (area == null || area.length() != Area.AREA_SIZE || area.width() != Area.AREA_SIZE || area.getShips() == null) {
            logger.debug("Incorrect input data received");
            result = false;
        } else {
            for (int i = 0; i < area.length(); i++) {
                for (int j = 0; j < area.width(); j++) {
                    if (area.getCell(i, j) == null) {
                        logger.debug("Cell is null");
                        result = false;
                        break;
                    }
                    if (area.getCell(i, j) == Area.CellsType.BEATEN || area.getCell(i, j) == Area.CellsType.MISS) {
                        logger.debug("Unexpected type of Cell detected");
                        result = false;
                        break;
                    }
                }
            }

            if (!checkShips(area)) {
                logger.debug("Ships check failed");
                result = false;
            }

            for (int i = 0; i < area.getShips().length; i++) {
                if (!checkNeighbor(area, area.getShips()[i])) {
                    logger.debug("Neighbor check failed");
                    result = false;
                    break;
                }
            }

        }


        return result;
    }

    boolean checkNeighbor(Area area, Ship ship) {
        area = ringAreaWithEmpty(area);
        switch (ship.getHealth()) {
            case 1:

                return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                        && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

            case 2:

                if (ship.getRow()[0] == ship.getRow()[1]) {
                    return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT - 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

                } else {
                    return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

                }
            case 3:

                if (ship.getRow()[0] == ship.getRow()[1]) {
                    return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT + 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT - 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT - 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

                } else {

                    return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

                }
            case 4:

                if (ship.getRow()[0] == ship.getRow()[1]) {
                    return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT + 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT - 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT + 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT - 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT + 1, ship.getColumn()[3] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT - 1, ship.getColumn()[3] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT, ship.getColumn()[3] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT, ship.getColumn()[3] + INSERTION_SHIFT - 1) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT - 1, ship.getColumn()[3] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT + 1, ship.getColumn()[3] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT - 1, ship.getColumn()[3] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT + 1, ship.getColumn()[3] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

                } else {

                    return area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT - 1, ship.getColumn()[0] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[0] + INSERTION_SHIFT + 1, ship.getColumn()[0] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT - 1, ship.getColumn()[1] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[1] + INSERTION_SHIFT + 1, ship.getColumn()[1] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT - 1, ship.getColumn()[2] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[2] + INSERTION_SHIFT + 1, ship.getColumn()[2] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP

                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT + 1, ship.getColumn()[3] + INSERTION_SHIFT) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT - 1, ship.getColumn()[3] + INSERTION_SHIFT) == Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT, ship.getColumn()[3] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT, ship.getColumn()[3] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT - 1, ship.getColumn()[3] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT + 1, ship.getColumn()[3] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT - 1, ship.getColumn()[3] + INSERTION_SHIFT + 1) != Area.CellsType.SHIP
                            && area.getCell(ship.getRow()[3] + INSERTION_SHIFT + 1, ship.getColumn()[3] + INSERTION_SHIFT - 1) != Area.CellsType.SHIP;

                }

            default:
                throw new ShipHealthException("Incorrect ship health");
        }

    }



    Area ringAreaWithEmpty(Area area) {
        Area resultArea = new Area(area.length() + 2, area.width() + 2);
        insertSmallerArea(resultArea, 1, 1, area);
        return resultArea;
    }

    private void insertSmallerArea(Area area, int leftTopCornerI, int leftTopCornerJ, Area smallerArea) {
        if (smallerArea.length() + leftTopCornerI > area.length()
                || smallerArea.width() + leftTopCornerJ > area.width()) {
            throw new IllegalArgumentException("Illegal argument, can't insert matrix ");
        }
        for (int i = 0; i < smallerArea.length(); i++) {
            for (int j = 0; j < smallerArea.width(); j++) {
                area.setCell(i + leftTopCornerI, j + leftTopCornerJ, smallerArea.getCell(i, j));
            }
        }
    }

    boolean checkShips(Area area) {
        int counterOneCellShip = ONE_CELLS_SHIPS_AMOUNT;
        int counterTwoCellShip = TWO_CELLS_SHIPS_AMOUNT;
        int counterThreeCellShip = THREE_CELLS_SHIPS_AMOUNT;
        int counterFourCellShip = FOUR_CELLS_SHIPS_AMOUNT;

        for (int i = 0; i < area.getShips().length; i++) {
            if (area.getShips()[i].getRow().length == area.getShips()[i].getColumn().length && area.getShips()[i].getRow().length == 4) {
                counterFourCellShip--;
            }
            if (area.getShips()[i].getRow().length == area.getShips()[i].getColumn().length && area.getShips()[i].getRow().length == 3) {
                counterThreeCellShip--;
            }
            if (area.getShips()[i].getRow().length == area.getShips()[i].getColumn().length && area.getShips()[i].getRow().length == 2) {
                counterTwoCellShip--;
            }
            if (area.getShips()[i].getRow().length == area.getShips()[i].getColumn().length && area.getShips()[i].getRow().length == 1) {
                counterOneCellShip--;
            }
        }

        return counterFourCellShip == 0 && counterThreeCellShip == 0 && counterTwoCellShip == 0 && counterOneCellShip == 0;
    }

    public boolean isShipAlive(Area area, int shipNumber) {
        Ship[] shipsGroup = area.getShips();
        return area.getCell(shipsGroup[shipNumber].getRow()[0], shipsGroup[shipNumber].getColumn()[0]) != Area.CellsType.KILLED;
    }

}
