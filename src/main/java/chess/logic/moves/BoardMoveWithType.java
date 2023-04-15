package chess.logic.moves;

public class BoardMoveWithType {
    BoardMove boardMove;
    BoardMoveType boardMoveType;

    public BoardMoveWithType(BoardMove boardMove, BoardMoveType boardMoveType) {
        this.boardMove = boardMove;
        this.boardMoveType = boardMoveType;
    }

    public BoardMove getBoardMove() {
        return boardMove;
    }

    public BoardMoveType getBoardMoveType() {
        return boardMoveType;
    }
}
