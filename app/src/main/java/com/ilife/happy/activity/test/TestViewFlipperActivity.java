package com.ilife.happy.activity.test;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ilife.happy.R;

public class TestViewFlipperActivity extends AppCompatActivity {

    private TextView textView;
    private ViewFlipper viewFlipper;
    private SimpleDraweeView animView;
    private SimpleDraweeView statusView;

    private ViewFlipper viewFlipper2;
    private TextView textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_flipper);

        animView = findViewById(R.id.status_anim);
        animView.setVisibility(View.GONE);
        statusView = findViewById(R.id.status_img);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(R.drawable.anim_down_large))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        statusView.setController(controller);


        viewFlipper = findViewById(R.id.filpper);
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_custom, null);
            textView = view.findViewById(R.id.myte1);
            textView.setText("幸福值.200" + i);
            viewFlipper.addView(view);
        }

        viewFlipper2 = findViewById(R.id.flipper);
        for (int i = 0; i < 5; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_custom, null);
            textView2 = view.findViewById(R.id.myte1);
            textView2.setText("幸福值.2000" + i);
            viewFlipper2.addView(view);
        }
    }

}
