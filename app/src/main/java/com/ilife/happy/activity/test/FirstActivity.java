package com.ilife.happy.activity.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ilife.happy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 1;

    @BindView(R.id.content)
    TextView contentTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_first);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.second)
    public void onClick() {
//        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
//        intent.putExtra("from", "First Page");
//        startActivityForResult(intent, REQUEST_CODE);
        launcher.launch("First Page");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.d("TAG", "onActivityResult: requestCode = " + requestCode + ", resultCode = " + resultCode);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            contentTv.setText(TextUtils.isEmpty(data.getStringExtra("result")) ? "没回传任何数据" : data.getStringExtra("result"));
//        }
//    }

    ActivityResultLauncher launcher = registerForActivityResult(new ResultContract(), new ActivityResultCallback<String>() {
        @Override
        public void onActivityResult(String result) {
            Toast.makeText(FirstActivity.this, result, Toast.LENGTH_SHORT).show();
            contentTv.setText(TextUtils.isEmpty(result) ? "没回传任何数据" : result);
        }
    });

    class ResultContract extends ActivityResultContract<String, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, String input) {
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            intent.putExtra("from", input);
            return intent;
        }

        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            return intent.getStringExtra("result");
        }
    }
}
