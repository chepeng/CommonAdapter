package com.frank.commonadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 带头布局列表项的ListView通用适配器<br/>
 * 需实现{@link CommonAdapter#bindData(CommonViewHolder, Object)}方法,
 * 如果Object为 {@code null} 表明该列表项是HeaderView
 *
 * @param <T> 数据实体类型
 */
public abstract class WithHeaderAdapter<T> extends CommonAdapter<T> {

    private int mHeaderLayoutId;
    private int mItemLayoutId;

    public WithHeaderAdapter(Context context, final int headerLayoutId, final int itemLayoutId, List<T> dataList) {
        super(context, dataList, -1);
        this.mHeaderLayoutId = headerLayoutId;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size() + 1;
    }

    @Override
    public T getItem(int position) {
        if (position == 0) {
            return null;
        }
        return super.getItem(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = (position == 0 ? mHeaderLayoutId : mItemLayoutId);
        CommonViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutId, parent, false);
            viewHolder = new CommonViewHolder(mContext, parent, layoutId, convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        bindData(viewHolder, getItem(position));
        return convertView;
    }

}
