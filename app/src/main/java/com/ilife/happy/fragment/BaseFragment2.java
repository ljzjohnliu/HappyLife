package com.ilife.happy.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ilife.happy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseFragment2 extends Fragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get title
        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_base, null);
        ButterKnife.bind(this, view);

        tvTitle.setText(title);
        return view;
    }
}
