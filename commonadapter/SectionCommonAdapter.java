package com.frank.commonadapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SectionCommonAdapter<T> extends BaseAdapter {

    private static final String TAG = "SectionCommonAdapter";

    private static final int TYPE_SECTION = 0;

    private SparseArray<String> mSectionList;
    protected LayoutInflater mInflater;
    private BaseAdapter mListAdapter;
    private int mSectionLayoutId;
    private int mSectionTitleId;

    public SectionCommonAdapter(Context context, BaseAdapter listAdapter,
                                int sectionLayoutId, int sectionTitleId) {
        this.mInflater = LayoutInflater.from(context);
        this.mListAdapter = listAdapter;
        this.mSectionLayoutId = sectionLayoutId;
        this.mSectionTitleId = sectionTitleId;
        this.mSectionList = new SparseArray<>();
        initSections();
    }

    @Override
    public int getCount() {
        return mListAdapter.getCount() + mSectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return mListAdapter.getItem(getDataPosition(position));
    }

    @Override
    public long getItemId(int position) {
        return mListAdapter.getItemId(getDataPosition(position));
    }

    @Override
    public int getViewTypeCount() {
        return mListAdapter.getViewTypeCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int positionInCustomAdapter = getDataPosition(position);
        if (mSectionList.get(position) == null) {
            return mListAdapter.getItemViewType(positionInCustomAdapter) + 1;
        } else {
            return TYPE_SECTION;
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mListAdapter.areAllItemsEnabled() && mSectionList.size() == 0;
    }

    @Override
    public boolean isEnabled(int position) {
        int positionInCustomAdapter = getDataPosition(position);
        return mSectionList.get(position) == null && mListAdapter.isEnabled(positionInCustomAdapter);
    }

    @Override
    public void notifyDataSetChanged() {
        mListAdapter.notifyDataSetChanged();
        initSections();
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        CommonAdapter.CommonViewHolder viewHolder = null;
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_SECTION:
                if (view == null) {
                    view = mInflater.inflate(mSectionLayoutId, parent, false);
                    viewHolder = new CommonAdapter.CommonViewHolder(mSectionLayoutId, view, position);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (CommonAdapter.CommonViewHolder) view.getTag();
                    viewHolder.mPosition = position;
                }
                break;
            default:
                view = mListAdapter.getView(getDataPosition(position), convertView, parent);
                break;
        }
        if (viewHolder != null) {
            String sectionTitle = getSectionTitleInPosition(position);
            onSetSectionTitle(view, sectionTitle, viewHolder, position);
        }
        return view;
    }

    public void onSetSectionTitle(View sectionView, String sectionTitle, CommonAdapter.CommonViewHolder viewHolder, int position) {
        viewHolder.setText(mSectionTitleId, sectionTitle);
    }

    private void initSections() {
        int n = mListAdapter.getCount();
        int nSections = 0;
        mSectionList.clear();
        for (int i = 0; i < n; i++) {
            String sectionName = getSectionTitle((T) mListAdapter.getItem(i));
            int j;
            for (j = 0; j < mSectionList.size(); j++) {
                String section = mSectionList.valueAt(j);
                if (section != null && section.equals(sectionName)) {
                    break;
                }
            }
            if (j >= mSectionList.size()) {
                mSectionList.put(i + nSections, sectionName);
                nSections++;
            }
        }
    }

    public int getDataPosition(int position) {
        int nSections = 0;
        for (int j = 0; j < mSectionList.size(); j++) {
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
