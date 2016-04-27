package com.frank.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class SectionExpandableListAdapter<T> extends BaseExpandableListAdapter {

    private static final int TYPE_SECTION = 0;

    private LinkedHashMap<String, Integer> mSectionList;
    private Context mContext;
    protected LayoutInflater mInflater;
    private BaseExpandableListAdapter mExpandableListAdapter;
    private int mSectionLayoutId;
    private int mSectionTitleId;

    public SectionExpandableListAdapter(Context context, BaseExpandableListAdapter expandableListAdapter,
                                        int sectionLayoutId, int sectionTitleId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mExpandableListAdapter = expandableListAdapter;
        this.mSectionLayoutId = sectionLayoutId;
        this.mSectionTitleId = sectionTitleId;
        mSectionList = new LinkedHashMap<>();
        initSections();
    }

    @Override
    public int getGroupCount() {
        return mExpandableListAdapter.getGroupCount() + mSectionList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int positionInCustomAdapter = getDataPosition(groupPosition);
        return mSectionList.values().contains(groupPosition) ?
                0 :
                mExpandableListAdapter.getChildrenCount(positionInCustomAdapter);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mExpandableListAdapter.getGroup(getDataPosition(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mExpandableListAdapter.getChild(getDataPosition(groupPosition), childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mExpandableListAdapter.getGroupId(getDataPosition(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mExpandableListAdapter.getChildId(getDataPosition(groupPosition), childPosition);
    }

    @Override
    public int getGroupType(int groupPosition) {
        int positionInCustomAdapter = getDataPosition(groupPosition);
        return mSectionList.values().contains(groupPosition) ?
                TYPE_SECTION :
                mExpandableListAdapter.getGroupType(positionInCustomAdapter) + 1;
    }

    @Override
    public int getGroupTypeCount() {
        return mExpandableListAdapter.getGroupTypeCount() + 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mExpandableListAdapter.areAllItemsEnabled() && mSectionList.size() == 0;
    }

    @Override
    public void notifyDataSetChanged() {
        mExpandableListAdapter.notifyDataSetChanged();
        initSections();
        super.notifyDataSetChanged();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = convertView;
        CommonAdapter.CommonViewHolder viewHolder = null;
        switch (getGroupType(groupPosition)) {
            case TYPE_SECTION:
                if (view == null) {
                    view = mInflater.inflate(mSectionLayoutId, parent, false);
                    viewHolder = new CommonAdapter.CommonViewHolder(mContext, mSectionLayoutId, view, groupPosition);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (CommonAdapter.CommonViewHolder) view.getTag();
                }
                break;
            default:
                view = mExpandableListAdapter.getGroupView(getDataPosition(groupPosition), isExpanded, convertView, parent);
                break;
        }
        if (viewHolder != null) {
            String sectionName = getSectionTitleInPosition(groupPosition);
            viewHolder.setText(mSectionTitleId, sectionName);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return mExpandableListAdapter.getChildView(getDataPosition(groupPosition), childPosition, isLastChild, convertView, parent);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initSections() {
        int n = mExpandableListAdapter.getGroupCount();
        int nSections = 0;
        mSectionList.clear();
        for (int i = 0; i < n; i++) {
            String sectionName = getSectionTitle((T) mExpandableListAdapter.getGroup(i));
            if (!mSectionList.containsKey(sectionName)) {
                mSectionList.put(sectionName, i + nSections);
                nSections++;
            }
        }
    }

    private int getDataPosition(int position) {
        int nSections = 0;
        Set<Map.Entry<String, Integer>> entrySet = mSectionList.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() < position) {
                nSections++;
            }
        }
        return position - nSections;
    }

    private String getSectionTitleInPosition(int position) {
        String title = null;
        Set<Map.Entry<String, Integer>> entrySet = mSectionList.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (entry.getValue() == position) {
                title = entry.getKey();
                break;
            }
        }
        return title;
    }

    public abstract String getSectionTitle(T data);
}
