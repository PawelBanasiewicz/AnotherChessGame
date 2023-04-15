package chess.logic.board;

import chess.game.Game;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.Knight;
import chess.logic.figures.None;
import chess.logic.figures.Pawn;
import chess.logic.moves.BoardMove;
import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;

import static chess.logic.checkers.LegalMovesChecker.isMoveInColorDirection;
import static chess.logic.checkers.LegalMovesChecker.pathIsClear;
import static chess.logic.figures.FigureColor.getOppositeColor;
import static chess.utils.Constants.FIRST_COLUMN;
import static chess.utils.Constants.FIRST_ROW;
import static chess.utils.Constants.LAST_COLUMN;
import static chess.utils.Constants.LAST_ROW;

public class BoardUpdater {

    public static void updateBoard(Game game) {
        updateControlledSquaresKingsPositionsAndFiguresGivingCheck(game);
        updatePines(game);
    }

    private static void updateControlledSquaresKingsPositionsAndFiguresGivingCheck(Game game) {
        Board board = game.getBoard();

        for (int row = FIRST_ROW; row < LAST_ROW; row++) {
            for (int column = FIRST_COLUMN; column < LAST_COLUMN; column++) {
                Figure figure = board.getFigure(column, row);

                if (!(figure instanceof None)) {
                    List<BoardMove> fullMovementsPathsFromSelectedPosition = getAllMovesContainingControlledSquaresFromSelectedPositionAndUpdateAvailabilityOfSquares(game, column, row);
                    for (BoardMove boardMove : fullMovementsPathsFromSelectedPosition) {
                        int destinationColumn = boardMove.getDestinationColumn();
                        int destinationRow = boardMove.getDestinationRow();

                        if (figure.getColor() == FigureColor.WHITE) {
                            int blackKingColumn = game.getKingsPositions().getBlackKingColumn();
                            int blackKingRow = game.getKingsPositions().getBlackKingRow();

                            if (destinationColumn == blackKingColumn && destinationRow == blackKingRow) {
                                ((King) game.getBoard().getFigure(blackKingColumn, blackKingRow)).setInCheck(true);
                                game.getFiguresGivingCheck().put(new Square(boardMove.getSourceColumn(), boardMove.getSourceRow()), figure);
                            }
                            board.getBoardSquare(destinationColumn, destinationRow).setControlledByWhite(true);
                        } else {
                            int whiteKingColumn = game.getKingsPositions().getWhiteKingColumn();
                            int whiteKingRow = game.getKingsPositions().getWhiteKingRow();

                            if (destinationColumn == whiteKingColumn && destinationRow == whiteKingRow) {
                                ((King) game.getBoard().getFigure(whiteKingColumn, whiteKingRow)).setInCheck(true);
                                game.getFiguresGivingCheck().put(new Square(boardMove.getSourceColumn(), boardMove.getSourceRow()), figure);
                            }
                            board.getBoardSquare(destinationColumn, destinationRow).setControlledByBlack(true);
                        }
                    }
                }
            }
        }
    }


