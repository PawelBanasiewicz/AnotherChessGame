package anotherchessgame.textui;

import anotherchessgame.launcher.Startable;

import static anotherchessgame.textui.Menu.startMenu;

public class TextUiMain implements Startable {
    public static void main(String[] args) {
        new TextUiMain().start(args);
    }

    @Override
    public void start(String[] args) {
        startMenu();
    }
}
