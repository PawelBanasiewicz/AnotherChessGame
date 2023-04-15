package anotherchessgame.launcher;

public class DefaultStartable implements Startable {
    private static final String LAUNCHER_HELP = "java -jar AnotherChessGame.jar mode -foo bar ...\n" +
            "cmd -> commandLine version\t gui -> graphical interface version (not supported yet)";

    public static void main(String[] args) {
        new DefaultStartable().start(args);
    }
    @Override
    public void start(String[] args) {
        System.out.println(LAUNCHER_HELP);
    }
}
