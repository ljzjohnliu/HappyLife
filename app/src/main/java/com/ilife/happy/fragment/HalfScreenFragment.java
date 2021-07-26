package com.ilife.happy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ilife.happy.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HalfScreenFragment extends Fragment {

    private static final String TAG = "HalfScreenFragment";

    public final static String KEY_TYPE = "key_type";

    @BindView(R.id.title)
    TextView titleTv;
    @BindView(R.id.content)
    TextView content;

    private int position = -1;
    private String title = "null";
    private OnCloseBtnClickListener listener;

    public interface OnCloseBtnClickListener {
        void onCloseBtnClick(int position);
    }

    public void setOnCloseBtnClickListener(OnCloseBtnClickListener listener) {
        this.listener = listener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private int type;

//    public StickerActionListener listener;

    public static HalfScreenFragment getInstance() {
        return new HalfScreenFragment();
    }

//    public void setListener(StickerActionListener listener) {
//        this.listener = listener;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            this.type = bundle.getInt(KEY_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_half, container, false);
        ButterKnife.bind(this, rootView);

        titleTv.setText(title.substring(64, 69));
        content.setText(title);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.close_btn)
    public void onClick() {
        Log.d(TAG, "onClick: close_btn is clicked!!");
        if (listener != null) {
            listener.onCloseBtnClick(position);
        }
//        getFragmentManager().popBackStack();
    }
}
