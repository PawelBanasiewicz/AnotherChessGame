package chess.logic.moves;

public class FigureMove {
    private final int numberOfColumnsToMove;
    private final int numberOfRowsToMove;

    public FigureMove(int numberOfColumnsToMove, int numberOfRowsToMove) {
        this.numberOfColumnsToMove = numberOfColumnsToMove;
        this.numberOfRowsToMove = numberOfRowsToMove;
    }

    public int getNumberOfColumnsToMove() {
        return numberOfColumnsToMove;
    }

    public int getNumberOfRowsToMove() {
        return numberOfRowsToMove;
    }

}
