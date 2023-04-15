package chess.logic.figures;

public enum FigureColor {
    BLACK, WHITE, NONE;

    public static FigureColor getOppositeColor(FigureColor figureColor) {
        if(figureColor == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
}
