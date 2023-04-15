package chess.logic.board;

import chess.utils.MoveMaps;

import java.util.Objects;

public class Square {
    private int column;
    private int row;

    public Square(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        if (column == -1 && row == -1) return "-";
        return MoveMaps.intToStringColumnMap.get(column) +
                MoveMaps.intToStringRowMap.get(row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return column == square.column && row == square.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
