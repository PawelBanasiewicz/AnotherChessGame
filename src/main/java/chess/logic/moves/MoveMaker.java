package chess.logic.moves;

import chess.game.Game;
import chess.logic.board.Board;
import chess.logic.figures.Figure;
import chess.logic.figures.None;
import chess.logic.figures.Queen;

import java.util.List;

import static chess.game.GameComponentsUpdater.updateGameComponentsAfterCaptureMove;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterCastle;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterEnpassantCapture;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterEveryMove;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterNonSpecialMove;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterNormalPawnMove;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterPromotionMove;
import static chess.game.GameComponentsUpdater.updateGameComponentsAfterTwoSquaresPawnMove;
import static chess.logic.checkers.LegalMovesChecker.getAllPossibleMovesFromSelectedPosition;

public class MoveMaker {

    public static void makeMove(Game game, BoardMoveWithType boardMoveWithType) {
        BoardMove boardMove = boardMoveWithType.getBoardMove();
        List<BoardMove> listOfMoves = getAllPossibleMovesFromSelectedPosition(game, boardMove.getSourceColumn(), boardMove.getSourceRow());
        if (listOfMoves.contains(boardMove)) {
            BoardMoveType boardMoveType = boardMoveWithType.getBoardMoveType();
            switch (boardMoveType) {
                case CASTLE -> makeCasteMove(game, (CastleMove) boardMove);
                case ENPASSANT_CAPTURE -> makeEnpassantCapture(game, boardMove);
                case PROMOTION_MOVE -> makePromotionMove(game, boardMove);
                case TWO_SQUARES_PAWN_MOVE -> makeTwoSquaresPawnMove(game, boardMove);
                case CAPTURE -> makeCaptureMove(game, boardMove);
                case NORMAL_PAWN_MOVE -> makeNormalPawnMove(game, boardMove);
                default -> makeNonSpecialMove(game, boardMove);
            }

            updateGameComponentsAfterEveryMove(game, boardMoveType);

        }
    }

    private static void makeCasteMove(Game game, CastleMove castleMove) {
        Figure king = game.getBoard().getFigure(castleMove.getSourceColumn(), castleMove.getRookSourceRow());
        Figure rook = game.getBoard().getFigure(castleMove.getRookSourceColumn(), castleMove.getRookSourceRow());

        setNoneFigureToSelectedSquare(game.getBoard(), castleMove.getSourceColumn(), castleMove.getSourceRow());
        setNoneFigureToSelectedSquare(game.getBoard(), castleMove.getRookSourceColumn(), castleMove.getRookSourceRow());

        setSelectedFigureToSelectedSquare(game.getBoard(), castleMove.getDestinationColumn(), castleMove.getDestinationRow(), king);
        setSelectedFigureToSelectedSquare(game.getBoard(), castleMove.getRookDestinationColumn(), castleMove.getRookDestinationRow(), rook);

        updateGameComponentsAfterCastle(game, castleMove, king, rook);
    }


    private static void makeEnpassantCapture(Game game, BoardMove boardMove) {
        Figure capturingPawn = game.getBoard().getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        Figure capturedPawn = game.getBoard().getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow());

        setSelectedFigureToSelectedSquare(game.getBoard(), boardMove.getDestinationColumn(), boardMove.getDestinationRow(), capturingPawn);
        setNoneFigureToSelectedSquare(game.getBoard(), boardMove.getSourceColumn(), boardMove.getSourceRow());
        setNoneFigureToSelectedSquare(game.getBoard(), boardMove.getDestinationColumn(), boardMove.getSourceRow());

        updateGameComponentsAfterEnpassantCapture(game, capturedPawn);
    }

    private static void makePromotionMove(Game game, BoardMove boardMove) {
        Figure movingFigure = game.getBoard().getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        Figure newFigure = new Queen(movingFigure.getColor());
        setNoneFigureToSelectedSquare(game.getBoard(), boardMove.getSourceColumn(), boardMove.getSourceRow());
        setSelectedFigureToSelectedSquare(game.getBoard(), boardMove.getDestinationColumn(), boardMove.getDestinationRow(), newFigure);

        updateGameComponentsAfterPromotionMove(game, movingFigure, newFigure);
    }

    private static void makeTwoSquaresPawnMove(Game game, BoardMove boardMove) {
        Figure movingFigure = game.getBoard().getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        moveSelectedFigureToSelectedSquare(game.getBoard(), boardMove, movingFigure);

        updateGameComponentsAfterTwoSquaresPawnMove(game, boardMove);
    }

    private static void makeCaptureMove(Game game, BoardMove boardMove) {
        Figure movingFigure = game.getBoard().getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        Figure capturedFigure = game.getBoard().getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow());
        moveSelectedFigureToSelectedSquare(game.getBoard(), boardMove, movingFigure);

        updateGameComponentsAfterCaptureMove(game, boardMove, movingFigure, capturedFigure);
    }

    private static void makeNormalPawnMove(Game game, BoardMove boardMove) {
        Figure movingFigure = game.getBoard().getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        moveSelectedFigureToSelectedSquare(game.getBoard(), boardMove, movingFigure);

        updateGameComponentsAfterNormalPawnMove(game);
    }


    private static void makeNonSpecialMove(Game game, BoardMove boardMove) {
        Figure movingFigure = game.getBoard().getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        moveSelectedFigureToSelectedSquare(game.getBoard(), boardMove, movingFigure);

        updateGameComponentsAfterNonSpecialMove(game, boardMove, movingFigure);
    }


    private static void moveSelectedFigureToSelectedSquare(Board board, BoardMove boardMove, Figure movingFigure) {
        setNoneFigureToSelectedSquare(board, boardMove.getSourceColumn(), boardMove.getSourceRow());
        setSelectedFigureToSelectedSquare(board, boardMove.getDestinationColumn(), boardMove.getDestinationRow(), movingFigure);
    }

    private static void setNoneFigureToSelectedSquare(Board board, int column, int row) {
        setSelectedFigureToSelectedSquare(board, column, row, new None());
    }

    private static void setSelectedFigureToSelectedSquare(Board board, int column, int row, Figure figure) {
        board.setFigure(column, row, figure);
    }
}
