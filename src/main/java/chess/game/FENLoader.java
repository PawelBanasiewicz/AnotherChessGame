package chess.game;

import chess.logic.board.Board;
import chess.logic.board.BoardRow;
import chess.logic.board.BoardSquare;
import chess.logic.board.CastlingRights;
import chess.logic.board.KingsPositions;
import chess.logic.board.RooksStartPositions;
import chess.logic.board.Square;
import chess.logic.board.SquareColor;
import chess.logic.figures.Bishop;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.Knight;
import chess.logic.figures.None;
import chess.logic.figures.Pawn;
import chess.logic.figures.Queen;
import chess.logic.figures.Rook;
import chess.utils.PiecesMapManager.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static chess.utils.MoveMaps.stringToIntColumnMap;
import static chess.utils.MoveMaps.stringToIntRowMap;
import static chess.utils.PiecesMapManager.addPieceToPiecesMap;
import static chess.utils.PiecesMapManager.createPiecesMap;

public class FENLoader {
    public static Game loadFromFEN(String FEN) throws Exception {
        List<String> splitFEN = new ArrayList<>(Arrays.asList(FEN.split(" ")));

        Map<Piece, Integer> whitePieces = createPiecesMap();
        Map<Piece, Integer> blackPieces = createPiecesMap();
        KingsPositions kingsPositions = new KingsPositions();
        RooksStartPositions rooksStartPositions = new RooksStartPositions();

        List<BoardRow> figures = FENFigureListToBoardRowList(Arrays.asList(splitFEN.get(0).split("/")), whitePieces, blackPieces, kingsPositions, rooksStartPositions);

        FigureColor whoseMove = loadWhoseMove(splitFEN.get(1));
        CastlingRights castlingRights = loadCastles(splitFEN.get(2));
        Square possibleEnPassantCaptureSquare = loadEnPassantSquare(splitFEN.get(3));
        int halfmoveClock = Integer.parseInt(splitFEN.get(4));
        int fullmoveNumber = Integer.parseInt(splitFEN.get(5));

        return new Game(new Board(figures), whoseMove,
                castlingRights, possibleEnPassantCaptureSquare, halfmoveClock, fullmoveNumber,
                kingsPositions, rooksStartPositions, whitePieces, blackPieces);
    }

    private static FigureColor loadWhoseMove(String whoseMove) throws Exception {
        if (whoseMove.equals("w")) {
            return FigureColor.WHITE;
        }
        if (whoseMove.equals("b")) {
            return FigureColor.BLACK;
        }
        throw new Exception();
    }

    private static CastlingRights loadCastles(String castles) {
        if (castles.equals("-")) {
            return new CastlingRights(false, false, false, false);
        }
        boolean canWhiteShortCastle = castles.contains("K");
        boolean canBlackShortCastle = castles.contains("k");
        boolean canWhiteLongCastle = castles.contains("Q");
        boolean canBlackLongCastle = castles.contains("q");

        return new CastlingRights(canWhiteShortCastle, canBlackShortCastle, canWhiteLongCastle, canBlackLongCastle);
    }

    private static Square loadEnPassantSquare(String enPassantCaptureString) {
        if (enPassantCaptureString.equals("-")) {
            return new Square(-1, -1);
        }
        int column = stringToIntColumnMap.get(enPassantCaptureString.substring(0, 1).toUpperCase());
        int row = stringToIntRowMap.get(enPassantCaptureString.substring(1, 2).toUpperCase());
        return new Square(column, row);
    }

