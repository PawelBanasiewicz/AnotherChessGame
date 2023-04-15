package anotherchessgame.textui.ui;

import chess.game.Game;
import chess.logic.moves.BoardMove;

import java.util.Scanner;

import static anotherchessgame.textui.ui.ConsolePrinter.printAbout;
import static anotherchessgame.textui.ui.ConsolePrinter.printLoadOptions;
import static anotherchessgame.textui.ui.ConsolePrinter.printMenu;
import static anotherchessgame.textui.ui.ConsolePrinter.printNewGameOptions;
import static chess.utils.MoveMaps.stringToIntColumnMap;
import static chess.utils.MoveMaps.stringToIntRowMap;

public class UserDialogs {

    public static int getMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("choice: ");

            try {
                String textFromUser = scanner.nextLine();
                int userChoice = Integer.parseInt(textFromUser);
                if (userChoice < 1 || userChoice > 4) {
                    throw new Exception();
                }
                return userChoice;
            } catch (Exception e) {
                printMenu();
            }
        }
    }

    public static int getNewGameChoice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("choice: ");

            try {
                String textFromUser = scanner.nextLine();
                int userChoice = Integer.parseInt(textFromUser);
                if (userChoice < 1 || userChoice > 3) {
                    throw new Exception();
                }
                return userChoice;
            } catch (Exception e) {
                printNewGameOptions();
            }
        }
    }

    public static String getLoadChoice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("choice: ");

            try {
                return scanner.nextLine();
            } catch (Exception e) {
                printLoadOptions();
            }
        }
    }

    public static void getLeaveAbout() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("choice: ");

            try {
                String textFromUser = scanner.nextLine();
                int userChoice = Integer.parseInt(textFromUser);
                if (userChoice != 1) {
                    throw new Exception();
                }
                return;
            } catch (Exception e) {
                printAbout();
            }
        }
    }

    public static BoardMove getNextMove(Game game) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter your move (X-Change board Orientation Q-Quit): ");

            try {
                String textFromUser = scanner.nextLine();
                if (textFromUser.equalsIgnoreCase("X")) {
                    ConsolePrinter.changeBoardOrientation(game);
                    continue;
                }
                if (textFromUser.equalsIgnoreCase("Q")) {
                    System.exit(0);
                }

                int columnBegin = stringToIntColumnMap.get(textFromUser.substring(0, 1).toUpperCase());
                int rowBegin = stringToIntRowMap.get(textFromUser.substring(1, 2));
                int columnEnd = stringToIntColumnMap.get(textFromUser.substring(2, 3).toUpperCase());
                int rowEnd = stringToIntRowMap.get(textFromUser.substring(3, 4));


                if (columnBegin > 7 || rowBegin > 7 || columnEnd > 7 || rowEnd > 7)
                    throw new Exception();

                return new BoardMove(columnBegin, rowBegin, columnEnd, rowEnd);
            } catch (Exception e) {
                System.out.println("Wrong move, try again.");
            }
        }
    }
}
