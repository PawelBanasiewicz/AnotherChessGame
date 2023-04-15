package anotherchessgame.textui;

import static anotherchessgame.textui.GameStarter.startGameFromFEN;
import static anotherchessgame.textui.GameStarter.startNew960Game;
import static anotherchessgame.textui.GameStarter.startNewGame;
import static anotherchessgame.textui.ui.ConsolePrinter.printAbout;
import static anotherchessgame.textui.ui.ConsolePrinter.printLoadOptions;
import static anotherchessgame.textui.ui.ConsolePrinter.printMenu;
import static anotherchessgame.textui.ui.ConsolePrinter.printNewGameOptions;
import static anotherchessgame.textui.ui.UserDialogs.getLeaveAbout;
import static anotherchessgame.textui.ui.UserDialogs.getLoadChoice;
import static anotherchessgame.textui.ui.UserDialogs.getMenuChoice;
import static anotherchessgame.textui.ui.UserDialogs.getNewGameChoice;

public class Menu {
    public static void startMenu() {
        while (true) {
            printMenu();
            int userChoice = getMenuChoice();

            if (userChoice == 1) {
                printNewGameOptions();
                int userChoiceNewGame = getNewGameChoice();
                if (userChoiceNewGame == 1) {
                    startNewGame();
                }
                if (userChoiceNewGame == 2) {
                    startNew960Game();
                }
            }
            if (userChoice == 2) {
                printLoadOptions();
                String userChoiceLoad = getLoadChoice();
                if (userChoiceLoad.equalsIgnoreCase("1")) {
                    continue;
                }
                startGameFromFEN(userChoiceLoad);
            }
            if (userChoice == 3) {
                printAbout();
                getLeaveAbout();
            }
            if (userChoice == 4) {
                break;
            }
        }

    }

}
