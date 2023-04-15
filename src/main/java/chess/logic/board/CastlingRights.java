package chess.logic.board;

import chess.logic.figures.FigureColor;

import static chess.logic.figures.FigureColor.WHITE;

public class CastlingRights {
    private boolean whiteCanShortCastle;
    private boolean blackCanShortCastle;
    private boolean whiteCanLongCastle;
    private boolean blackCanLongCastle;

    public CastlingRights(boolean whiteCanShortCastle, boolean blackCanShortCastle, boolean whiteCanLongCastle, boolean blackCanLongCastle) {
        this.whiteCanShortCastle = whiteCanShortCastle;
        this.blackCanShortCastle = blackCanShortCastle;
        this.whiteCanLongCastle = whiteCanLongCastle;
        this.blackCanLongCastle = blackCanLongCastle;
    }

    public void setUnableToCastleForOneColor(FigureColor figureColor) {
        if (figureColor == WHITE) {
            whiteCanShortCastle = false;
            whiteCanLongCastle = false;
        } else {
            blackCanShortCastle = false;
            blackCanLongCastle = false;
        }
    }

    public void setUnableToShortCastleForOneColor(FigureColor figureColor) {
        if (figureColor == WHITE) {
            whiteCanShortCastle = false;
        } else {
            blackCanShortCastle = false;
        }
    }

    public void setUnableToLongCastleForOneColor(FigureColor figureColor) {
        if (figureColor == WHITE) {
            whiteCanLongCastle = false;
        } else {
            blackCanLongCastle = false;
        }
    }

    public boolean canWhiteShortCastle() {
        return whiteCanShortCastle;
    }

    public boolean canBlackShortCastle() {
        return blackCanShortCastle;
    }

    public boolean canWhiteLongCastle() {
        return whiteCanLongCastle;
    }

    public boolean canBlackLongCastle() {
        return blackCanLongCastle;
    }


    @Override
    public String toString() {
        if (!whiteCanShortCastle && !whiteCanLongCastle && !blackCanShortCastle && !blackCanLongCastle) return "-";
        StringBuilder s = new StringBuilder();
        if (whiteCanShortCastle) s.append("K");
        if (whiteCanLongCastle) s.append("Q");
        if (blackCanShortCastle) s.append("k");
        if (blackCanLongCastle) s.append("q");
        return s.toString();
    }
}
