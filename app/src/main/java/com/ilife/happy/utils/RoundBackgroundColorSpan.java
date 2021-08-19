package com.ilife.happy.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.ilife.common.utils.DensityUtil;

public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private Context context;


    public RoundBackgroundColorSpan(Context context, int bgColor, int textColor) {
        super();
        this.context = context;
        this.bgColor = bgColor;
        this.textColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        return ((int) paint.measureText(text, start, end) + DensityUtil.px2dip(context, 76));
    }


    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        int color1 = paint.getColor();
        //设置背景颜色
        paint.setColor(this.bgColor);
        canvas.drawRoundRect(new RectF(x, top + DensityUtil.px2dip(context, 15),
                x + ((int) paint.measureText(text, start, end)) + DensityUtil.px2dip(context, 56),
                bottom - DensityUtil.px2dip(context, 15)), 50, 50, paint);
        //设置字体颜色
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x + DensityUtil.px2dip(context, 30), y - DensityUtil.px2dip(context, 10), paint);
        paint.setColor(color1);
    }

}
