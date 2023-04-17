package chess.logic.converters;

import chess.game.Game;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.Rook;
import chess.logic.moves.BoardMove;
import chess.logic.moves.CastleMove;

import static chess.utils.Constants.BLACK_CASTLE_ROW;
import static chess.utils.Constants.KING_LONG_CASTLE_COLUMN;
import static chess.utils.Constants.KING_SHORT_CASTLE_COLUMN;
import static chess.utils.Constants.ROOK_LONG_CASTLE_COLUMN;
import static chess.utils.Constants.ROOK_SHORT_CASTLE_COLUMN;
import static chess.utils.Constants.WHITE_CASTLE_ROW;

public class BoardMoveToCastleMoveConverter {
    private BoardMoveToCastleMoveConverter() {
    }

    public static BoardMove convertBoardMoveToCastleMoveIfNeeded(Game game, BoardMove boardMove) {
        int sourceColumn = boardMove.getSourceColumn();
        int sourceRow = boardMove.getSourceRow();
        Figure sourceFigure = game.getBoard().getFigure(sourceColumn, sourceRow);

        if (sourceFigure instanceof King) {
            int destinationColumn = boardMove.getDestinationColumn();
            int destinationRow = boardMove.getDestinationRow();
            Figure destinationFigure = game.getBoard().getFigure(destinationColumn, destinationRow);


            if (((King) sourceFigure).isFirstMove() &&
                    ((destinationFigure instanceof Rook && destinationFigure.getColor() == sourceFigure.getColor())
                            || destinationColumn == KING_SHORT_CASTLE_COLUMN || destinationColumn == KING_LONG_CASTLE_COLUMN)) {

                int kingDestinationColumn;
                int kingDestinationRow;

                int rookSourceColumn;
                int rookSourceRow;
                int rookDestinationColumn;
                int rookDestinationRow;

                boolean shortCastle = boardMove.getDestinationColumn() > boardMove.getSourceColumn();
                boolean whitesMove = game.getWhoseMove() == FigureColor.WHITE;


                if (shortCastle && whitesMove) {
                    kingDestinationColumn = KING_SHORT_CASTLE_COLUMN;
                    kingDestinationRow = WHITE_CASTLE_ROW;
                    rookDestinationColumn = ROOK_SHORT_CASTLE_COLUMN;
                    rookDestinationRow = WHITE_CASTLE_ROW;
                    rookSourceColumn = game.getRooksStartPositions().getWhiteShortCastleRookColumn();
                    rookSourceRow = game.getRooksStartPositions().getWhiteShortCastleRookRow();
                } else if (!shortCastle && whitesMove) {
                    kingDestinationColumn = KING_LONG_CASTLE_COLUMN;
                    kingDestinationRow = WHITE_CASTLE_ROW;
                    rookDestinationColumn = ROOK_LONG_CASTLE_COLUMN;
                    rookDestinationRow = WHITE_CASTLE_ROW;
                    rookSourceColumn = game.getRooksStartPositions().getWhiteLongCastleRookColumn();
                    rookSourceRow = game.getRooksStartPositions().getWhiteLongCastleRookRow();
                } else if (shortCastle && !whitesMove) {
                    kingDestinationColumn = KING_SHORT_CASTLE_COLUMN;
                    kingDestinationRow = BLACK_CASTLE_ROW;
                    rookDestinationColumn = ROOK_SHORT_CASTLE_COLUMN;
                    rookDestinationRow = BLACK_CASTLE_ROW;
                    rookSourceColumn = game.getRooksStartPositions().getBlackShortCastleRookColumn();
                    rookSourceRow = game.getRooksStartPositions().getBlackShortCastleRookRow();
                } else {
                    kingDestinationColumn = KING_LONG_CASTLE_COLUMN;
                    kingDestinationRow = BLACK_CASTLE_ROW;
                    rookDestinationColumn = ROOK_LONG_CASTLE_COLUMN;
                    rookDestinationRow = BLACK_CASTLE_ROW;
                    rookSourceColumn = game.getRooksStartPositions().getBlackLongCastleRookColumn();
                    rookSourceRow = game.getRooksStartPositions().getBlackLongCastleRookRow();
                }

                return new CastleMove(sourceColumn, sourceRow, kingDestinationColumn, kingDestinationRow, rookSourceColumn, rookSourceRow, rookDestinationColumn, rookDestinationRow);
            }
        }
        return boardMove;
    }
}
