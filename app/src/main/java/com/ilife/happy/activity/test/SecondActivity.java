package com.ilife.happy.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilife.happy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {
    @BindView(R.id.content)
    TextView contentTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_second);
        ButterKnife.bind(this);

        if(getIntent() != null) {
            getIntent().getStringExtra("from");
            contentTv.setText(TextUtils.isEmpty(getIntent().getStringExtra("from")) ? "没传递任何数据" : getIntent().getStringExtra("from"));
        }
    }

    @OnClick(R.id.back_first)
    public void onClick() {
        Intent intent = new Intent();
        intent.putExtra("result", "I am from Second Page");
        setResult(RESULT_OK, intent);
        finish();
    }
}
