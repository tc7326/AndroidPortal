package info.itloser.androidportal.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * author：zhaoliangwang on 2019/9/10 17:41
 * email：tc7326@126.com
 */
public class TopToast {
    public static Toast makeText(Context context, String text, int i) {
        Toast topToast = new Toast(context);
        topToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
        topToast.setView(getTextView(context, text));
        topToast.setDuration(i);
        return topToast;
    }

    private static TextView getTextView(Context context, String text) {
        TextView view = new TextView(context);
        view.setBackgroundColor(0xFFFF6A42);
        view.setTextColor(0xFFFFFFFF);
        view.setGravity(Gravity.CENTER);
        view.setPadding(8, getStatusBarHeight(context) + 8, 8, 8);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);//状态栏不可见
        view.setText(text);
        return view;
    }

    private static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
