package com.frank.commonadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class RVSectionCommonAdapter<T> extends RecyclerView.Adapter<RVCommonAdapter.RVCommonViewHolder> {

    private static final int TYPE_SECTION = 0;

    private SparseArray<String> mSectionList;
    protected LayoutInflater mInflater;
    private RVCommonAdapter<T> mRecyclerViewAdapter;
    private int mSectionLayoutId;
    private int mSectionTitleId;

    final RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            initSections();
        }
    };

    public RVSectionCommonAdapter(Context context, RVCommonAdapter<T> recyclerViewAdapter,
                                  int sectionLayoutId, int sectionTitleId) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerViewAdapter = recyclerViewAdapter;
        this.mSectionLayoutId = sectionLayoutId;
        this.mSectionTitleId = sectionTitleId;
        this.mSectionList = new SparseArray<>();
        initSections();
        registerAdapterDataObserver(observer);
    }

    @Override
    public int getItemCount() {
        return mRecyclerViewAdapter.getItemCount() + mSectionList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int positionInCustomAdapter = getDataPosition(position);
        if (mSectionList.get(position) == null) {
            return mRecyclerViewAdapter.getItemViewType(positionInCustomAdapter) + 1;
        } else {
            return TYPE_SECTION;
        }
    }

    @Override
    public RVCommonAdapter.RVCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RVCommonAdapter.RVCommonViewHolder vh;
        switch (viewType) {
            case TYPE_SECTION:
                View v = mInflater.inflate(mSectionLayoutId, parent, false);
                vh = new RVCommonAdapter.RVCommonViewHolder(mSectionLayoutId, v);
                break;
            default:
                vh = mRecyclerViewAdapter.onCreateViewHolder(parent, viewType);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RVCommonAdapter.RVCommonViewHolder holder, int position) {
        String sectionTitle = mSectionList.get(position);
        if (sectionTitle != null) {
            onSetSectionTitle(holder.itemView, sectionTitle, holder, position);
        } else {
            mRecyclerViewAdapter.onBindViewHolder(holder, getDataPosition(position));
        }
    }

    public void onSetSectionTitle(View sectionView, String sectionTitle, RVCommonAdapter.RVCommonViewHolder viewHolder, int position) {
        viewHolder.setText(mSectionTitleId, sectionTitle);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(observer);
    }

    private void initSections() {
        int n = mRecyclerViewAdapter.getItemCount();
        int nSections = 0;
        mSectionList.clear();
        for (int i = 0; i < n; i++) {
            String sectionName = getSectionTitle(mRecyclerViewAdapter.mDataList.get(i));
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

    public abstract String getSectionTitle(T data);

}
