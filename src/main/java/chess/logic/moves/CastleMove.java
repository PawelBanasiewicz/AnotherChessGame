package chess.logic.moves;

public class CastleMove extends BoardMove {
    private final int rookSourceColumn;
    private final int rookSourceRow;
    private final int rookDestinationColumn;
    private final int rookDestinationRow;

    public CastleMove(int kingSourceColumn, int kingSourceRow, int kingDestinationColumn, int kingDestinationRow,
                      int rookSourceColumn, int rookSourceRow, int rookDestinationColumn, int rookDestinationRow) {
        super(kingSourceColumn, kingSourceRow, kingDestinationColumn, kingDestinationRow);
        this.rookSourceColumn = rookSourceColumn;
        this.rookSourceRow = rookSourceRow;
        this.rookDestinationColumn = rookDestinationColumn;
        this.rookDestinationRow = rookDestinationRow;
    }

    public int getRookSourceColumn() {
        return rookSourceColumn;
    }

    public int getRookSourceRow() {
        return rookSourceRow;
    }

    public int getRookDestinationColumn() {
        return rookDestinationColumn;
    }

    public int getRookDestinationRow() {
        return rookDestinationRow;
    }
}
