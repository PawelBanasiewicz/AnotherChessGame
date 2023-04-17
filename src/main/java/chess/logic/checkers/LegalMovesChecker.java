package chess.logic.checkers;

import chess.game.Game;
import chess.logic.board.Board;
import chess.logic.board.BoardSquare;
import chess.logic.board.Square;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.Knight;
import chess.logic.figures.None;
import chess.logic.figures.Pawn;
import chess.logic.figures.Rook;
import chess.logic.moves.BoardMove;
import chess.logic.moves.CastleMove;
import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static chess.utils.Constants.BLACK_CASTLE_ROW;
import static chess.utils.Constants.BLACK_KING_LONG_CASTLE_SQUARE;
import static chess.utils.Constants.BLACK_KING_SHORT_CASTLE_SQUARE;
import static chess.utils.Constants.BLACK_PAWN_DOUBLE_SQUARE_MOVE_ROW;
import static chess.utils.Constants.BLACK_PAWN_START_ROW;
import static chess.utils.Constants.BLACK_ROOK_LONG_CASTLE_SQUARE;
import static chess.utils.Constants.BLACK_ROOK_SHORT_CASTLE_SQUARE;
import static chess.utils.Constants.FIRST_COLUMN;
import static chess.utils.Constants.FIRST_ROW;
import static chess.utils.Constants.KING_LONG_CASTLE_COLUMN;
import static chess.utils.Constants.KING_SHORT_CASTLE_COLUMN;
import static chess.utils.Constants.LAST_COLUMN;
import static chess.utils.Constants.LAST_ROW;
import static chess.utils.Constants.ROOK_LONG_CASTLE_COLUMN;
import static chess.utils.Constants.ROOK_SHORT_CASTLE_COLUMN;
import static chess.utils.Constants.WHITE_CASTLE_ROW;
import static chess.utils.Constants.WHITE_KING_LONG_CASTLE_SQUARE;
import static chess.utils.Constants.WHITE_KING_SHORT_CASTLE_SQUARE;
import static chess.utils.Constants.WHITE_PAWN_DOUBLE_SQUARE_MOVE_ROW;
import static chess.utils.Constants.WHITE_PAWN_START_ROW;
import static chess.utils.Constants.WHITE_ROOK_LONG_CASTLE_SQUARE;
import static chess.utils.Constants.WHITE_ROOK_SHORT_CASTLE_SQUARE;

public class LegalMovesChecker {
    private LegalMovesChecker() {
    }


