package com.frank.commonadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>通用适配器。封装了convertView复用及findViewById()，提供静态通用ViewHolder，
 * 只需实现{@link CommonAdapter#onBindViewHolder(CommonViewHolder, Object)}方法<p/>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    List<T> mDataList;
    LayoutInflater mInflater;
    int mLayoutId;

    public CommonAdapter(Context context, List<T> dataList, int layoutId) {
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutId, parent, false);
            viewHolder = new CommonViewHolder(mLayoutId, convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        onBindViewHolder(viewHolder, getItem(position));
        return convertView;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        List<T> result = new ArrayList<>(dataList.size());
        for (T item : dataList) {
            result.add(item);
        }
        this.mDataList = result;
    }

    /**
     * 绑定数据项到列表项<br/>
     * 注意：该方法每次`getView()`都会执行，不要在该方法中做复杂或耗时操作，不要每次都设置Listener监听器
     *
     * @param viewHolder 列表项View的Holder，可通过{@link CommonViewHolder#getView(int)}
     *                   获取当前列表项中Id为viewId的View对象，可通过{@link CommonViewHolder#getPosition()}
     *                   获取当前列表项的position
     * @param data       数据列表的数据实体
     */
    public abstract void onBindViewHolder(CommonViewHolder viewHolder, T data);

    public static class CommonViewHolder {

        private int mLayoutId;
        private View mConvertView;
        private int mPosition;
        private SparseArray<View> mViews;

        public CommonViewHolder(int layoutId, View convertView,
                                int position) {
            this.mLayoutId = layoutId;
            this.mConvertView = convertView;
            this.mPosition = position;
            this.mViews = new SparseArray<>();
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public void setPosition(int position) {
            this.mPosition = position;
        }

        public int getPosition() {
            return mPosition;
        }

        public int getLayoutId() {
            return mLayoutId;
        }

        public View getItemView() {
            return mConvertView;
        }

        public CommonViewHolder setText(int viewId, CharSequence text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public CommonViewHolder setTextColor(int viewId, int color) {
            TextView tv = getView(viewId);
            tv.setTextColor(color);
            return this;
        }

        public CommonViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }
        // ...
        // 可新增其它通用方法...

    }

}
