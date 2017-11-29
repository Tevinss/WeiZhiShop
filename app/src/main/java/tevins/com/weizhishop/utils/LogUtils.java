package tevins.com.weizhishop.utils;

import android.util.Log;

import tevins.com.weizhishop.BuildConfig;


/**
 * Created by yewyw on 2017/8/11/0011.
 */

public class LogUtils {
    public static final boolean DEBUG = BuildConfig.LOG_CALLS;

    public static void e(String tag, String msg) {
        if (DEBUG) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            StackTraceElement targetElement = null;
            for (int i = 0; i < stackTrace.length; i++) {
                if (stackTrace[i].getClassName().contentEquals(LogUtils.class.getName())) {
                    targetElement = stackTrace[i + 1];
                    break;
                }
            }
            String logString = String.format("(%s:%s)%s", targetElement.getFileName(), targetElement.getLineNumber(), msg);

            Log.e(tag, logString);
        }
    }
}
