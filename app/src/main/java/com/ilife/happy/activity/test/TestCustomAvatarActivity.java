package com.ilife.happy.activity.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilife.customview.widget.CircleImageView;
import com.ilife.customview.widget.CustomAvatar;
import com.ilife.happy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCustomAvatarActivity extends AppCompatActivity {
    @BindView(R.id.custom_avatar)
    CustomAvatar customAvatar;
    @BindView(R.id.custom_avatar2)
    CustomAvatar customAvatar2;
    @BindView(R.id.custom_avatar3)
    CustomAvatar customAvatar3;
    @BindView(R.id.circle_imageview)
    CircleImageView circleImageview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_avatar_layout);
        ButterKnife.bind(this);

        customAvatar.setImage(R.drawable.ic_test3);
//        customAvatar.setImageURI("https://wave-cdn.sheva.cn/wave/feed/im/100030/user_100030_23274037881702.PNG");
        customAvatar.showFemaleCircle(true);
        customAvatar.setProgress(5);

        customAvatar2.setImage(R.drawable.ic_test3);
        customAvatar2.showMaleCircle(true);
        customAvatar2.setProgress(0);

        customAvatar3.setImage(R.drawable.ic_test3);
        customAvatar3.showMaleCircle(true);
        customAvatar3.setProgress(7);

//        circleImageview.setImageResource(R.mipmap.ic_test);
        circleImageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_test3));

    }
}
