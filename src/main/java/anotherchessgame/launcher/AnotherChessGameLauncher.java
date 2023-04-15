package anotherchessgame.launcher;

import anotherchessgame.graphicui.GraphicUiMain;
import anotherchessgame.textui.TextUiMain;

public class AnotherChessGameLauncher {

    private static final String CMD = "CMD";
    private static final String GUI = "GUI";

    public static void main(String[] args) {
        new AnotherChessGameLauncher().run(args);
    }

    private void run(String[] args) {
        Startable subsystem;
        if(args.length == 0) {
            subsystem = new DefaultStartable();
        } else {
            subsystem = switch (args[0].toUpperCase()) {
                case CMD -> new TextUiMain();
                case GUI -> new GraphicUiMain();
                default -> new DefaultStartable();
            };
        }

        subsystem.start(args);
    }
}
