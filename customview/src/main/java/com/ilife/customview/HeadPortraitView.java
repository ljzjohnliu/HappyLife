package com.ilife.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class HeadPortraitView extends RelativeLayout {

    private RelativeLayout baseLayout;
    private ImageView mTagCircleImage;
    private CircleImageView mAvatarImage;
    private ImageView mProgressImage;

    private Context mContext;

    public HeadPortraitView(Context context) {
        this(context, null);
    }

    public HeadPortraitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadPortraitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HeadPortraitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;

        View view = View.inflate(mContext, R.layout.circle_custom_view, this);
        baseLayout = view.findViewById(R.id.base_layout);
        mTagCircleImage = view.findViewById(R.id.tag_view);
        mAvatarImage = view.findViewById(R.id.circle_avatar_imageview);
        mProgressImage = view.findViewById(R.id.progress_image);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = px2dip(mContext, baseLayout.getMeasuredHeight());
        int width = px2dip(mContext, baseLayout.getMeasuredWidth());

        int marMin = Math.min(height, width);

        RelativeLayout.LayoutParams mAvatarTagLP = (RelativeLayout.LayoutParams) mTagCircleImage.getLayoutParams();
        int mAvatarTagWidthMar = width / 20;
        int mAvatarTagHeightMar = height / 20;
        Log.d("test", "mAvatarTagWidthMar === " + mAvatarTagWidthMar + "   ,mAvatarTagHeightMar  = " + mAvatarTagHeightMar);

        Log.d("test", "dip2px(mContext, mAvatarTagWidthMar) === " + dip2px(mContext, mAvatarTagWidthMar) +
                "   ,dip2px(mContext, mAvatarTagHeightMar)  = " + dip2px(mContext, mAvatarTagHeightMar));
//        mAvatarTagLP.setMargins(mAvatarTagWidthMar,mAvatarTagHeightMar, mAvatarTagWidthMar, mAvatarTagHeightMar);
        mAvatarTagLP.setMargins(dip2px(mContext, mAvatarTagWidthMar), dip2px(mContext, mAvatarTagHeightMar),
                dip2px(mContext, mAvatarTagWidthMar), dip2px(mContext, mAvatarTagHeightMar));
        mTagCircleImage.setLayoutParams(mAvatarTagLP);


        RelativeLayout.LayoutParams mAvatarImageParams = (RelativeLayout.LayoutParams) mAvatarImage.getLayoutParams();
        int mAvatarImageWidthMar = width / 6;
        int mAvatarImageHeightMar = height / 6;

        Log.d("test", "dip2px(mContext, mAvatarImageWidthMar) === " + dip2px(mContext, mAvatarImageWidthMar) +
                "   mAvatarImageWidthMar  = " + mAvatarImageWidthMar);
        mAvatarImageParams.setMargins(dip2px(mContext, mAvatarImageWidthMar), dip2px(mContext, mAvatarImageHeightMar),
                dip2px(mContext, mAvatarImageWidthMar), dip2px(mContext, mAvatarImageHeightMar));
        mAvatarImage.setLayoutParams(mAvatarImageParams);
    }

    public void setTagCircleImage(int id){
        mTagCircleImage.setBackgroundResource(id);
    }

    public void setAvatarImage(int id){
        mAvatarImage.setImageResource(id);
    }

    public void setProgressImage(int id){
        mProgressImage.setBackgroundResource(id);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
