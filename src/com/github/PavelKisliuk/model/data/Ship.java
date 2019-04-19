package com.github.PavelKisliuk.model.data;

import com.github.PavelKisliuk.util.exception.ShipInitializationException;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Ship implements Serializable {

	private static final Logger logger;

	static {
		logger = Logger.getLogger(Ship.class);
	}

	public static final int ONE_CELL_SHIP_HEALTH = 1;
	public static final int TWO_CELL_SHIP_HEALTH = 2;
	public static final int THREE_CELL_SHIP_HEALTH = 3;
	public static final int FOUR_CELL_SHIP_HEALTH = 4;


	private int health;
	private int[] row;
	private int[] column;

	/**
	 * Parameterized constructor for creating {@param Ship}
	 *
	 * @param health is points of health of ship
	 *               if {@param health} == 0 Ship is dead
	 * @param row    is array contain places in matrix by row where reside Ship
	 * @param column is array contain places in matrix by column where reside Ship
	 */
	public Ship(int health, int[] row, int[] column) {
		this.health = health;
		this.row = new int[health];
		this.column = new int[health];
		for (int i = 0; i < health; i++) {
			this.row[i] = row[i];
			this.column[i] = column[i];
		}
	}

	public Ship() {
		this.health = ONE_CELL_SHIP_HEALTH;
		this.row = new int[health];
		this.column = new int[health];
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health <= 0 || health > Area.AREA_SIZE) {
			throw new ShipInitializationException("Illegal health parameter");
		}
		this.health = health;
	}

	public int[] getRow() {
		return row;
	}

	public void setRow(int[] row) {
		if (row == null || row.length <= 0 || row.length > Area.AREA_SIZE) {
			throw new ShipInitializationException("Illegal row parameter");
		}
		this.row = row;
		System.arraycopy(row, 0, this.row, 0, row.length);
	}

	public int[] getColumn() {
		return column;
	}

	public void setColumn(int[] column) {
		if (column == null || column.length <= 0 || column.length > Area.AREA_SIZE) {
			throw new ShipInitializationException("Illegal column parameter");
		}
		this.column = column;
		System.arraycopy(column, 0, this.column, 0, column.length);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ship ship = (Ship) o;
		return health == ship.health &&
				Arrays.equals(row, ship.row) &&
				Arrays.equals(column, ship.column);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(health);
		result = 31 * result + Arrays.hashCode(row);
		result = 31 * result + Arrays.hashCode(column);
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ship{");
		for (int i = 0; i < getHealth(); i++) {
			builder.append("(").append(getRow()[i]).append(",").append(getColumn()[i]).append(")");
		}
		return builder.append("}").toString();
	}
}
