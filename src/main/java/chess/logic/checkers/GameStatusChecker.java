package chess.logic.checkers;

import chess.game.Game;
import chess.game.GameStatus;
import chess.logic.figures.FigureColor;
import chess.utils.PiecesMapManager.Piece;

import java.util.Map;

import static chess.game.GameStatus.BLACK_WON;
import static chess.game.GameStatus.FIFTY_MOVE_RULE;
import static chess.game.GameStatus.GAME_IN_PROGRESS;
import static chess.game.GameStatus.INSUFFICIENT_MATERIAL;
import static chess.game.GameStatus.STALEMATE;
import static chess.game.GameStatus.THREEFOLD_REPETITION;
import static chess.game.GameStatus.WHITE_WON;
import static chess.logic.board.BoardUpdater.updateBoard;
import static chess.logic.checkers.CheckmateChecker.isCheckmate;
import static chess.logic.checkers.StalemateChecker.isStalemate;
import static chess.utils.PiecesMapManager.Piece.DARK_SQUARED_BISHOP;
import static chess.utils.PiecesMapManager.Piece.KING;
import static chess.utils.PiecesMapManager.Piece.KNIGHT;
import static chess.utils.PiecesMapManager.Piece.LIGHT_SQUARED_BISHOP;
import static chess.utils.PiecesMapManager.Piece.PAWN;
import static chess.utils.PiecesMapManager.Piece.QUEEN;
import static chess.utils.PiecesMapManager.Piece.ROOK;

public class GameStatusChecker {
    public static GameStatus checkGameStatus(Game game) {
        if (drawByFiftyMoveRule(game)) return FIFTY_MOVE_RULE;
        if (drawByInsufficientMaterialRule(game)) return INSUFFICIENT_MATERIAL;
        if (drawByThreefoldRepetitionRule(game)) return THREEFOLD_REPETITION;

        updateBoard(game);
        if (game.getWhoseMove() == FigureColor.WHITE && isCheckmate(game)) return BLACK_WON;
        if (game.getWhoseMove() == FigureColor.BLACK && isCheckmate(game)) return WHITE_WON;

        if (isStalemate(game)) return STALEMATE;

        return GAME_IN_PROGRESS;
    }

    private static boolean drawByFiftyMoveRule(Game game) {
        return game.getHalfMoveClock() >= 50;
    }

    private static boolean drawByInsufficientMaterialRule(Game game) {
        Map<Piece, Integer> whitePieces = game.getWhitePieces();
        Map<Piece, Integer> blackPieces = game.getBlackPieces();

        if ((noPawnLeft(whitePieces) && noPawnLeft(blackPieces)) &&
                (noHeavyPieceLeft(whitePieces) && noHeavyPieceLeft(blackPieces))) {
            if (onlyKingLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) && onlyKingLeftWhenNoPawnsAndNoHeavyPieces(blackPieces)) {
                return true;
            }
            if (onlyKingLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) && onlyKingAndKnightLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) ||
                    onlyKingLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) && onlyKingAndKnightLeftWhenNoPawnsAndNoHeavyPieces(whitePieces)) {
                return true;
            }
            if (onlyKingLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) && onlyKingAndOneAnyTypeBishopLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) ||
                    onlyKingLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) && onlyKingAndOneAnyTypeBishopLeftWhenNoPawnsAndNoHeavyPieces(whitePieces)) {
                return true;
            }
            if (onlyKingLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) &&
                    (onlyKingAndLightSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) || onlyKingAndDarkSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(blackPieces)) ||
                    onlyKingLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) &&
                            (onlyKingAndLightSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) || onlyKingAndDarkSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(whitePieces))) {
                return true;
            }
            if (onlyKingAndLightSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) && onlyKingAndLightSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(blackPieces) ||
                    onlyKingAndDarkSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(whitePieces) && onlyKingAndDarkSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(blackPieces)) {
                return true;
            }
        }
        return false;
    }

    private static boolean drawByThreefoldRepetitionRule(Game game) {
        return Game.threefoldRepetitionMap.getOrDefault(game.getBoard(), 0) > 2;
    }

    private static boolean noPawnLeft(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(PAWN) == 0;
    }

    private static boolean noHeavyPieceLeft(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(ROOK) == 0 && piecesMap.get(QUEEN) == 0;
    }

    private static boolean onlyKingLeftWhenNoPawnsAndNoHeavyPieces(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(KNIGHT) == 0 && piecesMap.get(LIGHT_SQUARED_BISHOP) == 0
                && piecesMap.get(DARK_SQUARED_BISHOP) == 0 && piecesMap.get(KING) == 1;
    }

    private static boolean onlyKingAndKnightLeftWhenNoPawnsAndNoHeavyPieces(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(KNIGHT) == 1 && piecesMap.get(LIGHT_SQUARED_BISHOP) == 0
                && piecesMap.get(DARK_SQUARED_BISHOP) == 0 && piecesMap.get(KING) == 1;
    }

    private static boolean onlyKingAndOneAnyTypeBishopLeftWhenNoPawnsAndNoHeavyPieces(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(KNIGHT) == 0 && ((piecesMap.get(LIGHT_SQUARED_BISHOP) == 1
                && piecesMap.get(DARK_SQUARED_BISHOP) == 0) || (piecesMap.get(LIGHT_SQUARED_BISHOP) == 0) && piecesMap.get(DARK_SQUARED_BISHOP) == 1)
                && piecesMap.get(KING) == 1;
    }

    private static boolean onlyKingAndLightSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(KNIGHT) == 0 && piecesMap.get(LIGHT_SQUARED_BISHOP) >= 1
                && piecesMap.get(DARK_SQUARED_BISHOP) == 0 && piecesMap.get(KING) == 1;
    }

    private static boolean onlyKingAndDarkSquaredBishopsLeftWhenNoPawnsAndNoHeavyPieces(Map<Piece, Integer> piecesMap) {
        return piecesMap.get(KNIGHT) == 0 && piecesMap.get(LIGHT_SQUARED_BISHOP) == 0
                && piecesMap.get(DARK_SQUARED_BISHOP) >= 1 && piecesMap.get(KING) == 1;
    }
}
