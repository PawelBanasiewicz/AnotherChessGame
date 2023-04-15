package chess.logic.board;

import java.util.List;
import java.util.Objects;

public class BoardRow {
    private final List<BoardSquare> boardColumns;

    public BoardRow(List<BoardSquare> boardColumns) {
        this.boardColumns = boardColumns;
    }

    public List<BoardSquare> getBoardColumns() {
        return boardColumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardRow boardRow = (BoardRow) o;
        return Objects.equals(boardColumns, boardRow.boardColumns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardColumns);
    }
}
