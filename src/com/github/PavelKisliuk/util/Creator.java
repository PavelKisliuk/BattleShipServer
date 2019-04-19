package com.github.PavelKisliuk.util;

import com.github.PavelKisliuk.model.data.Area;
import com.github.PavelKisliuk.model.data.Ship;

import java.util.Arrays;
import java.util.List;

/**
 * @author dzmitryplatonov on 2019-04-06.
 * @version 0.0.1
 */
public enum Creator {

	INSTANCE;

	public Ship createShip() {
		return new Ship();
	}

	public Area createArea() {
		return new Area();
	}

	public int[] getIntArray(List<Integer> ar) {
		int[] newAr = new int[ar.size()];
		for (int i = 0; i < newAr.length; i++) {
			newAr[i] = ar.get(i);
		}
		return newAr;
	}

	public void correctShip(Ship ship) {
		if (ship.getHealth() > 1) {
			int[] cor = ship.getRow();
			if (cor[0] == cor[1]) {
				cor = ship.getColumn();
			}
			Arrays.sort(cor);
		}
	}

	public int convertRow(int place) {
		return (place / Area.AREA_SIZE);
	}

	public int convertColumn(int place) {
		return (place % Area.AREA_SIZE);
	}

	public int convertPlace(int row, int column) {
		return ((row * Area.AREA_SIZE) + column);
	}
}