    private static List<BoardRow> FENFigureListToBoardRowList(List<String> rows, Map<Piece, Integer> whitePieces,
                                                              Map<Piece, Integer> blackPieces, KingsPositions kingsPositions, RooksStartPositions rooksStartPositions) throws Exception {
        List<BoardRow> figures = new ArrayList<>();

        for (int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
            String row = rows.get(rowNumber);
            char[] figuresInCharacters = row.toCharArray();
            List<BoardSquare> figuresRow = new ArrayList<>();
            if (figuresInCharacters.length == 1 && figuresInCharacters[0] == '8') {
                for (int i = 0; i < 8; i++) {
                    figuresRow.add(i, new BoardSquare(new None()));
                }
                figures.add(new BoardRow(figuresRow));
                continue;
            }
            int addedFiguresCounter = 0;
            for (char figureInCharacter : figuresInCharacters) {
                if (Character.isDigit(figureInCharacter)) {
                    int loadedDigit = Integer.parseInt(String.valueOf(figureInCharacter));
                    if (loadedDigit < 0 || loadedDigit > 8) throw new Exception();
                    if (loadedDigit > 1) {
                        for (; loadedDigit > 0; loadedDigit--) {
                            figuresRow.add(addedFiguresCounter, new BoardSquare(new None()));
                            addedFiguresCounter++;
                        }
                    } else {
                        figuresRow.add(addedFiguresCounter, new BoardSquare(new None()));
                        addedFiguresCounter++;
                    }
                }
                if (Character.isLetter(figureInCharacter)) {
                    figuresRow.add(addedFiguresCounter, FENFigureCharacterToBoardSquare(figureInCharacter));
                    addedFiguresCounter++;
                }
            }
            updatePiecesMapsAndKingsAndRooksPositions(figuresRow, rowNumber, whitePieces, blackPieces, kingsPositions, rooksStartPositions);
            figures.add(new BoardRow(figuresRow));
        }
        return figures;
    }

    private static BoardSquare FENFigureCharacterToBoardSquare(char figureInCharacter) throws Exception {
        switch (figureInCharacter) {
            case 'p' -> {
                return new BoardSquare(new Pawn(FigureColor.BLACK));
            }
            case 'r' -> {
                return new BoardSquare(new Rook(FigureColor.BLACK));
            }
            case 'n' -> {
                return new BoardSquare(new Knight(FigureColor.BLACK));
            }
            case 'b' -> {
                return new BoardSquare(new Bishop(FigureColor.BLACK));
            }
            case 'q' -> {
                return new BoardSquare(new Queen(FigureColor.BLACK));
            }
            case 'k' -> {
                return new BoardSquare(new King(FigureColor.BLACK));
            }
            case 'P' -> {
                return new BoardSquare(new Pawn(FigureColor.WHITE));
            }
            case 'R' -> {
                return new BoardSquare(new Rook(FigureColor.WHITE));
            }
            case 'N' -> {
                return new BoardSquare(new Knight(FigureColor.WHITE));
            }
            case 'B' -> {
                return new BoardSquare(new Bishop(FigureColor.WHITE));
            }
            case 'Q' -> {
                return new BoardSquare(new Queen(FigureColor.WHITE));
            }
            case 'K' -> {
                return new BoardSquare(new King(FigureColor.WHITE));
            }
        }
        throw new Exception();
    }