    private static void updatePines(Game game) {
        Board board = game.getBoard();

        for (int sourceRow = FIRST_ROW; sourceRow < LAST_ROW; sourceRow++) {
            for (int sourceColumn = FIRST_COLUMN; sourceColumn < LAST_COLUMN; sourceColumn++) {
                Figure figure = board.getFigure(sourceColumn, sourceRow);

                if (!(figure instanceof Pawn) && !(figure instanceof None) && !(figure instanceof King) && !(figure instanceof Knight)) {
                    List<FigureMove> allFigureMovesRegardingToBoard = figure.getAllMovesRegardingToBoard();

                    for (FigureMove figureMove : allFigureMovesRegardingToBoard) {
                        int destinationColumn = sourceColumn + figureMove.getNumberOfColumnsToMove();
                        int destinationRow = sourceRow + figureMove.getNumberOfRowsToMove();

                        if (isMoveInBounds(destinationColumn, destinationRow)) {

                            if ((figure.getColor() == FigureColor.WHITE && destinationColumn == game.getKingsPositions().getBlackKingColumn()
                                    && destinationRow == game.getKingsPositions().getBlackKingRow()) ||
                                    (figure.getColor() == FigureColor.BLACK && destinationColumn == game.getKingsPositions().getWhiteKingColumn()
                                            && destinationRow == game.getKingsPositions().getWhiteKingRow())) {

                                int deltaColumn = (sourceColumn == destinationColumn) ? 0 : ((destinationColumn > sourceColumn) ? 1 : -1);
                                int deltaRow = (sourceRow == destinationRow) ? 0 : ((destinationRow > sourceRow) ? 1 : -1);

                                int currentColumn = sourceColumn + deltaColumn;
                                int currentRow = sourceRow + deltaRow;

                                while (!(currentColumn == destinationColumn && currentRow == destinationRow)) {
                                    Figure checkingSquare = board.getFigure(currentColumn, currentRow);

                                    if (!(checkingSquare instanceof None) && checkingSquare.getColor() == getOppositeColor(figure.getColor()) && !(checkingSquare instanceof King)) {
                                        BoardMove boardMoveFromAttackingPieceToBlockingPiece = new BoardMove(sourceColumn, sourceRow, currentColumn, currentRow);
                                        BoardMove boardMoveFromBlockingPieceToKing = new BoardMove(currentColumn, currentRow, destinationColumn, destinationRow);

                                        if (pathIsClear(board, boardMoveFromAttackingPieceToBlockingPiece) && pathIsClear(board, boardMoveFromBlockingPieceToKing)) {
                                            checkingSquare.getPinDetails().setPinDetails(sourceColumn, sourceRow);
                                        }
                                    }
                                    currentColumn += deltaColumn;
                                    currentRow += deltaRow;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static List<BoardMove> getAllMovesContainingControlledSquaresFromSelectedPositionAndUpdateAvailabilityOfSquares(Game game, int sourceColumn, int sourceRow) {
        Figure sourceFigure = game.getBoard().getFigure(sourceColumn, sourceRow);
        List<FigureMove> allFigureMovesRegardingToBoard = sourceFigure.getAllMovesRegardingToBoard();

        List<BoardMove> allMovesContainingControlledSquaresFromSelectedPosition = new ArrayList<>();

        boolean opponentsKingPresentOnTheWay = false;

        for (FigureMove figureMove : allFigureMovesRegardingToBoard) {
            int destinationColumn = sourceColumn + figureMove.getNumberOfColumnsToMove();
            int destinationRow = sourceRow + figureMove.getNumberOfRowsToMove();
            if (isMoveInBounds(destinationColumn, destinationRow)) {
                BoardMove boardMove = new BoardMove(sourceColumn, sourceRow, destinationColumn, destinationRow);
                if ((sourceFigure instanceof Pawn))
                    if (!isMoveInColorDirection(game.getBoard(), boardMove)) {
                        continue;
                    }

                if ((sourceFigure.canJump() || pathIsClear(game.getBoard(), boardMove)))
                    allMovesContainingControlledSquaresFromSelectedPosition.add(boardMove);


                Figure figureToCheck = game.getBoard().getFigure(destinationColumn, destinationRow);
                if (figureToCheck instanceof King && figureToCheck.getColor() == getOppositeColor(sourceFigure.getColor())) {
                    BoardMove boardMoveFromAttackingPieceToKing = new BoardMove(sourceColumn, sourceRow, destinationColumn, destinationRow);
                    if (pathIsClear(game.getBoard(), boardMoveFromAttackingPieceToKing)) {
                        // TODO: should work properly but not exactly as I want
                        opponentsKingPresentOnTheWay = true;
                        int deltaColumn = (sourceColumn == destinationColumn) ? 0 : ((destinationColumn > sourceColumn) ? 1 : -1);
                        int deltaRow = (sourceRow == destinationRow) ? 0 : ((destinationRow > sourceRow) ? 1 : -1);

                    }
                }
                if (opponentsKingPresentOnTheWay) {
                    setKingsUnavailabilityOfSquare(game, sourceFigure, destinationColumn, destinationRow);
                }
            }
        }
        return allMovesContainingControlledSquaresFromSelectedPosition;
    }

    private static void setKingsUnavailabilityOfSquare(Game game, Figure figure, int destinationColumn, int destinationRow) {
        if (figure.getColor() == FigureColor.WHITE) {
            game.getBoard().getBoardSquare(destinationColumn, destinationRow).setUnavailableForBlackKing();
        } else {
            game.getBoard().getBoardSquare(destinationColumn, destinationRow).setUnavailableForWhiteKing();
        }
    }

    public static boolean isMoveInBounds(int destinationColumn, int destinationRow) {
        return destinationColumn <= 7 && destinationColumn >= 0 && destinationRow <= 7 && destinationRow >= 0;
    }
}
