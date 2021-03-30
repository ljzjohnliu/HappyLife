package com.ilife.happy.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ilife.happy.R;
import com.ilife.happy.fragment.BaseFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CenterFabActivity extends AppCompatActivity {
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        ButterKnife.bind(this);

        initData();
        initView();
        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>(4);

        // create music fragment and add it
        BaseFragment musicFragment = new BaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", getString(R.string.home));
        musicFragment.setArguments(bundle);

        // create backup fragment and add it
        BaseFragment backupFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.weather));
        backupFragment.setArguments(bundle);

        // create friends fragment and add it
        BaseFragment favorFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.favor));
        favorFragment.setArguments(bundle);

        // create friends fragment and add it
        BaseFragment visibilityFragment = new BaseFragment();
        bundle = new Bundle();
        bundle.putString("title", getString(R.string.mine));
        visibilityFragment.setArguments(bundle);


        // add to fragments for adapter
        fragments.add(musicFragment);
        fragments.add(backupFragment);
        fragments.add(favorFragment);
        fragments.add(visibilityFragment);
    }


    /**
     * change BottomNavigationViewEx style
     */
    private void initView() {
        bottomNavigationViewEx.enableItemShiftingMode(true);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.setIconSize(36f, 36f);
        bottomNavigationViewEx.setTextSize(12);

        // set adapter
        adapter = new VpAdapter(this, fragments);
        viewPager2.setAdapter(adapter);
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