    private static void updatePiecesMapsAndKingsAndRooksPositions(List<BoardSquare> figuresRow, int rowNumber, Map<Piece, Integer> whitePieces,
                                                                  Map<Piece, Integer> blackPieces, KingsPositions kingsPositions, RooksStartPositions rooksStartPositions) {
        int kingColumn = -1;
        int kingRow = -1;
        FigureColor kingColor = FigureColor.NONE;

        boolean kingFound = false;

        int firstRookColumn = -1;
        int firstRookRow = -1;
        int secondRookColumn = -1;
        int secondRookRow = -1;
        FigureColor firstRookColor = FigureColor.NONE;
        FigureColor secondRookColor = FigureColor.NONE;

        boolean firstRookFound = false;
        boolean secondRookFound = false;

        for (int i = 0; i < figuresRow.size(); i++) {
            Figure figure = figuresRow.get(i).getFigure();

            if (figure instanceof None) {
                continue;
            }

            if (figure instanceof Bishop) {
                if (rowNumber % 2 == 0) {
                    if (i % 2 == 0) {
                        ((Bishop) figure).setSquareColor(SquareColor.WHITE);
                    } else {
                        ((Bishop) figure).setSquareColor(SquareColor.BLACK);
                    }
                } else {
                    if (i % 2 == 0) {
                        ((Bishop) figure).setSquareColor(SquareColor.BLACK);
                    } else {
                        ((Bishop) figure).setSquareColor(SquareColor.WHITE);
                    }
                }
            }

            if (figure instanceof King) {
                kingColumn = i;
                kingRow = rowNumber;
                kingColor = figure.getColor();
                kingFound = true;

                kingsPositions.setKingPosition(figure.getColor(), kingColumn, kingRow);
            }

            if (figure instanceof Rook) {
                if (firstRookFound) {
                    secondRookColor = figure.getColor();
                    secondRookColumn = i;
                    secondRookRow = rowNumber;
                    secondRookFound = true;
                } else {
                    firstRookColor = figure.getColor();
                    firstRookColumn = i;
                    firstRookRow = rowNumber;
                    firstRookFound = true;
                }

            }

            if (figure.getColor() == FigureColor.WHITE) {
                addPieceToPiecesMap(whitePieces, figure);
            } else {
                addPieceToPiecesMap(blackPieces, figure);
            }
        }

        if (kingFound) {
            if (firstRookFound && secondRookFound) {

                if (kingColor == FigureColor.WHITE) {

                    if (firstRookColor == FigureColor.WHITE && firstRookRow == kingRow) {
                        if (firstRookColumn < kingColumn) {
                            rooksStartPositions.setWhiteLongCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setWhiteLongCastleRookRow(firstRookRow);
                        }
                        if (firstRookColumn > kingColumn) {
                            rooksStartPositions.setWhiteShortCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setWhiteShortCastleRookRow(firstRookRow);
                        }
                    }

                    if (secondRookColor == FigureColor.WHITE && secondRookRow == kingRow) {
                        if (secondRookColumn < kingColumn) {
                            rooksStartPositions.setWhiteLongCastleRookColumn(secondRookColumn);
                            rooksStartPositions.setWhiteLongCastleRookRow(secondRookRow);
                        }
                        if (secondRookColumn > kingColumn) {
                            rooksStartPositions.setWhiteShortCastleRookColumn(secondRookColumn);
                            rooksStartPositions.setWhiteShortCastleRookRow(secondRookRow);
                        }
                    }
                }

                if (kingColor == FigureColor.BLACK) {

                    if (firstRookColor == FigureColor.BLACK && firstRookRow == kingRow) {
                        if (firstRookColumn < kingColumn) {
                            rooksStartPositions.setBlackLongCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setBlackLongCastleRookRow(firstRookRow);
                        }
                        if (firstRookColumn > kingColumn) {
                            rooksStartPositions.setBlackShortCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setBlackShortCastleRookRow(firstRookRow);
                        }
                    }

                    if (secondRookColor == FigureColor.BLACK && secondRookRow == kingRow) {
                        if (secondRookColumn < kingColumn) {
                            rooksStartPositions.setBlackLongCastleRookColumn(secondRookColumn);
                            rooksStartPositions.setBlackLongCastleRookRow(secondRookRow);
                        }
                        if (secondRookColumn > kingColumn) {
                            rooksStartPositions.setBlackShortCastleRookColumn(secondRookColumn);
                            rooksStartPositions.setBlackShortCastleRookRow(secondRookRow);
                        }
                    }
                }
            }

            if (firstRookFound && !secondRookFound) {
                if (kingColor == FigureColor.WHITE) {

                    if (firstRookColor == FigureColor.WHITE && firstRookRow == kingRow) {
                        if (firstRookColumn < kingColumn) {
                            rooksStartPositions.setWhiteLongCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setWhiteLongCastleRookRow(firstRookRow);
                        }
                        if (firstRookColumn > kingColumn) {
                            rooksStartPositions.setWhiteShortCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setWhiteShortCastleRookRow(firstRookRow);
                        }
                    }
                }

                if(kingColor == FigureColor.BLACK) {

                    if (firstRookColor == FigureColor.BLACK && firstRookRow == kingRow) {
                        if (firstRookColumn < kingColumn) {
                            rooksStartPositions.setBlackLongCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setBlackLongCastleRookRow(firstRookRow);
                        }
                        if (firstRookColumn > kingColumn) {
                            rooksStartPositions.setBlackShortCastleRookColumn(firstRookColumn);
                            rooksStartPositions.setBlackShortCastleRookRow(firstRookRow);
                        }
                    }
                }
            }
        }
    }
}


