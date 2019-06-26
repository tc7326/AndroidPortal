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
 * 简单周视图
 * Created by huanghaibin on 2017/11/29.
 */

public class RiWeekView extends RangeMonthView {
    private int mRadius;

    private Bitmap car = BitmapFactory.decodeResource(getResources(), R.mipmap.page_icon_dowp);
    private Bitmap whiteCar = BitmapFactory.decodeResource(getResources(), R.mipmap.page_more_icon_travel);


    public RiWeekView(Context context) {
        super(context);
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSelectedPaint);
        //4.0以上硬件加速会导致无效
        mSelectedPaint.setMaskFilter(new BlurMaskFilter(25, BlurMaskFilter.Blur.SOLID));
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        return false;

    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
        //绘制车的图片
        canvas.drawBitmap(car, cx - car.getWidth() / 2, cy - car.getHeight() / 2, null);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine;
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        if (isSelected) {

            if (!hasScheme)
                canvas.drawText(String.valueOf(calendar.getDay()),
                        cx,
                        baselineY,
                        mSelectTextPaint);


            if (hasScheme)
                canvas.drawBitmap(whiteCar, cx - car.getWidth() / 2, cy - car.getHeight() / 2, null);

        }else if (hasScheme) {

            //如果是有标记的日期，不绘制--->onDrawScheme里已绘制小汽车

//            canvas.drawText(String.valueOf(calendar.getDay()),
//                    cx,
//                    baselineY,
//                    calendar.isCurrentDay() ? mCurDayTextPaint :
//                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }

    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);
    }

}
