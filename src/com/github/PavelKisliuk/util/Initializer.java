package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import com.github.PavelKisliuk.util.exception.ShipInitializationException;
import org.apache.log4j.Logger;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author dzmitryplatonov on 2019-04-06.
 * @version 0.0.1
 */
public enum Initializer {

    INSTANCE;

    private static final Logger logger;

    static {
        logger = Logger.getLogger(Initializer.class);
    }

    private static final int MAXIMUM_SHIP_HEALTH = 4;


    public void init(Ship ship, int health, int[] row, int[] column) {

        if (ship == null) {
            throw new ShipInitializationException("ship is null");
        }

        if (health <= 0 || health > MAXIMUM_SHIP_HEALTH || row.length != health || column.length != health) {
            throw new ShipInitializationException("Not correct input data");
        }

        if (health == 1) {
            ship.setHealth(1);
            ship.setRow(row);
            ship.setColumn(column);
        }

        for (int i = 0; i < health - 1; i++) {
            if ((row[i] == row[i + 1] && (column[i] + 1 == column[i + 1] || column[i] - 1 == column[i + 1]))
                    || (column[i] == column[i + 1] && (row[i] + 1 == row[i + 1] || row[i] - 1 == row[i + 1]))) {
                ship.setHealth(health);
                ship.setRow(row);
                ship.setColumn(column);
            } else {
                throw new ShipInitializationException("Not correct row or column input data");
            }
        }

    }

    public void init(Ship ship, int[] row, int[] column) {

        if (ship == null) {
            throw new ShipInitializationException("ship is null");
        }

        if (row.length <= 0 || row.length > MAXIMUM_SHIP_HEALTH || row.length != column.length) {
            throw new ShipInitializationException("Not correct input data");
        }

        if (row.length == 1) {
            ship.setHealth(row.length);
            ship.setRow(row);
            ship.setColumn(column);
        }

        for (int i = 0; i < row.length - 1; i++) {
            if ((row[i] == row[i + 1] && (column[i] + 1 == column[i + 1] || column[i] - 1 == column[i + 1]))
                    || (column[i] == column[i + 1] && (row[i] + 1 == row[i + 1] || row[i] - 1 == row[i + 1]))) {
                ship.setHealth(row.length);
                ship.setRow(row);
                ship.setColumn(column);
            } else {
                throw new ShipInitializationException("Not correct row or column input data");
            }
        }
    }


    public void initRandom(Ship ship, int health) {

        Random random = new SecureRandom();

        boolean isHorizontal = random.nextBoolean();
        int firstCellIndexI = random.nextInt(Area.AREA_SIZE);
        int firstCellIndexJ = random.nextInt(Area.AREA_SIZE);

        if (health == 1) {
            ship.setRow(new int[]{firstCellIndexI});
            ship.setColumn(new int[]{firstCellIndexJ});
            ship.setHealth(health);
            logger.debug("Ship (" + firstCellIndexI + "," + firstCellIndexJ + ") initialized successfully");
        }

        if (health > 1 && health <= MAXIMUM_SHIP_HEALTH) {
            if (isHorizontal) {
                ship.setHealth(health);
                ship.setRow(initEqualRow(health, firstCellIndexI));
                ship.setColumn(initAscendingRow(health,firstCellIndexJ));
                for (int i = 0; i < ship.getColumn().length; i++) {
                    if (ship.getColumn()[i] >= 10) {
                        logger.debug("ship random Re-initialization");
                        initRandom(ship, health);
                    }
                }
                logger.debug(ship + " initialized successfully");
            }
            else {
                ship.setHealth(health);
                ship.setRow(initAscendingRow(health, firstCellIndexI));
                ship.setColumn(initEqualRow(health, firstCellIndexJ));
                for (int i = 0; i < ship.getRow().length; i++) {
                    if (ship.getRow()[i] >= 10) {
                        logger.debug("ship random Re-initialization");
                        initRandom(ship, health);
                    }
                }
                logger.debug(ship + " initialized successfully");
            }
        }

    }

    private int[] initEqualRow(int length, int i) {
        int[] result = new int[length];
        for (int j = 0; j < result.length; j++) {
            result[j] = i;
        }
        return result;
    }

    private int[] initAscendingRow(int length, int i) {
        int[] result = new int[length];
        for (int j = 0; j < result.length; j++) {
            result[j] = i++;
        }
        return result;
    }
}
