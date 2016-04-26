package com.frank.commonadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public abstract class SectionCommonAdapter<T> extends BaseAdapter {

    private static final int TYPE_SECTION = 0;

    private LinkedHashMap<String, Integer> mSectionList;
    private Context mContext;
    protected LayoutInflater mInflater;
    private BaseAdapter mListAdapter;
    private int mSectionLayoutId;
    private int mSectionTitleId;

    public SectionCommonAdapter(Context context, BaseAdapter listAdapter,
                                int sectionLayoutId, int sectionTitleId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mListAdapter = listAdapter;
        this.mSectionLayoutId = sectionLayoutId;
        this.mSectionTitleId = sectionTitleId;
        mSectionList = new LinkedHashMap<>();
        initSections();
    }

    @Override
    public int getCount() {
        return mListAdapter.getCount() + mSectionList.size();
    }

    @Override
    public Object getItem(int position) {
        return  mListAdapter.getItem(getDataPosition(position));
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
        return mSectionList.values().contains(position) ?
                TYPE_SECTION :
                mListAdapter.getItemViewType(positionInCustomAdapter) + 1;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return mListAdapter.areAllItemsEnabled() && mSectionList.size() == 0;
    }

    @Override
    public boolean isEnabled(int position) {
        return !mSectionList.values().contains(position) && mListAdapter.isEnabled(getDataPosition(position));
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
        switch (getItemViewType(position)) {
            case TYPE_SECTION:
                if(view == null) {
                    view = mInflater.inflate(mSectionLayoutId, parent, false);
                    viewHolder = new CommonAdapter.CommonViewHolder(mContext, mSectionLayoutId, view, position);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (CommonAdapter.CommonViewHolder) view.getTag();
                }
                break;
            default:
                view = mListAdapter.getView(getDataPosition(position), convertView, parent);
                break;
        }
        if(viewHolder != null) {
            String sectionName = getSectionTitleInPosition(position);
            viewHolder.setText(mSectionTitleId, sectionName);
        }
        return view;
    }

    private void initSections() {
        int n = mListAdapter.getCount();
        int nSections = 0;
        mSectionList.clear();
        for(int i = 0; i < n; i++) {
            String sectionName = getSectionTitle((T)mListAdapter.getItem(i));
            if(!mSectionList.containsKey(sectionName)) {
                mSectionList.put(sectionName, i + nSections);
                nSections ++;
            }
        }
    }

    private int getDataPosition(int position) {
        int nSections = 0;
        Set<Map.Entry<String, Integer>> entrySet = mSectionList.entrySet();
        for(Map.Entry<String, Integer> entry : entrySet) {
            if(entry.getValue() < position) {
                nSections++;
            }
        }
        return position - nSections;
    }

    private String getSectionTitleInPosition(int position) {
        String title = null;
        Set<Map.Entry<String, Integer>> entrySet = mSectionList.entrySet();
        for(Map.Entry<String, Integer> entry : entrySet) {
            if(entry.getValue() == position) {
                title = entry.getKey();
                break;
            }
        }
        return title;
    }

    public abstract String getSectionTitle(T data);

}
