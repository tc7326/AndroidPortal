package info.itloser.androidportal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

/**
 * author：zhaoliangwang on 2018/9/22 16:43
 * email：tc7326@126.com
 */
public class RiMonthView extends RangeMonthView {

    private int mRadius;

    private Bitmap car = BitmapFactory.decodeResource(getResources(), R.mipmap.page_icon_dowp);
    private Bitmap whiteCar = BitmapFactory.decodeResource(getResources(), R.mipmap.page_more_icon_travel);

    public RiMonthView(Context context) {
        super(context);
        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;

        if (isSelectedPre) {
            if (isSelectedNext) {
                canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            } else {//最后一个，the last
                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            }
        } else {
            if (isSelectedNext) {
                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            }
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        }
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        //暂时不用到
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;

        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;

        if (isSelected) {

            if (!hasScheme)
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx,
                        baselineY,
                        mSelectTextPaint);
            if (hasScheme)
                canvas.drawBitmap(whiteCar, cx - car.getWidth() / 2, cy - car.getHeight() / 2, null);

        } else if (hasScheme) {

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

}
