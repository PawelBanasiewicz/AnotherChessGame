package chess.utils;

import java.util.Random;

public class SpidDrawer {

    public static int drawSpid() {
        Random random = new Random();
        return random.nextInt(960);
    }
}