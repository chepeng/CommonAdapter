package com.frank.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

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
            //Log.e("shang", "getGroupView inflate:" + groupPosition+"                          "+convertView);
            viewHolder = new CommonAdapter.CommonViewHolder(mContext, mGroupLayoutId, convertView, groupPosition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonAdapter.CommonViewHolder) convertView.getTag();
        }
        onBindViewHolder(groupPosition, -1, viewHolder, getGroup(groupPosition), true);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        CommonAdapter.CommonViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(mChildLayoutId, parent, false);
            //Log.e("shang", "getChildView inflate:" + groupPosition+"-"+childPosition+"                          "+convertView);
            viewHolder = new CommonAdapter.CommonViewHolder(mContext, mChildLayoutId, convertView, groupPosition);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommonAdapter.CommonViewHolder) convertView.getTag();
        }
        onBindViewHolder(groupPosition, childPosition, viewHolder, getGroup(groupPosition), false);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public abstract void onBindViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, T data, boolean isGroup);

    public abstract int getChildrenCount(int groupPosition, T groupData);

    public abstract Object getChild(int groupPosition, int childPosition, T groupData);

}
