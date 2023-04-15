package anotherchessgame.textui;

import chess.game.Game;
import chess.game.GameStatus;
import chess.logic.moves.BoardMove;
import chess.logic.moves.BoardMoveWithType;
import chess.utils.SpidDrawer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static anotherchessgame.textui.ui.ConsolePrinter.clearConsole;
import static anotherchessgame.textui.ui.ConsolePrinter.printGame;
import static anotherchessgame.textui.ui.UserDialogs.getNextMove;
import static chess.game.FENLoader.loadFromFEN;
import static chess.game.GameStatus.GAME_IN_PROGRESS;
import static chess.logic.board.BoardUpdater.updateBoard;
import static chess.logic.checkers.GameStatusChecker.checkGameStatus;
import static chess.logic.converters.BoardMoveToBoardMoveWithTypeConverter.convertBoardMoveToBoardMoveWithType;
import static chess.logic.converters.BoardMoveToCastleMoveConverter.convertBoardMoveToCastleMoveIfNeeded;
import static chess.logic.moves.MoveMaker.makeMove;
import static chess.utils.Constants.NEW_GAME;

public class GameStarter {

    public static void startNewGame() {
        startGameFromFEN(NEW_GAME);
    }

    public static void startNew960Game() {
        int spid = SpidDrawer.drawSpid();
        String chess960Fen;
        try (Stream<String> lines = Files.lines(Paths.get("D:/Projects/AnotherChessGame/src/main/resources/chess960positions.txt"))) {
            chess960Fen = lines.skip(spid).findFirst().get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        startGameFromFEN(chess960Fen);
    }

    public static void startGameFromFEN(String FEN) {
        clearConsole();
        Game game;
        try {
            game = loadFromFEN(FEN);
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