    public static List<BoardMove> getAllPossibleMovesFromSelectedPosition(Game game, int sourceColumn, int sourceRow) {
        List<BoardMove> allPossibleMovesFromSelectedPosition = new ArrayList<>();
        Figure figure = game.getBoard().getFigure(sourceColumn, sourceRow);

        if (figure.getColor() != game.getWhoseMove()) {
            return Collections.emptyList();
        }
        if (game.getFiguresGivingCheck().size() > 1 && !(figure instanceof King)) {
            return Collections.emptyList();
        }

        List<FigureMove> allFigureMovesRegardingToBoard = figure.getAllMovesRegardingToBoard();

        if (figure instanceof King && ((King) figure).isFirstMove() && (!((King) figure).isInCheck())) {
            addCastlesIfPossible(game, sourceColumn, sourceRow, allPossibleMovesFromSelectedPosition, figure);
        }

        boolean kingIsInCheck = game.getFiguresGivingCheck().size() > 0;
        Set<Square> squaresToBlockCheckOrCaptureCheckingPiece = null;
        if (kingIsInCheck) {
            squaresToBlockCheckOrCaptureCheckingPiece = createSquaresToBlockCheckOrCaptureCheckingPiece(game);
        }

        for (FigureMove figureMove : allFigureMovesRegardingToBoard) {
            Board board = game.getBoard();
            int destinationColumn = sourceColumn + figureMove.getNumberOfColumnsToMove();
            int destinationRow = sourceRow + figureMove.getNumberOfRowsToMove();
            BoardMove boardMoveToCheck = new BoardMove(sourceColumn, sourceRow, destinationColumn, destinationRow);
            Square destinationSquareToCheck = new Square(destinationColumn, destinationRow);

            if (isMoveInBounds(destinationColumn, destinationRow) && targetFieldIsEmptyOrEnemy(board, boardMoveToCheck) && (figure.canJump() || pathIsClear(board, boardMoveToCheck))) {
                if (figure instanceof King) {
                    if (kingMoveIsPossible(game, boardMoveToCheck)) {
                        allPossibleMovesFromSelectedPosition.add(boardMoveToCheck);
                    }
                    continue;
                }

                if (figure.isPinned()) {
                    if (figure instanceof Knight) {
                        continue;
                    }

                    if (figure instanceof Pawn && isMoveInColorDirection(board, boardMoveToCheck)) {
                        if (isMoveInColorDirection(board, boardMoveToCheck)) {
                            if ((pawnMoveIsCapturePawnMove(board, game.getPossibleEnPassantSquare(), boardMoveToCheck) && capturePinningPieceIsPossible(figure, boardMoveToCheck)) ||
                                    (pawnMoveIsTowardPinningPiece(figure, destinationColumn) && pawnMoveIsOneSquareMove(board, boardMoveToCheck)) ||
                                    (pawnMoveIsTowardPinningPiece(figure, destinationColumn) && pawnMoveIsTwoSquaresMove(board, figure, boardMoveToCheck))) {
                                if (!kingIsInCheck || checkingDestinationSquareIsContainedInBlockCheckSquares(squaresToBlockCheckOrCaptureCheckingPiece, destinationSquareToCheck)) {
                                    allPossibleMovesFromSelectedPosition.add(boardMoveToCheck);
                                }
                            }
                            continue;
                        }
                    }

                    int pinningFigureColumn = figure.getPinDetails().getAttackingFigureColumn();
                    int pinningFigureRow = figure.getPinDetails().getAttackingFigureRow();

                    int deltaColumn = (sourceColumn == pinningFigureColumn) ? 0 : ((sourceColumn > pinningFigureColumn) ? 1 : -1);
                    int deltaRow = (sourceRow == pinningFigureRow) ? 0 : ((sourceRow > pinningFigureRow) ? 1 : -1);


                    if (moveIsTowardsPinningPiece(deltaColumn, deltaRow, sourceColumn, sourceRow, destinationColumn, destinationRow)) {
                        if (!kingIsInCheck || checkingDestinationSquareIsContainedInBlockCheckSquares(squaresToBlockCheckOrCaptureCheckingPiece, destinationSquareToCheck)) {
                            allPossibleMovesFromSelectedPosition.add(boardMoveToCheck);
                        }
                    }
                } else {
                    if (figure instanceof Pawn) {
                        if (isMoveInColorDirection(board, boardMoveToCheck)) {
                            if (pawnMoveIsCapturePawnMove(board, game.getPossibleEnPassantSquare(), boardMoveToCheck) ||
                                    pawnMoveIsOneSquareMove(board, boardMoveToCheck) ||
                                    pawnMoveIsTwoSquaresMove(board, figure, boardMoveToCheck)) {
                                if (!kingIsInCheck || checkingDestinationSquareIsContainedInBlockCheckSquares(squaresToBlockCheckOrCaptureCheckingPiece, destinationSquareToCheck)) {
                                    allPossibleMovesFromSelectedPosition.add(boardMoveToCheck);
                                }
                            }
                        }
                        continue;
                    }
                    if (!kingIsInCheck || checkingDestinationSquareIsContainedInBlockCheckSquares(squaresToBlockCheckOrCaptureCheckingPiece, destinationSquareToCheck)) {
                        allPossibleMovesFromSelectedPosition.add(boardMoveToCheck);
                    }
                }
            }
        }
        return allPossibleMovesFromSelectedPosition;
    }

