package com.ilife.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;

import com.ilife.common.utils.DensityUtil;
import com.ilife.customview.R;

public class CustomAvatar extends RelativeLayout {
    private static final String TAG = "CustomAvatar";

    private static final float avatarScale = 0.6f;
    private static final float avatarCircleScale = 0.15f;

    private CircleImageView avatar;
    private ImageView avatarCircle;
    private ImageView progressView;

    private Drawable defaultAvatar;
    private boolean isShowCircle;
    private boolean isShowProgress;
    private int defaultProgress;
    private int happyProgress;

    private Context mContext;

    public CustomAvatar(Context context) {
        super(context);
        mContext = context;
    }

    public CustomAvatar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //加载视图的布局
        LayoutInflater.from(context).inflate(R.layout.avatar_with_circle, this, true);

        //加载自定义的属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomAvatar);
        defaultAvatar = a.getDrawable(R.styleable.CustomAvatar_defaultAvatar);
        isShowCircle = a.getBoolean(R.styleable.CustomAvatar_isShowCircle, false);
        isShowProgress = a.getBoolean(R.styleable.CustomAvatar_isShowHappyProgress, false);
        defaultProgress = a.getInt(R.styleable.CustomAvatar_defaultHappyProgress, 0);
        happyProgress = a.getInt(R.styleable.CustomAvatar_happyProgress, 0);
        Log.d("xxx", "CustomAvatar: isShowCircle = " + isShowCircle + ", isShowProgress = " + isShowProgress + ", happyProgress = " + happyProgress + ", defaultAvatar = " + defaultAvatar);

        //回收资源，这一句必须调用
        a.recycle();
    }

    /**
     * 此方法会在所有的控件都从xml文件中加载完成后调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //获取子控件
        avatar = findViewById(R.id.avatar);
        avatarCircle = findViewById(R.id.avatar_circle);
        progressView = findViewById(R.id.avatar_progress);

//        avatar.setImageResource(mContext.getDrawable(R.drawable.ic_test));
//        avatar.setImageResource(R.drawable.ic_test);
//        avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_test));

//        GenericDraweeHierarchy hierarchy = avatar.getHierarchy();
//        hierarchy.setPlaceholderImage(defaultAvatar);

        avatarCircle.setVisibility(isShowCircle ? View.VISIBLE : View.GONE);
        progressView.setVisibility(isShowProgress ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int avatarPadding = (int) (DensityUtil.px2dip(mContext, avatar.getMeasuredWidth()) * avatarScale * mContext.getResources().getDisplayMetrics().density / 3.84);
        Log.d(TAG, "onMeasure: avatarPadding = " + avatarPadding + ", density = " + mContext.getResources().getDisplayMetrics().density);
        //todo  使用padding不生效很困惑为什么？  * mContext.getResources().getDisplayMetrics().density / 3.84
//        avatar.setPadding(avatarPadding, avatarPadding, avatarPadding, avatarPadding);
        LayoutParams lp = (LayoutParams) avatar.getLayoutParams();
        lp.setMargins(avatarPadding, avatarPadding, avatarPadding, avatarPadding);

        int avatarCirclePadding = (int) (DensityUtil.px2dip(mContext, avatarCircle.getMeasuredWidth()) * avatarCircleScale * mContext.getResources().getDisplayMetrics().density / 3.84);
        Log.d(TAG, "onMeasure: avatarCirclePadding = " + avatarCirclePadding);
        avatarCircle.setPadding(avatarCirclePadding, avatarCirclePadding, avatarCirclePadding, avatarCirclePadding);
    }

    public void setImageURI(Uri uri) {
        avatar.setImageURI(uri);
    }

    public void setImage(@DrawableRes int resId) {
        Log.d(TAG, "setImage: resId = " + resId);
        avatar.setImageResource(resId);
//        avatar.setImageResource(R.drawable.ic_test);
    }

    public void setImageURI(String uriString) {
        avatar.setImageURI(Uri.parse(uriString));
    }

    public void showFemaleCircle(boolean isShow) {
        avatarCircle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        avatarCircle.setImageResource(R.drawable.circle_female);
    }

    public void showMaleCircle(boolean isShow) {
        avatarCircle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        avatarCircle.setImageResource(R.drawable.circle_male);
    }

    public void showProgress(boolean isShow) {
        progressView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setProgress(int progress) {
        progressView.setVisibility(VISIBLE);
        switch (progress) {
            case 0:
                progressView.setImageResource(R.drawable.happy_progress_0);
                break;
            case 1:
                progressView.setImageResource(R.drawable.happy_progress_1);
                break;
            case 2:
                progressView.setImageResource(R.drawable.happy_progress_2);
                break;
            case 3:
                progressView.setImageResource(R.drawable.happy_progress_3);
                break;
            case 4:
                progressView.setImageResource(R.drawable.happy_progress_4);
                break;
            case 5:
                progressView.setImageResource(R.drawable.happy_progress_5);
                break;
            case 6:
                progressView.setImageResource(R.drawable.happy_progress_6);
                break;
            case 7:
                progressView.setImageResource(R.drawable.happy_progress_7);
                break;
        }
    }

    /**
     * 设置按钮点击事件监听器
     *
     * @param listener
     */
    public void setOnCustonAvatarClickListener(OnClickListener listener) {
        avatar.setOnClickListener(listener);
    }
}