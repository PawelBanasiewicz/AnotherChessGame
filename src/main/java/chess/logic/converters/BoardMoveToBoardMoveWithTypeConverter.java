package chess.logic.converters;

import chess.game.Game;
import chess.logic.board.Board;
import chess.logic.board.Square;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.None;
import chess.logic.figures.Pawn;
import chess.logic.moves.BoardMove;
import chess.logic.moves.BoardMoveType;
import chess.logic.moves.BoardMoveWithType;
import chess.logic.moves.CastleMove;

import static chess.logic.checkers.LegalMovesChecker.targetFileIsEnemy;
import static chess.logic.moves.BoardMoveType.CAPTURE;
import static chess.logic.moves.BoardMoveType.CASTLE;
import static chess.logic.moves.BoardMoveType.ENPASSANT_CAPTURE;
import static chess.logic.moves.BoardMoveType.NON_SPECIAL;
import static chess.logic.moves.BoardMoveType.NORMAL_PAWN_MOVE;
import static chess.logic.moves.BoardMoveType.PROMOTION_MOVE;
import static chess.logic.moves.BoardMoveType.TWO_SQUARES_PAWN_MOVE;
import static chess.utils.Constants.BLACK_PROMOTION_ROW;
import static chess.utils.Constants.WHITE_PROMOTION_ROW;

public class BoardMoveToBoardMoveWithTypeConverter {
    private BoardMoveToBoardMoveWithTypeConverter() {
    }

    public static BoardMoveWithType convertBoardMoveToBoardMoveWithType(Game game, BoardMove boardMove) {
        BoardMoveType boardMoveType = NON_SPECIAL;

        if (boardMoveIsCastle(boardMove)) {
            boardMoveType = CASTLE;
        } else if (boardMoveIsEnpassantCapture(game.getBoard(), game.getPossibleEnPassantSquare(), boardMove)) {
            boardMoveType = ENPASSANT_CAPTURE;
        } else if (boardMoveIsPromotionMove(game.getBoard(), boardMove)) {
            boardMoveType = PROMOTION_MOVE;
        } else if (boardMoveIsTwoSquaresPawnMove(game.getBoard(), boardMove)) {
            boardMoveType = TWO_SQUARES_PAWN_MOVE;
        } else if (boardMoveIsCapture(game.getBoard(), boardMove)) {
            boardMoveType = CAPTURE;
        } else if (boardMoveIsNormalPawnMove(game.getBoard(), boardMove)) {
            boardMoveType = NORMAL_PAWN_MOVE;
        }

        return new BoardMoveWithType(boardMove, boardMoveType);
    }

    private static boolean boardMoveIsCastle(BoardMove boardMove) {
        return boardMove instanceof CastleMove;
    }

    private static boolean boardMoveIsEnpassantCapture(Board board, Square possibleEnPassantSquare, BoardMove boardMove) {
        return board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow()) instanceof Pawn &&
                board.getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow()) instanceof None &&
                boardMove.getDestinationColumn() == possibleEnPassantSquare.getColumn() &&
                boardMove.getDestinationRow() == possibleEnPassantSquare.getRow();
    }

    private static boolean boardMoveIsPromotionMove(Board board, BoardMove boardMove) {
        Figure sourceFigure = board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());
        boolean whitesMove = sourceFigure.getColor() == FigureColor.WHITE;

        return sourceFigure instanceof Pawn &&
                boardMove.getDestinationRow() == (whitesMove ? WHITE_PROMOTION_ROW : BLACK_PROMOTION_ROW);
    }

    private static boolean boardMoveIsTwoSquaresPawnMove(Board board, BoardMove boardMove) {
        return board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow()) instanceof Pawn &&
                Math.abs(boardMove.getSourceRow() - boardMove.getDestinationRow()) == 2;
    }

    private static boolean boardMoveIsCapture(Board board, BoardMove boardMove) {
        return targetFileIsEnemy(board, boardMove);
    }

    private static boolean boardMoveIsNormalPawnMove(Board board, BoardMove boardMove) {
        return board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow()) instanceof Pawn;
    }
}
