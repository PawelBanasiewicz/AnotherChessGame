package chess.logic.board;

public class RooksStartPositions {
    private int whiteShortCastleRookColumn = -1;
    private int whiteShortCastleRookRow = -1;
    private int whiteLongCastleRookColumn = -1;
    private int whiteLongCastleRookRow = -1;
    private int blackShortCastleRookColumn = -1;
    private int blackShortCastleRookRow = -1;
    private int blackLongCastleRookColumn = -1;
    private int blackLongCastleRookRow = -1;

    public RooksStartPositions() {
    }

    public boolean isWhiteShortCastleRookAvailable() {
        return whiteShortCastleRookColumn != -1 && whiteShortCastleRookRow != -1;
    }

    public boolean isWhiteLongCastleRookAvailable() {
        return whiteLongCastleRookColumn != -1 && whiteLongCastleRookRow != -1;
    }

    public boolean isBlackShortCastleRookAvailable() {
        return blackShortCastleRookColumn != -1 && blackShortCastleRookRow != -1;
    }

    public boolean isBlackLongCastleRookAvailable() {
        return blackLongCastleRookColumn != -1 && blackLongCastleRookRow != -1;
    }

    public int getWhiteShortCastleRookColumn() {
        return whiteShortCastleRookColumn;
    }

    public void setWhiteShortCastleRookColumn(int whiteShortCastleRookColumn) {
        this.whiteShortCastleRookColumn = whiteShortCastleRookColumn;
    }

    public int getWhiteShortCastleRookRow() {
        return whiteShortCastleRookRow;
    }

    public void setWhiteShortCastleRookRow(int whiteShortCastleRookRow) {
        this.whiteShortCastleRookRow = whiteShortCastleRookRow;
    }

    public int getWhiteLongCastleRookColumn() {
        return whiteLongCastleRookColumn;
    }

    public void setWhiteLongCastleRookColumn(int whiteLongCastleRookColumn) {
        this.whiteLongCastleRookColumn = whiteLongCastleRookColumn;
    }

    public int getWhiteLongCastleRookRow() {
        return whiteLongCastleRookRow;
    }

    public void setWhiteLongCastleRookRow(int whiteLongCastleRookRow) {
        this.whiteLongCastleRookRow = whiteLongCastleRookRow;
    }

    public int getBlackShortCastleRookColumn() {
        return blackShortCastleRookColumn;
    }

    public void setBlackShortCastleRookColumn(int blackShortCastleRookColumn) {
        this.blackShortCastleRookColumn = blackShortCastleRookColumn;
    }

    public int getBlackShortCastleRookRow() {
        return blackShortCastleRookRow;
    }

    public void setBlackShortCastleRookRow(int blackShortCastleRookRow) {
        this.blackShortCastleRookRow = blackShortCastleRookRow;
    }

    public int getBlackLongCastleRookColumn() {
        return blackLongCastleRookColumn;
    }

    public void setBlackLongCastleRookColumn(int blackLongCastleRookColumn) {
        this.blackLongCastleRookColumn = blackLongCastleRookColumn;
    }

    public int getBlackLongCastleRookRow() {
        return blackLongCastleRookRow;
    }

    public void setBlackLongCastleRookRow(int blackLongCastleRookRow) {
        this.blackLongCastleRookRow = blackLongCastleRookRow;
    }

}
