package chess.logic.checkers;

import chess.game.Game;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.None;
import chess.logic.moves.BoardMove;

import java.util.List;

import static chess.logic.checkers.LegalMovesChecker.getAllPossibleMovesFromSelectedPosition;
import static chess.utils.Constants.FIRST_COLUMN;
import static chess.utils.Constants.FIRST_ROW;
import static chess.utils.Constants.LAST_COLUMN;
import static chess.utils.Constants.LAST_ROW;

public class CheckmateChecker {
    private CheckmateChecker() {
    }

    public static boolean isCheckmate(Game game) {
        if (isCheck(game)) {
            List <BoardMove> kingPossibleMoves = getAllPossibleKingMoves(game);

            if (game.getFiguresGivingCheck().size() == 1) {
                return kingPossibleMoves.isEmpty() && !anyPieceHavePossibleMove(game);
            } else {
                return kingPossibleMoves.isEmpty();
            }
        }
        return false;
    }

    private static boolean isCheck(Game game) {
        return game.getKing(game.getWhoseMove()).isInCheck();
    }

    protected static List<BoardMove> getAllPossibleKingMoves(Game game) {
        int kingColumn;
        int kingRow;

        if (game.getWhoseMove() == FigureColor.WHITE) {
            kingColumn = game.getKingsPositions().getWhiteKingColumn();
            kingRow = game.getKingsPositions().getWhiteKingRow();
        } else {
            kingColumn = game.getKingsPositions().getBlackKingColumn();
            kingRow = game.getKingsPositions().getBlackKingRow();
        }
        return getAllPossibleMovesFromSelectedPosition(game, kingColumn, kingRow);
    }

    static boolean anyPieceHavePossibleMove(Game game) {
        for (int row = FIRST_ROW; row < LAST_ROW; row++) {
            for (int column = FIRST_COLUMN; column < LAST_COLUMN; column++) {
                Figure figure = game.getBoard().getFigure(column, row);
                if (!(figure instanceof None) && !(figure instanceof King) && figure.getColor() == game.getWhoseMove()) {
                    List<BoardMove> allPossibleMoves = getAllPossibleMovesFromSelectedPosition(game, column, row);
                    if(!allPossibleMoves.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
