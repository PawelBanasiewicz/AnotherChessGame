package chess.game;

import chess.logic.board.Board;
import chess.logic.board.CastlingRights;
import chess.logic.board.KingsPositions;
import chess.logic.board.RooksStartPositions;
import chess.logic.board.Square;
import chess.logic.figures.Figure;
import chess.logic.figures.FigureColor;
import chess.logic.figures.King;
import chess.utils.PiecesMapManager.Piece;

import java.util.HashMap;
import java.util.Map;

public class Game {
    public static final Map<Board, Integer> threefoldRepetitionMap = new HashMap<>();

    private final Board board;
    private FigureColor whoseMove;
    private final CastlingRights castlingRights;
    private final Square possibleEnPassantSquare;
    private int halfMoveClock;
    private int fullMoveNumber;

    private final KingsPositions kingsPositions;
    private final RooksStartPositions rooksStartPositions;
    private final Map<Piece, Integer> whitePieces;
    private final Map<Piece, Integer> blackPieces;
    private final Map<Square, Figure> figuresGivingCheck;


    public Game(Board board, FigureColor whoseMove, CastlingRights castlingRights, Square possibleEnPassantSquare,
                int halfMoveClock, int fullMoveNumber, KingsPositions kingsPositions,
                RooksStartPositions rooksStartPositions, Map<Piece, Integer> whitePieces, Map<Piece, Integer> blackPieces) {
        this.board = board;
        this.whoseMove = whoseMove;
        this.castlingRights = castlingRights;
        this.possibleEnPassantSquare = possibleEnPassantSquare;
        this.halfMoveClock = halfMoveClock;
        this.fullMoveNumber = fullMoveNumber;
        this.kingsPositions = kingsPositions;
        this.rooksStartPositions = rooksStartPositions;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.figuresGivingCheck = new HashMap<>();
    }

    public void setOppositeWhoseMove() {
        if (whoseMove == FigureColor.WHITE) {
            whoseMove = FigureColor.BLACK;
            return;
        }
        whoseMove = FigureColor.WHITE;
    }

    public King getKing(FigureColor figureColor) {
        if (figureColor == FigureColor.WHITE) {
            return (King) getBoard().getFigure(getKingsPositions().getWhiteKingColumn(), getKingsPositions().getWhiteKingRow());
        } else {
            return (King) getBoard().getFigure(getKingsPositions().getBlackKingColumn(), getKingsPositions().getBlackKingRow());
        }
    }

    public Board getBoard() {
        return board;
    }

    public FigureColor getWhoseMove() {
        return whoseMove;
    }

    public CastlingRights getCastlingRights() {
        return castlingRights;
    }

    public Square getPossibleEnPassantSquare() {
        return possibleEnPassantSquare;
    }

    public int getHalfMoveClock() {
        return halfMoveClock;
    }

    public void setHalfMoveClock(int halfMoveClock) {
        this.halfMoveClock = halfMoveClock;
    }

    public int getFullMoveNumber() {
        return fullMoveNumber;
    }

    public void setFullMoveNumber(int fullMoveNumber) {
        this.fullMoveNumber = fullMoveNumber;
    }

    public KingsPositions getKingsPositions() {
        return kingsPositions;
    }

    public Map<Piece, Integer> getWhitePieces() {
        return whitePieces;
    }

    public Map<Piece, Integer> getBlackPieces() {
        return blackPieces;
    }

    public Map<Square, Figure> getFiguresGivingCheck() {
        return figuresGivingCheck;
    }

    public RooksStartPositions getRooksStartPositions() {
        return rooksStartPositions;
    }
}
