package chess.logic.board;

import chess.logic.figures.Figure;

import java.util.List;
import java.util.Objects;

public class Board {
    private final List<BoardRow> boardRows;

    public Board(List<BoardRow> figuresRows) {
        this.boardRows = figuresRows;
    }

    public BoardSquare getBoardSquare(int column, int row) {
        return boardRows.get(row).getBoardColumns().get(column);
    }

    public BoardSquare getBoardSquare(Square square) {
        return boardRows.get(square.getRow()).getBoardColumns().get(square.getColumn());
    }

    public Figure getFigure(int column, int row) {
        return boardRows.get(row).getBoardColumns().get(column).getFigure();
    }

    public void setFigure(int column, int row, Figure figure) {
        boardRows.get(row).getBoardColumns().get(column).setFigure(figure);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(boardRows, board.boardRows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardRows);
    }
}
