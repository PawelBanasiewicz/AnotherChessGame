package chess.game;

import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.Rook;
import chess.logic.moves.BoardMove;
import chess.logic.moves.BoardMoveType;
import chess.logic.moves.CastleMove;

import static chess.logic.figures.FigureColor.WHITE;
import static chess.logic.moves.BoardMoveType.TWO_SQUARES_PAWN_MOVE;
import static chess.utils.PiecesMapManager.addPieceToPiecesMapForAlreadyCreatedGame;
import static chess.utils.PiecesMapManager.deletePieceFromPiecesMap;

public class GameComponentsUpdater {

    public static void updateGameComponentsAfterEveryMove(Game game, BoardMoveType boardMoveType) {
        if (game.getWhoseMove() == FigureColor.BLACK) {
            game.setFullMoveNumber(game.getFullMoveNumber() + 1);
        }

        if (boardMoveType != TWO_SQUARES_PAWN_MOVE &&
                game.getPossibleEnPassantSquare().getColumn() != -1 && game.getPossibleEnPassantSquare().getRow() != -1) {
            game.getPossibleEnPassantSquare().setColumn(-1);
            game.getPossibleEnPassantSquare().setRow(-1);
        }

        if (game.getKing(game.getWhoseMove()).isInCheck()) {
            game.getKing(game.getWhoseMove()).setInCheck(false);
        }

        if (!game.getFiguresGivingCheck().isEmpty()) {
            game.getFiguresGivingCheck().clear();
        }

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                game.getBoard().getBoardSquare(column, row).setUncontrolledAndUnavailable();
            }
        }


        game.setOppositeWhoseMove();
    }

    public static void updateGameComponentsAfterCastle(Game game, CastleMove castleMove, Figure king, Figure rook) {
        game.getCastlingRights().setUnableToCastleForOneColor(king.getColor());
        game.getKingsPositions().setKingPosition(king.getColor(), castleMove.getDestinationColumn(), castleMove.getDestinationRow());

        if (king instanceof King) {
            ((King) king).setFirstMove(false);
        }

        if (rook instanceof Rook) {
            ((Rook) rook).setFirstMove(false);
        }
        game.setHalfMoveClock(game.getHalfMoveClock() + 1);
        Game.threefoldRepetitionMap.clear();
    }


    public static void updateGameComponentsAfterNonSpecialMove(Game game, BoardMove boardMove, Figure movingFigure) {
        boolean lostPrivilegeToCastle = updateGameComponentsForKingOrRookMove(game, boardMove, movingFigure);
        game.setHalfMoveClock(game.getHalfMoveClock() + 1);

        if (!lostPrivilegeToCastle) {
            Game.threefoldRepetitionMap.put(game.getBoard(), Game.threefoldRepetitionMap.getOrDefault(game.getBoard(), 0) + 1);
        }
    }

    public static void updateGameComponentsAfterCaptureMove(Game game, BoardMove boardMove, Figure movingFigure, Figure capturedFigure) {
        updateGameComponentsForKingOrRookMove(game, boardMove, movingFigure);
        deletePieceFromPiecesMap(game, capturedFigure);
        game.setHalfMoveClock(0);
        Game.threefoldRepetitionMap.clear();
    }

    public static void updateGameComponentsAfterTwoSquaresPawnMove(Game game, BoardMove boardMove) {
        int newEnPassantRow = boardMove.getDestinationRow();
        if(game.getWhoseMove() == WHITE) {
            newEnPassantRow += 1;
        } else {
            newEnPassantRow -= 1;
        }

        game.getPossibleEnPassantSquare().setColumn(boardMove.getDestinationColumn());
        game.getPossibleEnPassantSquare().setRow(newEnPassantRow);
        game.setHalfMoveClock(0);
        Game.threefoldRepetitionMap.clear();
    }


    public static void updateGameComponentsAfterPromotionMove(Game game, Figure movingFigure, Figure newFigure) {
        deletePieceFromPiecesMap(game, movingFigure);
        addPieceToPiecesMapForAlreadyCreatedGame(game, newFigure);
        game.setHalfMoveClock(0);
        Game.threefoldRepetitionMap.clear();
    }

    public static void updateGameComponentsAfterEnpassantCapture(Game game, Figure capturedPawn) {
        deletePieceFromPiecesMap(game, capturedPawn);
        game.setHalfMoveClock(0);
        Game.threefoldRepetitionMap.clear();
    }


    public static void updateGameComponentsAfterNormalPawnMove(Game game) {
        game.setHalfMoveClock(0);
        Game.threefoldRepetitionMap.clear();
    }


    private static boolean updateGameComponentsForKingOrRookMove(Game game, BoardMove boardMove, Figure movingFigure) {
        boolean lostPrivilegeToCastle = false;

        if (movingFigure instanceof King) {
            if (((King) movingFigure).isFirstMove()) {
                ((King) movingFigure).setFirstMove(false);
                game.getCastlingRights().setUnableToCastleForOneColor(movingFigure.getColor());
                lostPrivilegeToCastle = true;
                Game.threefoldRepetitionMap.clear();
            }
            game.getKingsPositions().setKingPosition(movingFigure.getColor(), boardMove.getDestinationColumn(), boardMove.getDestinationRow());
        }
        if (movingFigure instanceof Rook && ((Rook) movingFigure).isFirstMove()) {
            int kingColumn = game.getKingsPositions().getKingColumn(movingFigure.getColor());
            int rookColumn = boardMove.getSourceColumn();

            if (rookColumn < kingColumn) {
                game.getCastlingRights().setUnableToLongCastleForOneColor(movingFigure.getColor());
            } else {
                game.getCastlingRights().setUnableToShortCastleForOneColor(movingFigure.getColor());
            }

            ((Rook) movingFigure).setFirstMove(false);
            lostPrivilegeToCastle = true;
            Game.threefoldRepetitionMap.clear();
        }
        return lostPrivilegeToCastle;
    }
}
