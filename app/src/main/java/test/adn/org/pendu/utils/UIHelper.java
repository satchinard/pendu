package test.adn.org.pendu.utils;

/**
 * Created by cagecfi on 17/07/2017.
 */

public class UIHelper {

    public static void killApp(boolean killSafely) {
        if (killSafely) {
            System.runFinalizersOnExit(true);
            System.exit(0);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
