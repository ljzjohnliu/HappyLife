package com.ilife.happy.activity.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilife.customview.HeadPortraitView;
import com.ilife.happy.R;

public class TestCustomViewActivity extends AppCompatActivity {
    private HeadPortraitView mHeadPortraitView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        mHeadPortraitView = findViewById(R.id.test_view);
        mHeadPortraitView.setTagCircleImage(R.drawable.circle_female);
        mHeadPortraitView.setAvatarImage(R.drawable.ic_test_cus);
        mHeadPortraitView.setProgressImage(R.drawable.happy_progress_3);
//        mHeadPortraitView.setViewSize(400, 400);
//        mHeadPortraitView.setViewSize(120, 120);
//        mHeadPortraitView.setViewSize(1200, 1200);
    }
}
