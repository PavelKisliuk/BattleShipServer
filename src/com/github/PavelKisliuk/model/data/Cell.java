package com.github.PavelKisliuk.model.data;

import java.util.Objects;

/**
 * @author dzmitryplatonov on 2019-04-11.
 * @version 0.0.1
 */
public class Cell {

    private int i;
    private int j;
    private Area.CellsType cellsType;

    public Cell(int i, int j, Area.CellsType cellsType) {
        this.i = i;
        this.j = j;
        this.cellsType = cellsType;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public Area.CellsType getCellsType() {
        return cellsType;
    }

    public void setCellsType(Area.CellsType cellsType) {
        this.cellsType = cellsType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return i == cell.i &&
                j == cell.j &&
                cellsType == cell.cellsType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, cellsType);
    }

    @Override
    public String toString() {
        return "{" + i + ", " + j + ", " + cellsType + '}';
    }
}
