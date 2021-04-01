package com.ilife.happy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ilife.happy.R;
import com.ilife.happy.fragment.BaseFragment2;
import com.ilife.happy.fragment.HomeFragment;
import com.ilife.happy.fragment.MineFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CenterFabActivity extends BaseActivity {
    private static final String TAG = "CenterFabActivity";

    @BindView(R.id.bnve)
    BottomNavigationViewEx bottomNavigationViewEx;
    @BindView(R.id.vp)
    ViewPager2 viewPager2;
    @BindView(R.id.fab)
    FloatingActionButton centerButton;

    private VpAdapter adapter;
    private List<Fragment> fragments;

    @Override
    public void initData() {
        fragments = new ArrayList<>(4);

        // create home fragment and add it
        HomeFragment homeFragment = new HomeFragment();

        Bundle bundle;
        // create weather fragment and add it
        BaseFragment2 weatherFragment = new BaseFragment2();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.weather));
        weatherFragment.setArguments(bundle);

        // create favor fragment and add it
        BaseFragment2 favorFragment = new BaseFragment2();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.favor));
        favorFragment.setArguments(bundle);

        // create mine fragment and add it
        MineFragment mineFragment = MineFragment.Companion.newInstance(getString(R.string.mine));

        // add to fragments for adapter
        fragments.add(homeFragment);
        fragments.add(weatherFragment);
        fragments.add(favorFragment);
        fragments.add(mineFragment);

        // set adapter
        adapter = new VpAdapter(this, fragments);
        viewPager2.setAdapter(adapter);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_layout;
    }

    /**
     * change BottomNavigationViewEx style
     */
    @Override
    public void initView() {
        bottomNavigationViewEx.enableItemShiftingMode(true);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.setIconSize(36f, 36f);
        bottomNavigationViewEx.setTextSize(12);

        initEvent();
    }

    /**
     * set listeners
     */
    private void initEvent() {
        // set listener to change the current item of view pager when click bottom nav item
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        position = 0;
                        break;
                    case R.id.i_weather:
                        position = 1;
                        break;
                    case R.id.i_favor:
                        position = 2;
                        break;
                    case R.id.i_mine:
                        position = 3;
                        break;
                    case R.id.i_empty: {
                        return false;
                    }
                }
                if (previousPosition != position) {
                    viewPager2.setCurrentItem(position, false);
                    previousPosition = position;
                    Log.i(TAG, "-----bottomNavigationViewEx-------- previous item:" + bottomNavigationViewEx.getCurrentItem() + " current item:" + position + " ------------------");
                }
                return true;
            }
        });

        // set listener to change the current checked item of bottom nav when scroll view pager
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.i(TAG, "-----ViewPager-------- previous item:" + bottomNavigationViewEx.getCurrentItem() + ", current item:" + position + ", positionOffset = " + positionOffset + ", positionOffsetPixels = " + positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    bottomNavigationViewEx.setCurrentItem(position >= 2 ? position + 1 : position);
                }
            }
        });

        centerButton.setOnClickListener(view -> {
            Log.d(TAG, "Center btn is clicked!: ");
            Toast.makeText(CenterFabActivity.this, "Center", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * view pager adapter
     */
    private static class VpAdapter extends FragmentStateAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentActivity fa, List<Fragment> data) {
            super(fa);
            this.data = data;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
