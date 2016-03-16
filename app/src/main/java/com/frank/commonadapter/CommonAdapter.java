package com.frank.commonadapter;
/**
 * 通用适配器，封装复用，静态ViewHolder
 * reference hongyangAndroid/base-adapter
 */

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutId, parent,
                    false);
            holder = new CommonViewHolder(mContext, parent, layoutId, convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (CommonViewHolder) convertView.getTag();
        }
        bindData(holder, getItem(position));
        return convertView;
    }

    public abstract void bindData(CommonViewHolder holder, T t);

    public static class CommonViewHolder {

        private SparseArray<View> mViews;
        public int mPosition;
        private View mConvertView;
        private Context mContext;
        private int mLayoutId;

        public CommonViewHolder(Context context, ViewGroup parent, int layoutId, View convertView,
                                int position) {
            mContext = context;
            mLayoutId = layoutId;
            this.mPosition = position;
            this.mViews = new SparseArray<View>();
            mConvertView = convertView;
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public int getPosition() {
            return mPosition;
        }

        public int getLayoutId() {
            return mLayoutId;
        }

        public CommonViewHolder setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public CommonViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }
        //...
        //新增其它通用方法...
    }
}