    private static Set<Square> createSquaresToBlockCheckOrCaptureCheckingPiece(Game game) {
        Set<Square> squaresToBlockCheck = new HashSet<>();
        int figureGivingCheckColumn = game.getFiguresGivingCheck().keySet().iterator().next().getColumn();
        int figureGivingCheckRow = game.getFiguresGivingCheck().keySet().iterator().next().getRow();

        if (game.getBoard().getFigure(figureGivingCheckColumn, figureGivingCheckRow) instanceof Knight) {
            squaresToBlockCheck.add(new Square(figureGivingCheckColumn, figureGivingCheckRow));
            return squaresToBlockCheck;
        }

        int kingColumn;
        int kingRow;

        if (game.getWhoseMove() == FigureColor.WHITE) {
            kingColumn = game.getKingsPositions().getWhiteKingColumn();
            kingRow = game.getKingsPositions().getWhiteKingRow();
        } else {
            kingColumn = game.getKingsPositions().getBlackKingColumn();
            kingRow = game.getKingsPositions().getBlackKingRow();
        }

        int deltaColumn = (kingColumn == figureGivingCheckColumn) ? 0 : ((kingColumn > figureGivingCheckColumn) ? 1 : -1);
        int deltaRow = (kingRow == figureGivingCheckRow) ? 0 : ((kingRow > figureGivingCheckRow) ? 1 : -1);

        int currentColumn = figureGivingCheckColumn;
        int currentRow = figureGivingCheckRow;

        while (!(currentColumn == kingColumn && currentRow == kingRow)) {
            squaresToBlockCheck.add(new Square(currentColumn, currentRow));
            currentColumn += deltaColumn;
            currentRow += deltaRow;
        }

        return squaresToBlockCheck;
    }

    private static boolean checkingDestinationSquareIsContainedInBlockCheckSquares(Set<Square> squaresToBlockCheckOrCaptureCheckingPiece, Square destinationSquareToCheck) {
        return squaresToBlockCheckOrCaptureCheckingPiece.contains(destinationSquareToCheck);
    }

    private static boolean moveIsTowardsPinningPiece(int deltaColumn, int deltaRow, int sourceColumn, int sourceRow, int destinationColumn, int destinationRow) {
        return deltaColumn == 1 && deltaRow == 1 && sourceColumn > destinationColumn && sourceRow > destinationRow ||
                deltaColumn == 0 && deltaRow == 1 && sourceColumn == destinationColumn && sourceRow > destinationRow ||
                deltaColumn == -1 && deltaRow == 1 && sourceColumn < destinationColumn && sourceRow > destinationRow ||
                deltaColumn == -1 && deltaRow == 0 && sourceColumn < destinationColumn && sourceRow == destinationRow ||
                deltaColumn == -1 && deltaRow == -1 && sourceColumn < destinationColumn && sourceRow < destinationRow ||
                deltaColumn == 0 && deltaRow == -1 && sourceColumn == destinationColumn && sourceRow < destinationRow ||
                deltaColumn == 1 && deltaRow == -1 && sourceColumn > destinationColumn && sourceRow < destinationRow ||
                deltaColumn == 1 && deltaRow == 0 && sourceColumn > destinationColumn && sourceRow == destinationRow;
    }

    private static boolean pawnMoveIsTowardPinningPiece(Figure pawn, int destinationColumn) {
        return destinationColumn == pawn.getPinDetails().getAttackingFigureColumn();

    }

    private static boolean capturePinningPieceIsPossible(Figure figure, BoardMove boardMove) {
        return boardMove.getDestinationColumn() == figure.getPinDetails().getAttackingFigureColumn() &&
                boardMove.getDestinationRow() == figure.getPinDetails().getAttackingFigureRow();
    }

