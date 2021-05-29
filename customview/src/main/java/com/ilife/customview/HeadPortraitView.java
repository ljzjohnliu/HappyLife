package com.ilife.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

public class HeadPortraitView extends RelativeLayout {
    private String mTagViewAttrs;
    private String mCircleImageAttrs;
    private String mProgressAttrs;
    private int mSexAttrs;

    private FrameLayout mFrameLayout;
    private ImageView mTagCircleImage;
    private CircleImageView mCircleImage;
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
        mFrameLayout = view.findViewById(R.id.image_view_frame_layout);
        mTagCircleImage = view.findViewById(R.id.tag_view);
        mCircleImage = view.findViewById(R.id.circle_view_one);
        mProgressImage = view.findViewById(R.id.circle_progress_image);

//        init(attrs);
    }

    public void init(AttributeSet attrs){
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.circleCustomView);
        mTagViewAttrs = array.getString(R.styleable.circleCustomView_circleView_image);
        mCircleImageAttrs = array.getString(R.styleable.circleCustomView_concentricView_image);
        mProgressAttrs = array.getString(R.styleable.circleCustomView_progressView_image);
        mSexAttrs = array.getInt(R.styleable.circleCustomView_sex,0);
        array.recycle();

//        Drawable mSexCircleDrawable = getResources().getDrawable(mTagViewAttrs);
//        mTagCircleImage.setImageDrawable(mSexCircleDrawable);
//
//        Drawable mCircleDrawable = getResources().getDrawable(mCircleImageAttrs);
//        mCircleImage.setImageDrawable(mCircleDrawable);
//
//        Drawable mProgressDrawable = getResources().getDrawable(mProgressImage);
//        mProgressImage.setImageDrawable(mProgressDrawable);
    }

    public void setViewSize(int width, int height){
        FrameLayout.LayoutParams  mCustomLayoutParams = (FrameLayout.LayoutParams) mTagCircleImage.getLayoutParams();
//                new FrameLayout.LayoutParams(width,height);
        mCustomLayoutParams.width = width;
        mCustomLayoutParams.height = height;
        mTagCircleImage.setLayoutParams(mCustomLayoutParams);

        int mcirWidth = width / 30 * 23;
        int mcirHeight= height / 30 * 23;
        FrameLayout.LayoutParams  mConcentricCircleParams = (FrameLayout.LayoutParams) mCircleImage.getLayoutParams();
//                new FrameLayout.LayoutParams(mcirWidth,mcirHeight);
        mConcentricCircleParams.width = mcirWidth;
        mConcentricCircleParams.height = mcirHeight;
        mCircleImage.setLayoutParams(mConcentricCircleParams);

        FrameLayout.LayoutParams  mConcentricCircleProgressParams = (FrameLayout.LayoutParams) mProgressImage.getLayoutParams();
//                new FrameLayout.LayoutParams(width,height);
        mConcentricCircleProgressParams.width = width;
        mConcentricCircleProgressParams.height = height;
        mProgressImage.setLayoutParams(mConcentricCircleProgressParams);
    }

    public void setTagCircleImage(int id){
//        mTagCircleImage.setImageResource(id);
        mTagCircleImage.setBackgroundResource(id);
    }

    public void setAvatarImage(int id){
        mCircleImage.setImageResource(id);
//        mCircleImage.setBackgroundResource(id);
    }

    public void setProgressImage(int id){
        mProgressImage.setBackgroundResource(id);
    }
}
