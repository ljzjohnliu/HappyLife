package com.ilife.happy.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilife.happy.R;

public class SpinnerAdapter extends BaseAdapter {
    private int[] image;
    private String[] province;
    private Context context;
    private ViewHolder mViewHolder;

    /**
     * function:通过构造方法传递数据
     * @param image：图片数据
     * @param province：省份数据
     * @param context：上下文
     */
    public SpinnerAdapter(int[] image, String[] province, Context context) {
        this.image = image;
        this.province = province;
        this.context = context;
    }

    //item的总长度
    @Override
    public int getCount() {
        return image.length;
    }

    //获取item的标识
    @Override
    public Object getItem(int position) {
        return position;
    }

    //获取item的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    //获取item视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //判断是否有可复用的view对象，没有的话走if，有的话走else
        if (convertView==null){
            //找到我们自定义的行布局
            convertView = View.inflate(context, R.layout.spinner_item_layout, null);
            //实例化ViewHolder内部类
            mViewHolder = new ViewHolder();
            //给ViewHolder里的控件初始化，通过我们自定义的行布局
            mViewHolder.picture = (ImageView) convertView.findViewById(R.id.picture_img);
            mViewHolder.province = (TextView) convertView.findViewById(R.id.province_tv);
            //给convertView设置一个标签
            convertView.setTag(mViewHolder);
        }else {
            //获取我们设置过的标签，实现复用convertView
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        //分别给每个控件设置相应的内容
        mViewHolder.province.setText(province[position]);
        mViewHolder.picture.setImageResource(image[position]);
        //返回convertView对象
        return convertView;
    }
    //新建ViewHolder内部类，用来定义我们行布局中所用到的控件
    class ViewHolder{
        private ImageView picture;
        private TextView province;
    }
}