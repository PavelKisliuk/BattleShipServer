package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;
import org.apache.log4j.Logger;


/**
 * @author dzmitryplatonov on 2019-04-06.
 * @version 0.0.1
 */
public enum  RandomAreaArranger {

    INSTANCE;

    private static final Logger logger;

    static {
        logger = Logger.getLogger(RandomAreaArranger.class);
    }

    AreaArranger arranger = AreaArranger.INSTANCE;
    Creator creator = Creator.INSTANCE;
    Initializer initializer = Initializer.INSTANCE;
    Checker checker = Checker.INSTANCE;

    public boolean arrangeRandomArea(Area area) {
        Ship shipFourCell = creator.createShip();
        initializer.initRandom(shipFourCell, Ship.FOUR_CELL_SHIP_HEALTH);
        arranger.arrangeShip(area, shipFourCell);


        while (area.getShips().length != 2) {
            Ship shipThreeCellA = creator.createShip();
            initializer.initRandom(shipThreeCellA, Ship.THREE_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipThreeCellA);
        }



        while (area.getShips().length != 3) {
            Ship shipThreeCellB = creator.createShip();
            initializer.initRandom(shipThreeCellB, Ship.THREE_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipThreeCellB);
        }



        while (area.getShips().length != 4) {
            Ship shipTwoCellA = creator.createShip();
            initializer.initRandom(shipTwoCellA, Ship.TWO_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipTwoCellA);
        }



        while (area.getShips().length != 5) {
            Ship shipTwoCellB = creator.createShip();
            initializer.initRandom(shipTwoCellB, Ship.TWO_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipTwoCellB);
        }



        while (area.getShips().length != 6) {
            Ship shipTwoCellC = creator.createShip();
            initializer.initRandom(shipTwoCellC, Ship.TWO_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipTwoCellC);
        }



        while (area.getShips().length != 7) {
            Ship shipOneCellA = creator.createShip();
            initializer.initRandom(shipOneCellA, Ship.ONE_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipOneCellA);
        }



        while (area.getShips().length != 8) {
            Ship shipOneCellB = creator.createShip();
            initializer.initRandom(shipOneCellB, Ship.ONE_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipOneCellB);
        }


        while (area.getShips().length != 9) {
            Ship shipOneCellC = creator.createShip();
            initializer.initRandom(shipOneCellC, Ship.ONE_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipOneCellC);
        }



        while (area.getShips().length != 10) {
            Ship shipOneCellD = creator.createShip();
            initializer.initRandom(shipOneCellD, Ship.ONE_CELL_SHIP_HEALTH);
            arranger.arrangeShip(area, shipOneCellD);
        }

        logger.debug(area.getShips().length + " random ships was successfully arranged on \n" + area);

        arranger.changeCelltype(area, Area.CellsType.NEIGHBOR, Area.CellsType.EMPTY);

        return checker.isRightArrangement(area);
    }
}
