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
 * 组装好的实体类List并实现以下方法：
 * <ul>
 * <li>getItemViewTypeCount(). 返回布局类型个数</li>
 * <li>getItemViewType(int layoutId, int position, T data). 返回[0,getItemViewTypeCount()-1]的整数(由layoutId或position决定具体返回值）</li>
 * <li>getLayoutId(int position, T data). 返回布局文件id(由position或data.getType()决定具体返回值）</li>
 * <li>onBindViewHolder(CommonViewHolder holder, T data). 绑定ViewHolder(由holder.getLayoutId()或data.getType()决定具体View绑定）</li>
 * </ul>
 *
 * @param <T> 数据实体类型
 */
public abstract class MultiTypeCommonAdapter<T> extends CommonAdapter<T> {

    private static final String TAG = "MultiTypeCommonAdapter";

    public MultiTypeCommonAdapter(Context context, List<T> dataList) {
        super(context, dataList, -1);
    }

    @Override
    public int getViewTypeCount() {
        return getItemViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mDataList.size()) {
            return super.getItemViewType(position);
        }
        return getItemViewType(getLayoutId(position, mDataList.get(position)), position, mDataList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = getLayoutId(position, getItem(position));
        CommonViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(layoutId, parent, false);
            viewHolder = new CommonViewHolder(layoutId, convertView, position);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }
        onBindViewHolder(viewHolder, getItem(position));
        return convertView;
    }

    /**
     * 返回布局类型个数
     * @return 布局类型个数
     */
    public abstract int getItemViewTypeCount();

    /**
     * 返回[0,getItemViewTypeCount()-1]的整数(由layoutId或position决定具体返回值）
     * @param layoutId 当前布局id
     * @param position position
     * @param data 当前位置数据实体
     * @return [0,getItemViewTypeCount()-1]的整数
     */
    public abstract int getItemViewType(int layoutId, int position, T data);

    /**
     * 返回布局文件id(由position或data.getType()决定具体返回值）
     * @param position position
     * @param data 数据实体
     * @return 布局文件id
     */
    public abstract int getLayoutId(int position, T data);

}
