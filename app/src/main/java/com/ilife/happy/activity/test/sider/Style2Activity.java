package com.ilife.happy.activity.test.sider;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.ilife.happy.R;

public class Style2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_style2);

//        NavigationView navigationview = (NavigationView) findViewById(R.id.navigation_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, null, 0, 0);
//        drawer.setDrawerListener(toggle);//初始化状态
//        toggle.syncState();

        /*---------------------------添加头布局和尾布局-----------------------------*/

//        WebView mWebView = findViewById(R.id.webview);
//        mWebView.loadUrl("https://www.baidu.com/");
        //获取xml头布局view
//        View headerView = navigationview.getHeaderView(0);
//        //添加头布局的另外一种方式
//        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);
//
//        //寻找头部里面的控件
//        ImageView imageView = headerView.findViewById(R.id.iv_head);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
