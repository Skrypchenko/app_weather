package com.task.test.weatherapp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.util.DebugUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by OLEG on 18.10.2017.
 */

public class Lgi {
    public static boolean LOG = false;
    private static String TAG = "fatal";

    public static void i(String tag, String string) {
        if (LOG) android.util.Log.i(tag, string);
    }
    public static void e(String tag, String string) {
        if (LOG) android.util.Log.e(tag, string);
    }
    public static void d(String tag, String string) {
        if (LOG) android.util.Log.d(tag, string);
    }
    public static void v(String tag, String string) {
        if (LOG) android.util.Log.v(tag, string);
    }
    public static void w(String tag, String string) {
        if (LOG) android.util.Log.w(tag, string);
    }

    public static void i(String string) {
        if (LOG) android.util.Log.i(TAG, string);
    }
    public static void e(String tag, String string, IOException e) {
        if (LOG) android.util.Log.e(TAG, string);
    }
    public static void d(String string) {
        if (LOG) android.util.Log.d(TAG, string);
    }
    public static void v(String string) {
        if (LOG) android.util.Log.v(TAG, string);
    }
    public static void w(String string) {
        if (LOG) android.util.Log.w(TAG, string);
    }



    private static final String SEPAR = ": ";
    private static long sStartTime = 0;

    // Выводит в лог имя класса и имя метода, в котором был вызван
    public static void p() {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]");
        }
    }

    // Выводит в лог имя класса + String переданный в параметре
    public static void p(String s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(Object s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + getShortClassTag(s));
        }
    }

    public static String getShortClassTag(Object cls) {
        if (cls == null) {
            return "null";
        } else {
            String simpleName = cls.getClass().getSimpleName();
            if (simpleName == null || simpleName.length() <= 0) {
                simpleName = cls.getClass().getName();
                int end = simpleName.lastIndexOf('.');
                if (end > 0) {
                    simpleName = simpleName.substring(end + 1);
                }
            }
            return simpleName + "[" + Integer.toHexString(System.identityHashCode(cls))  + "]";
        }
    }

    public static void p(int s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(double s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(float s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(long s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(char s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(byte s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    public static void p(boolean s) {
        if (LOG) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
            String className = ste.getFileName().split("\\.")[0];
            String method = ste.getMethodName();
            log("[" + className + "." + method + "]" + SEPAR + s);
        }
    }

    // просто перенос строки
    public static void separatorLine() {
        if (LOG) {
            log("\t-\t-\t-\t-\t-");
        }
    }


    public static long time() {
        return System.currentTimeMillis();
    }

    public static void toastShort(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void toastLong(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    // выводит класс, метод, и строку, в которой был вызван
    public static void err() {
        if (LOG) {
            StackTraceElement ste = null;
            ste = Thread.currentThread().getStackTrace()[3];
            String str = ste.getClassName() + "." + ste.getMethodName() +
                    "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            log(str);
        }
    }
    // выводит класс, метод, и строку, в которой был вызван + текст
    public static void err(String s) {
        if (LOG) {
            StackTraceElement ste = null;
            ste = Thread.currentThread().getStackTrace()[3];
            String str = ste.getClassName() + "." + ste.getMethodName() +
                    "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
            log(str + " > " + s);
        }
    }

    // выводит в лог String s и Exception
    public static void err(String s, Throwable ex) {
        if (LOG) {
            err("Message: " + s);
            android.util.Log.v(TAG, "stacktrace: ", ex);
        }
    }

    // используется для замера промежутка времени
    public static void startTime() {
        sStartTime = time();
    }

    // используется для замера промежутка времени, возвращает время между
    // startTime и endTime
    public static void endTime() {
        if (sStartTime == 0) {
            log("Error, startTime = 0. Don't use endTime() without startTime() before.");
            return;
        } else {
            long diff = time() - sStartTime;
            sStartTime = 0;
            log("Time: " + diff + " ms.");
        }
    }

    public static String getFormatDate(long d) {
        return new SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss", Locale.getDefault()).format(new Date(d));
    }

    public static String getFormatDate(long d, String format) {
        return new SimpleDateFormat(format, Locale.getDefault()).format(new Date(d));
    }

    private static void log(String s) {
        if (LOG) {
            android.util.Log.v(TAG, s);
        }
    }

    public static class Utils {
        public static int dpToPx(@NonNull final Context context, final float dp) {
            return (int) (dp * context.getResources().getDisplayMetrics().density);
        }

        public static int screenHeight(@NonNull final Activity context) {
            final Point size = new Point();

            context.getWindowManager().getDefaultDisplay().getSize(size);

            return size.y;
        }

        public static int screenWidth(@NonNull final Activity context) {
            final Point size = new Point();

            context.getWindowManager().getDefaultDisplay().getSize(size);

            return size.x;
        }

        public static double getInches(Activity activity) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width=dm.widthPixels;
            int height=dm.heightPixels;
            double wi=(double)width/(double)dm.xdpi;
            double hi=(double)height/(double)dm.ydpi;
            double x = Math.pow(wi,2);
            double y = Math.pow(hi,2);
            double screenInches = Math.sqrt(x+y);
            return screenInches;
        }



        @NonNull
        public static Point locationOnScreen(@NonNull final View view) {
            final int[] location = new int[2];
            view.getLocationOnScreen(location);
            return new Point(location[0], location[1]);
        }
    }

}
