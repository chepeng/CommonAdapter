package com.frank.commonadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 多类型布局适配器<br/>
 * 建议：先将所有类型数据统一为一个实体类：{所有布局类型的类型标志常量，表明当前类型的变量，数据对象}
 * 如：<br/>
 * <code>
 * public static final String TYPE_GAME = "game";<br/>
 * public static final String TYPE_VIDEO = "video";<br/><br/>
 * private String eventType;<br/>
 * private GameBean gameBean;<br/>
 * private VideoBean videoBean;<br/>
 * </code>
 * <p>然后实现3个方法：<br/>
 * ①getViewTypeCount()，返回布局类型数量<br/>
 * ②getItemViewType(int position, T data)，返回0~ getViewTypeCount()-1的整数(可由position或data.getType()决定具体返回值）<br/>
 * ③getLayoutId(int position, T data)，返回布局文件id(可由position或data.getType()决定具体返回值）<p/>
 *
 * @param <T> 数据实体类型
 */
public abstract class MultiTypeCommonAdapter<T> extends CommonAdapter<T> {

    protected MultiTypeSupport<T> mMultiTypeSupport;

    public MultiTypeCommonAdapter(Context context, List<T> dataList, MultiTypeSupport<T> multiTypeSupport) {
        super(context, dataList, -1);
        mMultiTypeSupport = multiTypeSupport;
    }

    @Override
    public int getViewTypeCount() {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getViewTypeCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getItemViewType(position, mDataList.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mMultiTypeSupport == null)
            return super.getView(position, convertView, parent);
        int layoutId = mMultiTypeSupport.getLayoutId(position, getItem(position));
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
