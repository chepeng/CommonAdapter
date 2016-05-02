package com.frank.commonadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.BaseExpandableListAdapter;

public abstract class RVSectionCommonAdapter<T> extends RecyclerView.Adapter<RVCommonAdapter.RVCommonViewHolder> {

    private static final String TAG = "RVSectionCommonAdapter";

    private static final int TYPE_SECTION = 0;

    private SparseArray<String> mSectionList;
    protected LayoutInflater mInflater;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private int mSectionLayoutId;
    private int mSectionTitleId;

    public RVSectionCommonAdapter(Context context, RecyclerView.Adapter recyclerViewAdapter,
                                   int sectionLayoutId, int sectionTitleId) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerViewAdapter = recyclerViewAdapter;
        this.mSectionLayoutId = sectionLayoutId;
        this.mSectionTitleId = sectionTitleId;
        this.mSectionList = new SparseArray<>();
    }

}
