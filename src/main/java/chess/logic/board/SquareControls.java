package chess.logic.board;

public class SquareControls {
    private boolean isControlledByWhite = false;
    private boolean isControlledByBlack = false;
    private boolean availableForWhiteKing = true;
    private boolean availableForBlackKing = true;

    public SquareControls() {
    }

    protected void setUncontrolledAndAvailable() {
        isControlledByWhite = false;
        isControlledByBlack = false;
        availableForWhiteKing = true;
        availableForBlackKing = true;
    }

    protected boolean isControlledByWhite() {
        return isControlledByWhite;
    }

    protected void setControlledByWhite(boolean controlledByWhite) {
        isControlledByWhite = controlledByWhite;
    }

    protected boolean isControlledByBlack() {
        return isControlledByBlack;
    }

    protected void setControlledByBlack(boolean controlledByBlack) {
        isControlledByBlack = controlledByBlack;
    }

    protected boolean isAvailableForWhiteKing() {
        return availableForWhiteKing;
    }

    protected void setUnavailableForWhiteKing() {
        this.availableForWhiteKing = false;
    }

    protected boolean isAvailableForBlackKing() {
        return availableForBlackKing;
    }

    protected void setUnavailableForBlackKing() {
        this.availableForBlackKing = false;
    }
}
