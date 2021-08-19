package com.ilife.happy.activity.test;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ilife.customview.HeadPortraitView;
import com.ilife.happy.R;
import com.ilife.happy.utils.RoundBackgroundColorSpan;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCustomViewActivity extends AppCompatActivity {
    @BindView(R.id.avatar)
    SimpleDraweeView avatar;
    @BindView(R.id.txt)
    TextView textView;

    private HeadPortraitView mHeadPortraitView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);
        mHeadPortraitView = findViewById(R.id.test_view);
        mHeadPortraitView.setTagCircleImage(R.drawable.circle_female);
        mHeadPortraitView.setAvatarImage(R.drawable.ic_test_cus);
        mHeadPortraitView.setProgressImage(R.drawable.happy_progress_3);
//        mHeadPortraitView.setViewSize(400, 400);
//        mHeadPortraitView.setViewSize(120, 120);
//        mHeadPortraitView.setViewSize(1200, 1200);

        avatar.setImageURI("https://static.hanamiapp.com/im/brand_group_icon_v2.png");
//        avatar.setImageURI("https://h-static-cdn.sheva.cn/user/100067/f88b486f-fe44-403d-a2fc-b5dd1e7a5ab3.jpg");

        Spannable wordtoSpan = new SpannableString(textView.getText().toString());
        wordtoSpan.setSpan(new RoundBackgroundColorSpan(this, Color.parseColor("#00a2ff"), Color.parseColor("#FFFFFF")), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordtoSpan);
    }
}
