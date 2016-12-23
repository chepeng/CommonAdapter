package com.frank.commonadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class RVMultiTypeCommonAdapter<T> extends RVCommonAdapter<T> {

    public RVMultiTypeCommonAdapter(Context context, List<T> dataList) {
        super(context, dataList, -1);
    }

    @Override
    public RVCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(viewType, parent, false);
        RVCommonViewHolder vh = new RVCommonViewHolder(viewType, v);
        setListener(parent, viewType, v, vh);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutId(position, mDataList.get(position));
    }

    /**
     * 返回布局文件id(由position或data.getType()决定具体返回值）
     *
     * @param position position
     * @param data     数据实体
     * @return 布局文件id
     */
    public abstract int getLayoutId(int position, T data);
}
