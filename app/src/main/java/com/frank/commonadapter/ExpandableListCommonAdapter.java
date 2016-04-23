package com.frank.commonadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

/**
 * <p>{@link android.widget.ExpandableListView}的通用适配器。封装了convertView复用及findViewById()，
 * 子类只须实现：
 * <ol>
 * <li>{@link ExpandableListCommonAdapter#onBindGroupViewHolder(int, CommonAdapter.CommonViewHolder, Object)}</li>
 * <li>{@link ExpandableListCommonAdapter#getChildrenCount(int, Object)}</li>
 * <li>{@link ExpandableListCommonAdapter#getChild(int, int, Object)}</li>
 * </ol>
 * 三个方法。<br></br>
 * 在<font color="blue">onBindViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, T data, boolean isGroup)</font>
 * 中，可通过isGroup判断是Group还是Child：
 * <pre class="prettyprint">
 *     public void onBindViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, GameTypeBean data, boolean isGroup) {
 *         if (isGroup) {
 *             //绑定组的数据...
 *             viewHolder.setText(R.id.tv_game_type, data.getName());
 *         } else {
 *             //绑定子项的数据...
 *             viewHolder.setText(R.id.tv_game_name, data.getGameBeanList().get(childPosition).getName());
 *         }
 *     }
 * </pre>
 * </p>
 * @param <T>
 */
public abstract class ExpandableListCommonAdapter<T> extends BaseExpandableListAdapter {

    private Context mContext;
    private List<T> mDataList;
    private LayoutInflater mInflater;
    private int mGroupLayoutId;
    private int mChildLayoutId;

    public ExpandableListCommonAdapter(Context context, List<T> dataList, int groupLayoutId, int childLayoutId) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mInflater = LayoutInflater.from(context);
        this.mGroupLayoutId = groupLayoutId;
        this.mChildLayoutId = childLayoutId;
    }

    @Override
    public int getGroupCount() {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getChildrenCount(groupPosition, mDataList.get(groupPosition));
    }

    @Override
    public T getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getChild(groupPosition, childPosition, mDataList.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        CommonAdapter.CommonViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mGroupLayoutId, parent, false);
            Log.e("shang", "getGroupView inflate:" + groupPosition+"                          "+convertView);
            viewHolder = new CommonAdapter.CommonViewHolder(mContext, mGroupLayoutId, convertView, groupPosition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonAdapter.CommonViewHolder) convertView.getTag();
        }
        onBindGroupViewHolder(groupPosition, viewHolder, getGroup(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CommonAdapter.CommonViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mChildLayoutId, parent, false);
            Log.e("shang", "getChildView inflate:" + groupPosition+"-"+childPosition+"                          "+convertView);
            viewHolder = new CommonAdapter.CommonViewHolder(mContext, mChildLayoutId, convertView, groupPosition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonAdapter.CommonViewHolder) convertView.getTag();
        }
        onBindChildViewHolder(groupPosition, childPosition, viewHolder, getGroup(groupPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 绑定Group项的ViewHolder
     * @param groupPosition 当前组的position
     * @param viewHolder ViewHolder
     * @param groupData 所在组的数据实体
     */
    public abstract void onBindGroupViewHolder(int groupPosition, CommonAdapter.CommonViewHolder viewHolder, T groupData);

    /**
     * 绑定子项的ViewHolder
     * @param groupPosition 所在组的position
     * @param childPosition 当前子项的position
     * @param viewHolder ViewHolder
     * @param groupData 所在组的数据实体
     */
    public abstract void onBindChildViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, T groupData);

    /**
     * 返回groupPosition组的子项数量
     * @param groupPosition 组的position
     * @param groupData 所在组的数据实体
     * @return groupPosition组的子项数量
     */
    public abstract int getChildrenCount(int groupPosition, T groupData);

    /**
     * 建议正确实现该方法，返回该子项的数据实体
     * @param groupPosition 所在组的position
     * @param childPosition 该子项的position
     * @param groupData 所在组的数据实体
     * @return 该子项的数据实体
     */
    public abstract Object getChild(int groupPosition, int childPosition, T groupData);

}
