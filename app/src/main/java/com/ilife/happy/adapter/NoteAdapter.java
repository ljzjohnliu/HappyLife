package com.ilife.happy.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ilife.dataroom.model.NoteModel;
import com.ilife.happy.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<NoteModel> mData;

    private OnItemClickListener onItemClickListener;
    private OnBidStatClickListener onBidStatClickListener;

    public NoteAdapter(List<NoteModel> data) {
        this.mData = data;
    }

    public List<NoteModel> getData() {
        return mData;
    }

    public void refreshData(List<NoteModel> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void resetDatas() {
        mData.clear();
    }

    public void updateList(List<NoteModel> newDatas) {
        if (newDatas != null) {
            mData.addAll(newDatas);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public void setOnBidStatClickListener(OnBidStatClickListener listener) {
        this.onBidStatClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_layout, parent, false);
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

//        holder.setIsRecyclable(false);
        Log.d("xxx", "onBindViewHolder: position = " + position + ", size = " + mData.size());
        Log.d("xxx", "onBindViewHolder: title = " + mData.get(position).title + ", content = " + mData.get(position).content);
        holder.noteDateTv.setText(mData.get(position).date);
        holder.noteTitleTv.setText(mData.get(position).title);
        holder.noteContentTv.setText(mData.get(position).content);
        holder.stateView.setChecked(mData.get(position).state == 1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public interface OnBidStatClickListener {
        void onBidStatClick(View view, int position, int state);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picImg;
        TextView noteDateTv;
        TextView noteTitleTv;
        TextView noteContentTv;
        CheckBox stateView;

        public ViewHolder(View itemView) {
            super(itemView);
            picImg = itemView.findViewById(R.id.note_bg);
            noteDateTv = itemView.findViewById(R.id.note_date);
            noteTitleTv = itemView.findViewById(R.id.note_title);
            noteContentTv = itemView.findViewById(R.id.note_content);
            stateView = itemView.findViewById(R.id.note_state);
        }
    }
}
