package com.ilife.happy.activity.test.flip;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ilife.happy.R;

public class FlipMainActivity extends AppCompatActivity {

    GestureDetector mGestureDetector;  //手势检测对象
    private static final String TAG = "MainActivity";
    private static final int FLING_MIN_DISTANCE = 50;//滑动最小的距离
    private static final int FLING_MIN_VELOCITY = 0;//滑动最小的速度

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_flip_main_layout);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                Toast.makeText(FlipMainActivity.this, "向左手势", Toast.LENGTH_SHORT).show();
                Intent leftIntent = new Intent(FlipMainActivity.this, FlipLeftActivity.class);
                startActivity(leftIntent);
                //滑动动画
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                Toast.makeText(FlipMainActivity.this, "向右手势", Toast.LENGTH_SHORT).show();
                Intent rightIntent = new Intent(FlipMainActivity.this, FlipRightActivity.class);
                startActivity(rightIntent);
                //滑动动画
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
            return false;
        }
    }
}
