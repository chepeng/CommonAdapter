package com.frank.commonadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * <p>多类型布局适配器。简化多种布局文件的View操作。</p>
 * <p>建议：将所有类型数据统一为一个实体类：{所有布局类型的类型标志常量，表明当前类型的变量，数据对象}</p>
 * 如：<br/>
 * <pre class="prettyprint">
 * public static final String TYPE_GAME = "game";
 * public static final String TYPE_VIDEO = "video";
 * private String eventType;
 * private GameBean gameBean;
 * private VideoBean videoBean;
 * </pre>
 * 利用组装好的实体类List并实现3个方法：
 * <ul>
 * <li>getViewTypeCount()，返回布局类型个数</li>
 * <li>getItemViewType(int position, T data)，返回0~ getViewTypeCount()-1的整数(可由position或data.getType()决定具体返回值）</li>
 * <li>getLayoutId(int position, T data)，返回布局文件id(可由position或data.getType()决定具体返回值）</li>
 * </ul>
 *
 * @param <T> 数据实体类型
 */
public abstract class MultiTypeCommonAdapter<T> extends CommonAdapter<T> {

    public MultiTypeCommonAdapter(Context context, List<T> dataList) {
        super(context, dataList, -1);
    }

    @Override
    public int getViewTypeCount() {
        return getItemViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = getLayoutId(position, getItem(position));
        CommonViewHolder viewHolder;
        if (convertView == null) {
            //Log.e("shang", "getView->inflate:" + position);
            convertView = mInflater.inflate(layoutId, parent, false);
            viewHolder = new CommonViewHolder(mContext, layoutId, convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder, getItem(position));
        return convertView;
    }

    public abstract int getLayoutId(int position, T data);

    public abstract int getItemViewTypeCount();

    public abstract int getItemViewType(int position, T data);
}
