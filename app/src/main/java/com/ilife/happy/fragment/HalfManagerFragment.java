package com.ilife.happy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ilife.common.utils.DensityUtil;
import com.ilife.happy.R;
import com.ilife.happy.adapter.HalfFragmentAdapter;
import com.ilife.happy.utils.HorizontalItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HalfManagerFragment extends DialogFragment implements HalfScreenFragment.OnCloseBtnClickListener {
    public static final String TAG = "HalfManagerFragment";

    public final static String KEY_FROM_TYPE = "key_from_type";
    public static final String KEY_GROUP_ID = "key_group_id";

    public final static int TYPE_FROM_CHAT = 0;
    public final static int TYPE_FROM_CARD = 1;

    private int type = TYPE_FROM_CHAT;
    private String groupId;

    @BindView(R.id.avatar_list)
    RecyclerView avatarList;
    @BindView(R.id.half_fragment_vp)
    ViewPager2 viewpage;

    List<Fragment> mFragments = new ArrayList<>();
    HalfFragmentAdapter halfFragmentManagerAdapter;
    private List<String> list = new ArrayList<>();
    private AvatarAdapter avatarAdapter;



    private void mockAvatarData() {
        list.add("https://b-ssl.duitang.com/uploads/item/201807/08/20180708095827_SYPL3.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/01/20181101093301_u2NKu.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223950_vygmz.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201807/11/20180711091152_FakCJ.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/01/20181101093301_u2NKu.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/01/20181101093301_u2NKu.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
        list.add("https://b-ssl.duitang.com/uploads/item/201810/30/20181030153225_mixve.thumb.700_0.jpg");
        list.add("https://b-ssl.duitang.com/uploads/item/201811/04/20181104223952_zfhli.thumb.700_0.jpeg");
    }

    private void resizeWindowHeight(int height) {
        final Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = DensityUtil.dip2px(getActivity(), height);//WindowManager.LayoutParams.WRAP_CONTENT;
        Log.d(TAG, "onCreateView: wlp.height = " + wlp.height);
        window.setAttributes(wlp);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialogStyle);

        Bundle bundle = getArguments();
        if (bundle != null) {
            this.type = bundle.getInt(KEY_FROM_TYPE);
            this.groupId = bundle.getString(KEY_GROUP_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.fragment_half_manager, null, false);
        ButterKnife.bind(this, rootView);
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        resizeWindowHeight(320);

        mockAvatarData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        avatarList.setLayoutManager(linearLayoutManager);
        avatarList.addItemDecoration(new HorizontalItemDecoration(10, getActivity()));

        avatarAdapter = new AvatarAdapter(type, list);
        avatarAdapter.setOnItemClickListener(new AvatarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d(TAG, "onItemClick: position = " + position);
                HalfScreenFragment halfFragment = new HalfScreenFragment();
                halfFragment.setPosition(position);
                halfFragment.setTitle(list.get(position));
                halfFragment.setOnCloseBtnClickListener(HalfManagerFragment.this);
                mFragments.clear();
                mFragments.add(halfFragment);
                halfFragmentManagerAdapter = new HalfFragmentAdapter(getActivity(), mFragments);
                viewpage.setAdapter(halfFragmentManagerAdapter);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d(TAG, "onItemLongClick: position = " + position);
            }
        });
        avatarList.setAdapter(avatarAdapter);

        initVierpage();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;

        window.setAttributes(windowParams);
    }

    private void initVierpage() {
        HalfScreenFragment halfFragment = new HalfScreenFragment();
        halfFragment.setTitle(list.get(0));
        halfFragment.setPosition(0);
        halfFragment.setOnCloseBtnClickListener(this);
        mFragments.add(halfFragment);
        halfFragmentManagerAdapter = new HalfFragmentAdapter(getActivity(), mFragments);
        viewpage.setAdapter(halfFragmentManagerAdapter);

        viewpage.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewpage.setCurrentItem(position);
            }
        });
    }

    @Override
    public void onCloseBtnClick(int position) {
        Log.d(TAG, "onCloseBtnClick: position = " + position);
        list.remove(position);
        avatarAdapter.getData().remove(position);
        avatarAdapter.notifyDataSetChanged();

        HalfScreenFragment halfFragment = new HalfScreenFragment();
        halfFragment.setPosition(position);
        halfFragment.setTitle(list.get(position));
        halfFragment.setOnCloseBtnClickListener(HalfManagerFragment.this);
        mFragments.clear();
        mFragments.add(halfFragment);
        halfFragmentManagerAdapter = new HalfFragmentAdapter(getActivity(), mFragments);
        viewpage.setAdapter(halfFragmentManagerAdapter);
    }

    public static class Builder {
        private int type;
        private String groupId;

        public Builder() {
        }

        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        public Builder setGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public HalfManagerFragment build() {
            HalfManagerFragment fragment = new HalfManagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(HalfManagerFragment.KEY_FROM_TYPE, type);
            bundle.putString(HalfManagerFragment.KEY_GROUP_ID, groupId);
            fragment.setArguments(bundle);
            return fragment;
        }
    }

    public static class AvatarAdapter extends RecyclerView.Adapter<AvatarViewHolder> {
        private int type;
        private List<String> items;

        private OnItemClickListener onItemClickListener;

        public AvatarAdapter(int type, List<String> items) {
            this.type = type;
            this.items = new ArrayList<>(items);
        }

        public List<String> getData() {
            return items;
        }

        public void refreshData(List<String> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.avatar_item, parent, false);

            return new AvatarViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {

            holder.avatarImg.setImageURI(items.get(position));

            holder.avatarImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, position);
                    }
                }
            });

            holder.avatarImg.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemLongClick(view, position);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onItemLongClick(View view, int position);
        }
    }

    public static class AvatarViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView avatarImg;

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImg = itemView.findViewById(R.id.avatar_img);
        }
    }

    public interface StickerActionListener {
        void sendSticker(String stickerUrl);
    }
}
