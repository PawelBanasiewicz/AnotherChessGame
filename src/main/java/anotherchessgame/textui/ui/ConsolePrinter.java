package anotherchessgame.textui.ui;

import chess.game.Game;
import chess.logic.board.Board;
import chess.logic.board.BoardOrientation;

import java.io.IOException;

import static chess.logic.board.BoardOrientation.BLACK_ON_TOP;
import static chess.logic.board.BoardOrientation.WHITE_ON_TOP;
import static chess.utils.Constants.FIRST_COLUMN;
import static chess.utils.Constants.FIRST_ROW;
import static chess.utils.Constants.LAST_COLUMN;
import static chess.utils.Constants.LAST_ROW;


public class ConsolePrinter {
    private static BoardOrientation boardOrientation = BLACK_ON_TOP;
    private static final String HEADLINE = """
                                                                  .::.
                                                       _()_       _::_
                                             _O      _/____\\_   _/____\\_
                      _  _  _     ^^__      / //\\    \\      /   \\      /
                     | || || |   /  - \\_   {     }    \\____/     \\____/
                     |_______| <|    __<    \\___/     (____)     (____)
               _     \\__ ___ / <|    \\      (___)      |  |       |  |
              (_)     |___|_|  <|     \\      |_|       |__|       |__|
             (___)    |_|___|  <|______\\    /   \\     /    \\     /    \\
             _|_|_    |___|_|   _|____|_   (_____)   (______)   (______)
            (_____)  (_______) (________) (_______) (________) (________)
            /_____\\  /_______\\ /________\\ /_______\\ /________\\ /________\\
               """;
    private static final String MENU_OPTIONS = """
            1.NEW GAME
            2.LOAD GAME
            3.ABOUT
            4.EXIT""";

    private static final String NEW_GAME_OPTIONS = """
            1.CLASSIC CHESS
            2.FISHER RANDOM CHESS
            3.BACK
            """;

    private static final String LOAD_OPTIONS = "PASTE FEN (1 to go back): ";
    private static final String ABOUT_DIALOG = "just another chess game :)\n(1 to go back)";
    private static final String FIGURES_ROW_FORMAT = "%4d %c%s%c%s%c%s%c%s%c%s%c%s%c%s%c%s%c";
    private static final String LETTERS_ROW_FORMAT = "%8c %2c %2c %2c %2c %2c %2c %2c";
    private static final char PIPE = '|';


    public static void printMenu() {
        clearConsole();
        System.out.println(HEADLINE);
        System.out.println(MENU_OPTIONS);
    }

    public static void printNewGameOptions() {
        clearConsole();
        System.out.println(HEADLINE);
        System.out.println(NEW_GAME_OPTIONS);
    }

    public static void printLoadOptions() {
        clearConsole();
        System.out.println(HEADLINE);
        System.out.println(LOAD_OPTIONS);
    }

    public static void printAbout() {
        clearConsole();
        System.out.println(HEADLINE);
        System.out.println(ABOUT_DIALOG);
    }

    public static void printGame(Game game) {
        String stringBuilder = "Whose move: " + game.getWhoseMove() +
                "\tCastling Rights: " + game.getCastlingRights().toString() +
                "\tEnPassant: " + game.getPossibleEnPassantSquare().toString() +
                "\nHalfmoveClock: " + game.getHalfMoveClock() +
                "\tFullMoveClock: " + game.getFullMoveNumber();
        System.out.println(stringBuilder + "\n");

        printBoard(game);
    }

    protected static void changeBoardOrientation(Game game) {
        boardOrientation = boardOrientation == BLACK_ON_TOP ? WHITE_ON_TOP : BLACK_ON_TOP;
        printGame(game);
    }

    private static void printBoard(Game game) {
        if (boardOrientation == BLACK_ON_TOP) {
            printBoardFromWhitePerspective(game.getBoard());
        } else {
            printBoardFromBlackPerspective(game.getBoard());
        }
    }

    private static void printBoardFromWhitePerspective(Board board) {
        String[] figuresRow = new String[LAST_ROW];
        char[] letterRow = new char[LAST_ROW];
        char letter = 'A';
        int rowCounter = LAST_ROW;

        for (int row = FIRST_ROW; row < LAST_ROW; row++) {
            letterRow[row] = letter;
            letter++;
            for (int column = FIRST_COLUMN; column < LAST_COLUMN; column++) {
                figuresRow[column] = board.getFigure(column, row).toString();
            }
            System.out.printf((FIGURES_ROW_FORMAT) + "%n", rowCounter, PIPE, figuresRow[0], PIPE, figuresRow[1], PIPE, figuresRow[2], PIPE,
                    figuresRow[3], PIPE, figuresRow[4], PIPE, figuresRow[5], PIPE, figuresRow[6], PIPE, figuresRow[7], PIPE);

            rowCounter--;
        }
        System.out.printf((LETTERS_ROW_FORMAT) + "\n", letterRow[0], letterRow[1], letterRow[2], letterRow[3], letterRow[4], letterRow[5], letterRow[6], letterRow[7]);
    }

    private static void printBoardFromBlackPerspective(Board board) {
        String[] figuresRow = new String[LAST_ROW];
        char[] letterRow = new char[LAST_ROW];
        char letter = 'H';
        int rowCounter = FIRST_ROW + 1;

        for (int row = LAST_ROW - 1; row >= FIRST_ROW; row--) {
            letterRow[row] = letter;
            letter--;
            for (int column = LAST_COLUMN - 1; column >= FIRST_COLUMN; column--) {
                figuresRow[column] = board.getFigure(column, row).toString();
            }
            System.out.printf((FIGURES_ROW_FORMAT) + "%n", rowCounter, PIPE, figuresRow[7], PIPE, figuresRow[6], PIPE, figuresRow[5], PIPE,
                    figuresRow[4], PIPE, figuresRow[3], PIPE, figuresRow[2], PIPE, figuresRow[1], PIPE, figuresRow[0], PIPE);
            rowCounter++;
        }
        System.out.printf((LETTERS_ROW_FORMAT) + "\n", letterRow[7], letterRow[6], letterRow[5], letterRow[4], letterRow[3], letterRow[2], letterRow[1], letterRow[0]);
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ignored) {
        }

    }
}
