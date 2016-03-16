package com.frank.commonadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多类型复杂布局适配器
 * 使用时，先将所有种类数据项统一为一个实体类：{所有布局类型的标志常量，表明当前类型的变量，数据对象}
 * 如：
 * public static final String TYPE_GAME = "game";
 * public static final String TYPE_VIDEO = "video";
 *
 * private String eventType;
 * private GameBean gameBean;
 * private VideoBean videoBean;
 * 然后实现3个方法：
 * ①getViewTypeCount() ，返回类型数量
 * ②getItemViewType(int position, T t)，返回0~size -1的整数(根据position位置值或t.getEventType()业务逻辑）
 * ③getLayoutId(int position, T t)，返回布局文件id(根据position位置值或t.getEventType()业务逻辑）
 * @param <T>
 */
public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas,
                                  MultiItemTypeSupport<T> multiItemTypeSupport) {
        super(context, datas, -1);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getViewTypeCount();
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiItemTypeSupport != null)
            return mMultiItemTypeSupport.getItemViewType(position,
                    mDatas.get(position));
        return super.getItemViewType(position);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mMultiItemTypeSupport == null)
            return super.getView(position, convertView, parent);
        int layoutId = mMultiItemTypeSupport.getLayoutId(position,
                getItem(position));
        CommonViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutId, parent,
                    false);
            holder = new CommonViewHolder(mContext, parent, layoutId, convertView, position);
            convertView.setTag(holder);
        } else {
            holder = (CommonViewHolder) convertView.getTag();
            holder.mPosition = position;
        }
        bindData(holder, getItem(position));
        return convertView;
    }

}
