package com.frank.commonadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public abstract class ELVSectionCommonAdapter<T> extends BaseExpandableListAdapter {

    private static final String TAG = "ELVSectionCommonAdapter";

    private static final int TYPE_SECTION = 0;

    private SparseArray<String> mSectionList;
    protected LayoutInflater mInflater;
    private BaseExpandableListAdapter mExpandableListAdapter;
    private int mSectionLayoutId;
    private int mSectionTitleId;

    public ELVSectionCommonAdapter(Context context, BaseExpandableListAdapter expandableListAdapter,
                                   int sectionLayoutId, int sectionTitleId) {
        this.mInflater = LayoutInflater.from(context);
        this.mExpandableListAdapter = expandableListAdapter;
        this.mSectionLayoutId = sectionLayoutId;
        this.mSectionTitleId = sectionTitleId;
        this.mSectionList = new SparseArray<>();
        initSections();
    }

    @Override
    public int getGroupCount() {
        return mExpandableListAdapter.getGroupCount() + mSectionList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int positionInCustomAdapter = getDataPosition(groupPosition);
        if (mSectionList.get(groupPosition) == null) {
            return mExpandableListAdapter.getChildrenCount(positionInCustomAdapter);
        } else {
            return 0;
        }
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
        if (mSectionList.get(groupPosition) == null) {
            return mExpandableListAdapter.getGroupType(positionInCustomAdapter) + 1;
        } else {
            return TYPE_SECTION;
        }
    }

    @Override
    public int getGroupTypeCount() {
        return mExpandableListAdapter.getGroupTypeCount() + 1;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mExpandableListAdapter.areAllItemsEnabled() && mSectionList.size() == 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
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
        int itemViewType = getGroupType(groupPosition);
        switch (itemViewType) {
            case TYPE_SECTION:
                if (view == null) {
                    view = mInflater.inflate(mSectionLayoutId, parent, false);
                    viewHolder = new CommonAdapter.CommonViewHolder(mSectionLayoutId, view, groupPosition);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (CommonAdapter.CommonViewHolder) view.getTag();
                    viewHolder.mPosition = groupPosition;
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

    private void initSections() {
        int n = mExpandableListAdapter.getGroupCount();
        int nSections = 0;
        mSectionList.clear();
        for(int i = 0; i < n; i++) {
            String sectionName = getSectionTitle((T) mExpandableListAdapter.getGroup(i));
            int j;
            for (j = 0; j < mSectionList.size(); j ++) {
                String section = mSectionList.valueAt(j);
                if (section != null && section.equals(sectionName)) {
                    break;
                }
            }
            if (j >= mSectionList.size()) {
                mSectionList.put(i+nSections, sectionName);
                nSections++;
            }
        }
    }

    public int getDataPosition(int position) {
        int nSections = 0;
        for (int j = 0; j < mSectionList.size(); j ++) {
            int key = mSectionList.keyAt(j);
            if (key < position) {
                nSections++;
            }
        }
        return position - nSections;
    }

    private String getSectionTitleInPosition(int position) {
        return mSectionList.get(position);
    }

    public abstract String getSectionTitle(T data);
}
