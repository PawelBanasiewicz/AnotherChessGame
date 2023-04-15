package chess.logic.moves;

import java.util.Objects;

public class BoardMove {
    private final int sourceColumn;
    private final int sourceRow;
    private final int destinationColumn;
    private final int destinationRow;

    public BoardMove(int sourceColumn, int sourceRow, int destinationColumn, int destinationRow) {
        this.sourceColumn = sourceColumn;
        this.sourceRow = sourceRow;
        this.destinationColumn = destinationColumn;
        this.destinationRow = destinationRow;
    }

    public int getSourceColumn() {
        return sourceColumn;
    }

    public int getSourceRow() {
        return sourceRow;
    }

    public int getDestinationColumn() {
        return destinationColumn;
    }

    public int getDestinationRow() {
        return destinationRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardMove boardMove = (BoardMove) o;
        return sourceColumn == boardMove.sourceColumn && sourceRow == boardMove.sourceRow &&
                destinationColumn == boardMove.destinationColumn && destinationRow == boardMove.destinationRow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceColumn, sourceRow, destinationColumn, destinationRow);
    }
}