    private static boolean pawnMoveIsOneSquareMove(Board board, BoardMove boardMove) {
        return boardMove.getSourceColumn() == boardMove.getDestinationColumn() &&
                board.getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow()) instanceof None &&
                Math.abs(boardMove.getSourceRow() - boardMove.getDestinationRow()) == 1;
    }

    private static boolean pawnMoveIsTwoSquaresMove(Board board, Figure pawn, BoardMove boardMove) {
        Figure destinationSquare = board.getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow());

        if (destinationSquare instanceof None && boardMove.getSourceColumn() == boardMove.getDestinationColumn()) {
            if (pawn.getColor() == FigureColor.WHITE) {
                return boardMove.getDestinationRow() == WHITE_PAWN_DOUBLE_SQUARE_MOVE_ROW && boardMove.getSourceRow() == WHITE_PAWN_START_ROW;
            } else {
                return boardMove.getDestinationRow() == BLACK_PAWN_DOUBLE_SQUARE_MOVE_ROW && boardMove.getSourceRow() == BLACK_PAWN_START_ROW;
            }
        }
        return false;
    }

    private static boolean pawnMoveIsCapturePawnMove(Board board, Square possibleEnPassantSquare, BoardMove boardMoveToCheck) {
        return boardMoveToCheck.getDestinationColumn() != boardMoveToCheck.getSourceColumn() && targetFileIsEnemy(board, boardMoveToCheck) ||
                (boardMoveToCheck.getDestinationColumn() == possibleEnPassantSquare.getColumn() && boardMoveToCheck.getDestinationRow() == possibleEnPassantSquare.getRow());
    }


    private static boolean kingMoveIsPossible(Game game, BoardMove boardMove) {
        BoardSquare checkingBoardSquare = game.getBoard().getBoardSquare(boardMove.getDestinationColumn(), boardMove.getDestinationRow());

        if (game.getWhoseMove() == FigureColor.WHITE) {
            return !checkingBoardSquare.isControlledByBlack() && checkingBoardSquare.getFigure().getColor() != FigureColor.WHITE && checkingBoardSquare.isAvailableForWhiteKing();
        } else {
            return !checkingBoardSquare.isControlledByWhite() && checkingBoardSquare.getFigure().getColor() != FigureColor.BLACK && checkingBoardSquare.isAvailableForBlackKing();
        }
    }

    private static void addCastlesIfPossible(Game game, int sourceColumn, int sourceRow, List<BoardMove> allPossibleMovesFromSelectedPosition, Figure figure) {
        if (figure.getColor() == FigureColor.WHITE) {
            if (game.getCastlingRights().canWhiteShortCastle() && game.getRooksStartPositions().isWhiteShortCastleRookAvailable()) {
                addWhiteShortCastleIfPossible(game, sourceColumn, sourceRow, allPossibleMovesFromSelectedPosition, figure);
            }

            if (game.getCastlingRights().canWhiteLongCastle() && game.getRooksStartPositions().isWhiteLongCastleRookAvailable()) {
                addWhiteLongCastleIfPossible(game, sourceColumn, sourceRow, allPossibleMovesFromSelectedPosition, figure);
            }
        }

        if (figure.getColor() == FigureColor.BLACK) {
            if (game.getCastlingRights().canBlackShortCastle() && game.getRooksStartPositions().isBlackShortCastleRookAvailable()) {
                addBlackShortCastleIfPossible(game, sourceColumn, sourceRow, allPossibleMovesFromSelectedPosition, figure);
            }

            if (game.getCastlingRights().canBlackLongCastle() && game.getRooksStartPositions().isBlackLongCastleRookAvailable()) {
                addBlackLongCastleIfPossible(game, sourceColumn, sourceRow, allPossibleMovesFromSelectedPosition, figure);
            }
        }
    }

    private static void addWhiteShortCastleIfPossible(Game game, int sourceColumn, int sourceRow, List<BoardMove> allPossibleMovesFromSelectedPosition, Figure figure) {
        int rookColumn = game.getRooksStartPositions().getWhiteShortCastleRookColumn();
        int rookRow = game.getRooksStartPositions().getWhiteShortCastleRookRow();
        Rook rook = (Rook) game.getBoard().getFigure(rookColumn, rookRow);


        BoardMove moveFromKingToRook = new BoardMove(sourceColumn, sourceRow, rookColumn, rookRow);
        BoardMove moveFromKingToKingCastleSquare = new BoardMove(sourceColumn, sourceRow, KING_SHORT_CASTLE_COLUMN, WHITE_CASTLE_ROW);
        BoardMove moveFromRookToKingCastleSquare = new BoardMove(rookColumn, rookRow, KING_SHORT_CASTLE_COLUMN, WHITE_CASTLE_ROW);

        Square rookSquare = new Square(rookColumn, rookRow);
        Square kingSquare = new Square(sourceColumn, sourceRow);

        if (rook.isFirstMove() && pathIsClear(game.getBoard(), moveFromKingToRook) &&
                (pathIsClear(game.getBoard(), moveFromKingToKingCastleSquare) || pathIsClear(game.getBoard(), moveFromRookToKingCastleSquare)) &&
                castleSquaresAreAvailable(game.getBoard(), kingSquare, rookSquare, figure.getColor(), true)) {
            allPossibleMovesFromSelectedPosition.add(new CastleMove(sourceColumn, sourceRow, KING_SHORT_CASTLE_COLUMN, WHITE_CASTLE_ROW,
                    rookColumn, rookRow, ROOK_SHORT_CASTLE_COLUMN, WHITE_CASTLE_ROW));
        }
    }

    private static void addWhiteLongCastleIfPossible(Game game, int sourceColumn, int sourceRow, List<BoardMove> allPossibleMovesFromSelectedPosition, Figure figure) {
        int rookColumn = game.getRooksStartPositions().getWhiteLongCastleRookColumn();
        int rookRow = game.getRooksStartPositions().getWhiteLongCastleRookRow();
        Rook rook = (Rook) game.getBoard().getFigure(rookColumn, rookRow);

        BoardMove moveFromKingToRook = new BoardMove(sourceColumn, sourceRow, rookColumn, rookRow);
        BoardMove moveFromKingToKingCastleSquare = new BoardMove(sourceColumn, sourceRow, KING_LONG_CASTLE_COLUMN, WHITE_CASTLE_ROW);
        BoardMove moveFromRookToKingCastleSquare = new BoardMove(rookColumn, rookRow, KING_LONG_CASTLE_COLUMN, WHITE_CASTLE_ROW);

        Square rookSquare = new Square(rookColumn, rookRow);
        Square kingSquare = new Square(sourceColumn, sourceRow);

        if (rook.isFirstMove() && pathIsClear(game.getBoard(), moveFromKingToRook) &&
                (pathIsClear(game.getBoard(), moveFromKingToKingCastleSquare) || pathIsClear(game.getBoard(), moveFromRookToKingCastleSquare)) &&
                castleSquaresAreAvailable(game.getBoard(), kingSquare, rookSquare, figure.getColor(), false)) {
            allPossibleMovesFromSelectedPosition.add(new CastleMove(sourceColumn, sourceRow, KING_LONG_CASTLE_COLUMN, WHITE_CASTLE_ROW,
                    rookColumn, rookRow, ROOK_LONG_CASTLE_COLUMN, WHITE_CASTLE_ROW));
        }
    }

    private static void addBlackShortCastleIfPossible(Game game, int sourceColumn, int sourceRow, List<BoardMove> allPossibleMovesFromSelectedPosition, Figure figure) {
        int rookColumn = game.getRooksStartPositions().getBlackShortCastleRookColumn();
        int rookRow = game.getRooksStartPositions().getBlackShortCastleRookRow();
        Rook rook = (Rook) game.getBoard().getFigure(rookColumn, rookRow);

        BoardMove moveFromKingToRook = new BoardMove(sourceColumn, sourceRow, rookColumn, rookRow);
        BoardMove moveFromKingToKingCastleSquare = new BoardMove(sourceColumn, sourceRow, KING_SHORT_CASTLE_COLUMN, BLACK_CASTLE_ROW);
        BoardMove moveFromRookToKingCastleSquare = new BoardMove(rookColumn, rookRow, KING_SHORT_CASTLE_COLUMN, BLACK_CASTLE_ROW);

        Square rookSquare = new Square(rookColumn, rookRow);
        Square kingSquare = new Square(sourceColumn, sourceRow);

        if (rook.isFirstMove() && pathIsClear(game.getBoard(), moveFromKingToRook) &&
                (pathIsClear(game.getBoard(), moveFromKingToKingCastleSquare) || pathIsClear(game.getBoard(), moveFromRookToKingCastleSquare)) &&
                castleSquaresAreAvailable(game.getBoard(), kingSquare, rookSquare, figure.getColor(), true)) {
            allPossibleMovesFromSelectedPosition.add(new CastleMove(sourceColumn, sourceRow, KING_SHORT_CASTLE_COLUMN, BLACK_CASTLE_ROW,
                    rookColumn, rookRow, ROOK_SHORT_CASTLE_COLUMN, BLACK_CASTLE_ROW));
        }
    }

    private static void addBlackLongCastleIfPossible(Game game, int sourceColumn, int sourceRow, List<BoardMove> allPossibleMovesFromSelectedPosition, Figure figure) {
        int rookColumn = game.getRooksStartPositions().getBlackLongCastleRookColumn();
        int rookRow = game.getRooksStartPositions().getBlackLongCastleRookRow();
        Rook rook = (Rook) game.getBoard().getFigure(rookColumn, rookRow);

        BoardMove moveFromKingToRook = new BoardMove(sourceColumn, sourceRow, rookColumn, rookRow);
        BoardMove moveFromKingToKingCastleSquare = new BoardMove(sourceColumn, sourceRow, KING_LONG_CASTLE_COLUMN, BLACK_CASTLE_ROW);
        BoardMove moveFromRookToKingCastleSquare = new BoardMove(rookColumn, rookRow, KING_LONG_CASTLE_COLUMN, BLACK_CASTLE_ROW);

        Square rookSquare = new Square(rookColumn, rookRow);
        Square kingSquare = new Square(sourceColumn, sourceRow);

        if (rook.isFirstMove() && pathIsClear(game.getBoard(), moveFromKingToRook) &&
                (pathIsClear(game.getBoard(), moveFromKingToKingCastleSquare) || pathIsClear(game.getBoard(), moveFromRookToKingCastleSquare)) &&
                castleSquaresAreAvailable(game.getBoard(), kingSquare, rookSquare, figure.getColor(), false)) {
            allPossibleMovesFromSelectedPosition.add(new CastleMove(sourceColumn, sourceRow, KING_LONG_CASTLE_COLUMN, BLACK_CASTLE_ROW,
                    rookColumn, rookRow, ROOK_LONG_CASTLE_COLUMN, BLACK_CASTLE_ROW));
        }
    }


    private static boolean castleSquaresAreAvailable(Board board, Square kingSquare, Square rookSquare, FigureColor figureColor, boolean isShortCastle) {
        BoardSquare kingCastleSquare;
        BoardSquare rookCastleSquare;

        if (isShortCastle) {
            if (figureColor == FigureColor.WHITE) {
                kingCastleSquare = board.getBoardSquare(WHITE_KING_SHORT_CASTLE_SQUARE);
                rookCastleSquare = board.getBoardSquare(WHITE_ROOK_SHORT_CASTLE_SQUARE);

                return !kingCastleSquare.isControlledByBlack() && !rookCastleSquare.isControlledByBlack() &&
                        castleSquaresAreEmptyOrKingOrRooksAlreadyAreOnTheirCastleSquares(board, kingSquare, rookSquare, figureColor, kingCastleSquare, rookCastleSquare, true);
            } else {
                kingCastleSquare = board.getBoardSquare(BLACK_KING_SHORT_CASTLE_SQUARE);
                rookCastleSquare = board.getBoardSquare(BLACK_ROOK_SHORT_CASTLE_SQUARE);

                return !kingCastleSquare.isControlledByWhite() && !rookCastleSquare.isControlledByWhite() &&
                        castleSquaresAreEmptyOrKingOrRooksAlreadyAreOnTheirCastleSquares(board, kingSquare, rookSquare, figureColor, kingCastleSquare, rookCastleSquare, true);
            }
        } else {
            if (figureColor == FigureColor.WHITE) {
                kingCastleSquare = board.getBoardSquare(WHITE_KING_LONG_CASTLE_SQUARE);
                rookCastleSquare = board.getBoardSquare(WHITE_ROOK_LONG_CASTLE_SQUARE);

                return !kingCastleSquare.isControlledByBlack() && !rookCastleSquare.isControlledByBlack() &&
                        castleSquaresAreEmptyOrKingOrRooksAlreadyAreOnTheirCastleSquares(board, kingSquare, rookSquare, figureColor, kingCastleSquare, rookCastleSquare, false);
            } else {
                kingCastleSquare = board.getBoardSquare(BLACK_KING_LONG_CASTLE_SQUARE);
                rookCastleSquare = board.getBoardSquare(BLACK_ROOK_LONG_CASTLE_SQUARE);

                return !kingCastleSquare.isControlledByWhite() && !rookCastleSquare.isControlledByWhite() &&
                        castleSquaresAreEmptyOrKingOrRooksAlreadyAreOnTheirCastleSquares(board, kingSquare, rookSquare, figureColor, kingCastleSquare, rookCastleSquare, false);
            }
        }
    }

    private static boolean castleSquaresAreEmptyOrKingOrRooksAlreadyAreOnTheirCastleSquares(Board board, Square kingSquare, Square rookSquare, FigureColor figureColor,
                                                                                            BoardSquare kingCastleSquare, BoardSquare rookCastleSquare, boolean isShortCastle) {
        return kingAndRookCastleSquaresAreEmpty(kingCastleSquare, rookCastleSquare) || kingOrRookAreOnTheirCastleSquares(board, kingSquare, rookSquare, figureColor, isShortCastle);
    }


    private static boolean kingAndRookCastleSquaresAreEmpty(BoardSquare kingCastleSquare, BoardSquare rookCastleSquare) {
        return squareIsEmpty(kingCastleSquare) && squareIsEmpty(rookCastleSquare);
    }

    private static boolean kingOrRookAreOnTheirCastleSquares(Board board, Square kingSquare, Square rookSquare, FigureColor figureColor, boolean isShortCastle) {
        return ((kingIsAlreadyOnHisCastleSquare(kingSquare, figureColor, isShortCastle) && rookIsAlreadyOnHisCastleSquare(rookSquare, figureColor, isShortCastle)) ||
                (kingIsAOnRookCastleSquare(kingSquare, figureColor, isShortCastle) && rookIsOnKingCastleSquare(rookSquare, figureColor, isShortCastle)) ||
                (kingIsAlreadyOnHisCastleSquare(kingSquare, figureColor, isShortCastle) && rookCastleSquareIsEmpty(board, figureColor, isShortCastle)) ||
                (rookIsAlreadyOnHisCastleSquare(rookSquare, figureColor, isShortCastle) && kingCastleSquareIsEmpty(board, figureColor, isShortCastle)) ||
                (kingIsAOnRookCastleSquare(kingSquare, figureColor, isShortCastle) && kingCastleSquareIsEmpty(board, figureColor, isShortCastle)) ||
                (rookIsOnKingCastleSquare(rookSquare, figureColor, isShortCastle) && rookCastleSquareIsEmpty(board, figureColor, isShortCastle)));
    }

    private static boolean squareIsEmpty(BoardSquare squareToCheck) {
        return squareToCheck.getFigure() instanceof None;
    }

    private static boolean kingIsAlreadyOnHisCastleSquare(Square kingSquare, FigureColor figureColor, boolean isShortCastle) {
        if (figureColor == FigureColor.WHITE) {
            return isShortCastle ? kingSquare.equals(WHITE_KING_SHORT_CASTLE_SQUARE) : kingSquare.equals(WHITE_KING_LONG_CASTLE_SQUARE);
        } else {
            return isShortCastle ? kingSquare.equals(BLACK_KING_SHORT_CASTLE_SQUARE) : kingSquare.equals(BLACK_KING_LONG_CASTLE_SQUARE);
        }
    }

    private static boolean kingIsAOnRookCastleSquare(Square kingSquare, FigureColor figureColor, boolean isShortCastle) {
        if (figureColor == FigureColor.WHITE) {
            return isShortCastle ? kingSquare.equals(WHITE_ROOK_SHORT_CASTLE_SQUARE) : kingSquare.equals(WHITE_ROOK_LONG_CASTLE_SQUARE);
        } else {
            return isShortCastle ? kingSquare.equals(BLACK_ROOK_SHORT_CASTLE_SQUARE) : kingSquare.equals(BLACK_ROOK_LONG_CASTLE_SQUARE);
        }
    }

    private static boolean rookIsAlreadyOnHisCastleSquare(Square rookSquare, FigureColor figureColor, boolean isShortCastle) {
        if (figureColor == FigureColor.WHITE) {
            return isShortCastle ? rookSquare.equals(WHITE_ROOK_SHORT_CASTLE_SQUARE) : rookSquare.equals(WHITE_ROOK_LONG_CASTLE_SQUARE);
        } else {
            return isShortCastle ? rookSquare.equals(BLACK_ROOK_SHORT_CASTLE_SQUARE) : rookSquare.equals(BLACK_ROOK_LONG_CASTLE_SQUARE);
        }
    }

    private static boolean rookIsOnKingCastleSquare(Square rookSquare, FigureColor figureColor, boolean isShortCastle) {
        if (figureColor == FigureColor.WHITE) {
            return isShortCastle ? rookSquare.equals(WHITE_KING_SHORT_CASTLE_SQUARE) : rookSquare.equals(WHITE_KING_LONG_CASTLE_SQUARE);
        } else {
            return isShortCastle ? rookSquare.equals(BLACK_KING_SHORT_CASTLE_SQUARE) : rookSquare.equals(BLACK_KING_LONG_CASTLE_SQUARE);
        }
    }

    private static boolean kingCastleSquareIsEmpty(Board board, FigureColor figureColor, boolean isShortCastle) {
        BoardSquare kingBoardSquare = null;

        if (figureColor == FigureColor.WHITE && isShortCastle) {
            kingBoardSquare = board.getBoardSquare(WHITE_KING_SHORT_CASTLE_SQUARE);
        }
        if (figureColor == FigureColor.WHITE && !isShortCastle) {
            kingBoardSquare = board.getBoardSquare(WHITE_KING_LONG_CASTLE_SQUARE);
        }
        if (figureColor == FigureColor.BLACK && isShortCastle) {
            kingBoardSquare = board.getBoardSquare(BLACK_KING_SHORT_CASTLE_SQUARE);
        }
        if (figureColor == FigureColor.BLACK && !isShortCastle) {
            kingBoardSquare = board.getBoardSquare(BLACK_KING_LONG_CASTLE_SQUARE);
        }

        assert kingBoardSquare != null;
        return kingBoardSquare.getFigure() instanceof None;
    }

    private static boolean rookCastleSquareIsEmpty(Board board, FigureColor figureColor, boolean isShortCastle) {
        BoardSquare rookBoardSquare = null;

        if (figureColor == FigureColor.WHITE && isShortCastle) {
            rookBoardSquare = board.getBoardSquare(WHITE_ROOK_SHORT_CASTLE_SQUARE);
        }
        if (figureColor == FigureColor.WHITE && !isShortCastle) {
            rookBoardSquare = board.getBoardSquare(WHITE_ROOK_LONG_CASTLE_SQUARE);
        }
        if (figureColor == FigureColor.BLACK && isShortCastle) {
            rookBoardSquare = board.getBoardSquare(BLACK_ROOK_SHORT_CASTLE_SQUARE);
        }
        if (figureColor == FigureColor.BLACK && !isShortCastle) {
            rookBoardSquare = board.getBoardSquare(BLACK_ROOK_LONG_CASTLE_SQUARE);
        }

        assert rookBoardSquare != null;
        return rookBoardSquare.getFigure() instanceof None;
    }

    //////////////////////////////////////////////////////////


    private static boolean isMoveInBounds(int destinationColumn, int destinationRow) {
        return destinationColumn < LAST_COLUMN && destinationColumn >= FIRST_COLUMN && destinationRow < LAST_ROW && destinationRow >= FIRST_ROW;
    }


    private static boolean targetFieldIsEmptyOrEnemy(Board board, BoardMove boardMove) {
        Figure figureToCheck = board.getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow());
        Figure sourceFigure = board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());

        return (figureToCheck instanceof None) || (sourceFigure.getColor() != figureToCheck.getColor());
    }

    public static boolean targetFileIsEnemy(Board board, BoardMove boardMove) {
        Figure figureToCheck = board.getFigure(boardMove.getDestinationColumn(), boardMove.getDestinationRow());
        Figure sourceFigure = board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow());

        return !(figureToCheck instanceof None) && figureToCheck.getColor() != sourceFigure.getColor();
    }

    public static boolean pathIsClear(Board board, BoardMove boardMove) {
        boolean result = true;

        int sourceColumn = boardMove.getSourceColumn();
        int sourceRow = boardMove.getSourceRow();
        int destinationColumn = boardMove.getDestinationColumn();
        int destinationRow = boardMove.getDestinationRow();


        int deltaColumn = (destinationColumn == sourceColumn) ? 0 : ((destinationColumn > sourceColumn) ? 1 : -1);
        int deltaRow = (destinationRow == sourceRow) ? 0 : ((destinationRow > sourceRow) ? 1 : -1);
        int currentColumn = sourceColumn + deltaColumn;
        int currentRow = sourceRow + deltaRow;

        while (!(currentColumn == destinationColumn && currentRow == destinationRow)) {
            if (!(board.getFigure(currentColumn, currentRow) instanceof None)) {
                result = false;
                break;
            }
            currentColumn += deltaColumn;
            currentRow += deltaRow;
        }
        return result;
    }

    public static boolean isMoveInColorDirection(Board board, BoardMove boardMove) {
        boolean isMoveInColorDirection;

        if (board.getFigure(boardMove.getSourceColumn(), boardMove.getSourceRow()).getColor() == FigureColor.WHITE)
            isMoveInColorDirection = (boardMove.getDestinationRow() < boardMove.getSourceRow());
        else {
            isMoveInColorDirection = (boardMove.getDestinationRow() > boardMove.getSourceRow());
        }

        return isMoveInColorDirection;
    }
}
