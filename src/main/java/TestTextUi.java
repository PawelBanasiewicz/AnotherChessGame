import chess.game.Game;
import chess.game.GameStatus;
import chess.logic.moves.BoardMove;
import chess.logic.moves.BoardMoveWithType;

import static chess.game.FENLoader.loadFromFEN;
import static chess.game.GameStatus.GAME_IN_PROGRESS;
import static chess.logic.board.BoardUpdater.updateBoard;
import static chess.logic.checkers.GameStatusChecker.checkGameStatus;
import static chess.logic.converters.BoardMoveToBoardMoveWithTypeConverter.convertBoardMoveToBoardMoveWithType;
import static chess.logic.converters.BoardMoveToCastleMoveConverter.convertBoardMoveToCastleMoveIfNeeded;
import static chess.logic.moves.MoveMaker.makeMove;
import static anotherchessgame.textui.ui.ConsolePrinter.printGame;
import static anotherchessgame.textui.ui.UserDialogs.getNextMove;

public class TestTextUi {
    public static void main(String[] args) {
        Game game;
        try {
            game = loadFromFEN("rnbqkbnr/1pp1pppp/p7/3pP3/8/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 3");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateBoard(game);

        GameStatus gameStatus = checkGameStatus(game);
        while (gameStatus == GAME_IN_PROGRESS) {
            printGame(game);
            BoardMove boardMove = getNextMove(game);
            boardMove = convertBoardMoveToCastleMoveIfNeeded(game, boardMove);
            BoardMoveWithType boardMoveWithType = convertBoardMoveToBoardMoveWithType(game, boardMove);
            makeMove(game, boardMoveWithType);
            gameStatus = checkGameStatus(game);
        }

    }
}
