package chess.logic.checkers;

import chess.game.Game;
import chess.logic.moves.BoardMove;

import java.util.List;

import static chess.logic.checkers.CheckmateChecker.anyPieceHavePossibleMove;
import static chess.logic.checkers.CheckmateChecker.getAllPossibleKingMoves;

public class StalemateChecker {
    public StalemateChecker() {
    }

    public static boolean isStalemate(Game game) {
        List<BoardMove> kingPossibleMoves = getAllPossibleKingMoves(game);
        if (kingPossibleMoves.isEmpty()) {
            return !anyPieceHavePossibleMove(game);
        }
        return false;
    }

}

