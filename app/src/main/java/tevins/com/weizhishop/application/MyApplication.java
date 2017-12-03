package tevins.com.weizhishop.application;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.facebook.drawee.backends.pipeline.Fresco;

import tevins.com.weizhishop.utils.LogUtils;

/**
 * Created by tevins on 2017/11/30/0030.
 */

public class MyApplication extends Application {
    private PendingIntent mRestartIntent;
    private MyUncaughtExceptionHandler mMyUncaughtExceptionHandler;
    private String mPackageName;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mPackageName = getPackageName();
        catchException();
    }


    private void catchException() {
        Intent intent = new Intent();
        // 参数1：包名，参数2：程序入口的activity
        intent.setClassName(mPackageName, mPackageName + ".ui.activity.MainActivity");
        mRestartIntent = PendingIntent.getActivity(getApplicationContext(), -1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        // 程序崩溃时触发线程
        mMyUncaughtExceptionHandler = new MyUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mMyUncaughtExceptionHandler);
    }


    // 创建服务用于捕获崩溃异常
    private class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            LogUtils.e("MyUncaughtExceptionHandler", "uncaughtException11111: " + ex.getMessage());
            LogUtils.e("MyUncaughtExceptionHandler", "uncaughtException22222: " + ex.getMessage().toString());
            LogUtils.e("MyUncaughtExceptionHandler", "uncaughtException33333: " + ex.toString());
            // 1秒钟后重启应用
            AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, mRestartIntent);
            // 关闭当前应用
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
