package com.frank.commonadapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RVHeaderFooterAdapter<T> extends RecyclerView.Adapter<RVCommonAdapter.RVCommonViewHolder> {

    private static final String TAG = "RVHeaderFooterAdapter";

    private SparseArray<View> mHeaderViewInfos = new SparseArray<>();//key:layoutId/viewType, value:layoutView
    private SparseArray<View> mFooterViewInfos = new SparseArray<>();
    private List<Integer> mHeaderViewOrder = new ArrayList<>();
    private List<Integer> mFooterViewOrder = new ArrayList<>();
    private RVCommonAdapter<T> mAdapter;

    public RVHeaderFooterAdapter(RVCommonAdapter<T> adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mHeaderViewInfos.size() + mFooterViewInfos.size();
    }

    @Override
    public int getItemViewType(int position) {
        int index;
        if ((index = getIndexInHeader(position)) != -1) {
            return mHeaderViewOrder.get(index);
        } else if ((index = getIndexInFooter(position)) != -1) {
            return mFooterViewOrder.get(index);
        } else if ((index = getIndexInInnerAdapter(position)) != -1) {
            return mAdapter.getItemViewType(index);
        }
        return -1;
    }

    @Override
    public RVCommonAdapter.RVCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RVCommonAdapter.RVCommonViewHolder vh;
        if (mHeaderViewInfos.get(viewType) != null) {
            vh = new RVCommonAdapter.RVCommonViewHolder(viewType, mHeaderViewInfos.get(viewType));
        } else if (mFooterViewInfos.get(viewType) != null) {
            vh = new RVCommonAdapter.RVCommonViewHolder(viewType, mFooterViewInfos.get(viewType));
        } else {
            vh = mAdapter.onCreateViewHolder(parent, viewType);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RVCommonAdapter.RVCommonViewHolder holder, int position) {
        int index;
        if ((index = getIndexInInnerAdapter(position)) != -1) {
            mAdapter.onBindViewHolder(holder, index);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getIndexInInnerAdapter(position) != -1) {
                        return 1;
                    } else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            };
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }
    }

    @Override
    public void onViewAttachedToWindow(RVCommonAdapter.RVCommonViewHolder viewHolder) {
        mAdapter.onViewAttachedToWindow(viewHolder);
        int position = viewHolder.getLayoutPosition();
        if (getIndexInHeader(position) != -1 || getIndexInFooter(position) != -1) {
            ViewGroup.LayoutParams lp = viewHolder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    public void addHeaderView(int layoutId, View v) {
        mHeaderViewInfos.put(layoutId, v);
        mHeaderViewOrder.add(layoutId);
    }

    public int getHeaderViewsCount() {
        return mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(int layoutId) {
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < mHeaderViewInfos.size(); i++) {
            if (mHeaderViewInfos.keyAt(i) == layoutId) {
                index1 = i;
            }
            if (mHeaderViewOrder.get(i).compareTo(layoutId) == 0) {
                index2 = i;
            }
        }
        if (index1 != -1 && index2 != -1) {
            mHeaderViewInfos.removeAt(index1);
            mHeaderViewOrder.remove(index2);
            notifyItemRemoved(index2);
            return true;
        }
        return false;
    }

    public void addFooterView(int layoutId, View v) {
        mFooterViewInfos.put(layoutId, v);
        mFooterViewOrder.add(layoutId);
    }

    public int getFooterViewsCount() {
        return mFooterViewInfos.size();
    }

    public boolean removeFooterView(int layoutId) {
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < mFooterViewInfos.size(); i++) {
            if (mFooterViewInfos.keyAt(i) == layoutId) {
                index1 = i;
            }
            if (mFooterViewOrder.get(i).compareTo(layoutId) == 0) {
                index2 = i;
            }
        }
        if (index1 != -1 && index2 != -1) {
            mFooterViewInfos.removeAt(index1);
            mFooterViewOrder.remove(index2);
            notifyItemRemoved(index2 + mHeaderViewInfos.size() + mAdapter.getItemCount());
            return true;
        }
        return false;
    }

    public int getIndexInHeader(int position) {
        if (position >= 0 && position < mHeaderViewInfos.size()) {
            return position;
        } else {
            return -1;
        }
    }

    public int getIndexInInnerAdapter(int position) {
        if (getIndexInHeader(position) == -1 && getIndexInFooter(position) == -1) {
            return position - mHeaderViewInfos.size();
        } else {
            return -1;
        }
    }

    public int getIndexInFooter(int position) {
        if (mFooterViewInfos.size() > 0 && position - mHeaderViewInfos.size() - mAdapter.getItemCount() >= 0) {
            return position - mHeaderViewInfos.size() - mAdapter.getItemCount();
        } else {
            return -1;
        }
    }

}
