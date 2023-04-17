package chess.utils;

import chess.game.Game;
import chess.logic.board.SquareColor;
import chess.logic.figures.Bishop;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.logic.figures.Knight;
import chess.logic.figures.Pawn;
import chess.logic.figures.Queen;
import chess.logic.figures.Rook;

import java.util.HashMap;
import java.util.Map;

import static chess.utils.PiecesMapManager.Piece.DARK_SQUARED_BISHOP;
import static chess.utils.PiecesMapManager.Piece.KING;
import static chess.utils.PiecesMapManager.Piece.KNIGHT;
import static chess.utils.PiecesMapManager.Piece.LIGHT_SQUARED_BISHOP;
import static chess.utils.PiecesMapManager.Piece.PAWN;
import static chess.utils.PiecesMapManager.Piece.QUEEN;
import static chess.utils.PiecesMapManager.Piece.ROOK;

public class PiecesMapManager {
    private PiecesMapManager() {
    }

    public enum Piece {
        PAWN, KNIGHT, LIGHT_SQUARED_BISHOP, DARK_SQUARED_BISHOP, ROOK, QUEEN, KING
    }

    public static Map<Piece, Integer> createPiecesMap() {
        return new HashMap<>(Map.of(
                PAWN, 0,
                KNIGHT, 0,
                LIGHT_SQUARED_BISHOP, 0,
                DARK_SQUARED_BISHOP, 0,
                ROOK, 0,
                QUEEN, 0,
                KING, 0));
    }

    public static void addPieceToPiecesMapForAlreadyCreatedGame(Game game, Figure figureToAdd) {
        Map<Piece, Integer> piecesMap;
        if (figureToAdd.getColor() == FigureColor.WHITE) {
            piecesMap = game.getWhitePieces();
        } else {
            piecesMap = game.getBlackPieces();
        }
        addPieceToPiecesMap(piecesMap, figureToAdd);
    }

    public static void addPieceToPiecesMap(Map<Piece, Integer> piecesMap, Figure figureToAdd) {
        if (figureToAdd instanceof Pawn) {
            piecesMap.put(PAWN, piecesMap.getOrDefault(PAWN, -2) + 1);
        }
        if (figureToAdd instanceof Knight) {
            piecesMap.put(KNIGHT, piecesMap.getOrDefault(KNIGHT, -2) + 1);
        }
        if (figureToAdd instanceof Bishop) {
            if (((Bishop) figureToAdd).getSquareColor() == SquareColor.WHITE) {
                piecesMap.put(LIGHT_SQUARED_BISHOP, piecesMap.getOrDefault(LIGHT_SQUARED_BISHOP, -2) + 1);
            } else {
                piecesMap.put(DARK_SQUARED_BISHOP, piecesMap.getOrDefault(DARK_SQUARED_BISHOP, -2) + 1);
            }
        }
        if (figureToAdd instanceof Rook) {
            piecesMap.put(ROOK, piecesMap.getOrDefault(ROOK, -2) + 1);
        }
        if (figureToAdd instanceof Queen) {
            piecesMap.put(QUEEN, piecesMap.getOrDefault(QUEEN, -2) + 1);
        }
        if (figureToAdd instanceof King) {
            piecesMap.put(KING, piecesMap.getOrDefault(KING, -2) + 1);
        }
    }

    public static void deletePieceFromPiecesMap(Game game, Figure figureToDelete) {
        Map<Piece, Integer> piecesMap;
        if (figureToDelete.getColor() == FigureColor.WHITE) {
            piecesMap = game.getWhitePieces();
        } else {
            piecesMap = game.getBlackPieces();
        }

        if (figureToDelete instanceof Pawn) {
            piecesMap.put(PAWN, piecesMap.getOrDefault(PAWN, -1) - 1);
        }
        if (figureToDelete instanceof Knight) {
            piecesMap.put(KNIGHT, piecesMap.getOrDefault(KNIGHT, -1) - 1);
        }
        if (figureToDelete instanceof Bishop) {
            if (((Bishop) figureToDelete).getSquareColor() == SquareColor.WHITE) {
                piecesMap.put(LIGHT_SQUARED_BISHOP, piecesMap.getOrDefault(LIGHT_SQUARED_BISHOP, -1) - 1);
            } else {
                piecesMap.put(DARK_SQUARED_BISHOP, piecesMap.getOrDefault(DARK_SQUARED_BISHOP, -1) - 1);
            }
        }
        if (figureToDelete instanceof Rook) {
            piecesMap.put(ROOK, piecesMap.getOrDefault(ROOK, -1) - 1);
        }
        if (figureToDelete instanceof Queen) {
            piecesMap.put(QUEEN, piecesMap.getOrDefault(QUEEN, -1) - 1);
        }
        if (figureToDelete instanceof King) {
            piecesMap.put(KING, piecesMap.getOrDefault(KING, -1) - 1);
        }
    }
}
