package frc.utils;

/**
 * Util for hanging main thread
 */
public class SleepUtil {
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
