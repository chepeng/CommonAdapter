package com.frank.commonadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public abstract class RecyclerViewCommonAdapter<T> extends RecyclerView.Adapter<RecyclerViewCommonAdapter.RecyclerViewCommonViewHolder> {
    private Context mContext;
    private List<T> mDataList;
    private LayoutInflater mInflater;
    private int mLayoutId;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public RecyclerViewCommonAdapter(Context context, int layoutId, List<T> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
    }

    /**
     * 为每个数据项提供视图引用
     */
    public static class RecyclerViewCommonViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        private int mLayoutId;
        private View mItemView;
        private SparseArray<View> mViews;

        public RecyclerViewCommonViewHolder(Context context, int layoutId, View itemView) {
            super(itemView);
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mItemView = itemView;
            this.mViews = new SparseArray<>();
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mItemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public int getLayoutId() {
            return mLayoutId;
        }

        public Context getContext() {
            return mContext;
        }

        public RecyclerViewCommonViewHolder setText(int viewId, String text) {
            TextView tv = getView(viewId);
            tv.setText(text);
            return this;
        }

        public RecyclerViewCommonViewHolder setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
            return this;
        }
        //...
        //新增其它通用方法...
    }

    /**
     * 当RecyclerView需要为给定viewType项的Item创建新的ViewHolder时调用
     * 可以手动创建一个新View，也可以inflate XML布局文件。
     * 这个新的ViewHolder将被用作{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)}
     * 由于该ViewHolder将被重用以便显示数据集中不同的项，所以最好缓存子View的引用以避免不必要的{@link View#findViewById(int)}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public RecyclerViewCommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(mLayoutId, parent, false);
        RecyclerViewCommonViewHolder vh = new RecyclerViewCommonViewHolder(mContext, mLayoutId, v);
        setListener(parent, v, vh);
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        v.setBackgroundResource(typedValue.resourceId);
        return vh;
    }

    /**
     * 当显示指定position位置的数据时由RecyclerView调用. 该方法应该更新{@link RecyclerView.ViewHolder#itemView}的内容。
     * <p/>
     * 注意：不像{@link android.widget.ListView}, RecyclerView只会调用该方法一次，除非item自己更新自己
     * 或者新position不确定。因此，只有当你需要position位置的数据项时可以用这个<code>position</code>，千万不要
     * 保持该position的拷贝。如果之后需要知道数据项的position（如onClickListener）时，使用{@link RecyclerView.ViewHolder#getAdapterPosition()}
     * 才能得到已更新的adapter position。
     * <p/>
     * 如果adapter可以处理部分绑定，可以重写{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int, List)}方法
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerViewCommonViewHolder holder, int position) {
        onBindViewHolder(holder, mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private void setListener(final ViewGroup parent, View view, final RecyclerViewCommonViewHolder holder) {
        if (mOnItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(parent, v, holder.getAdapterPosition(), mDataList.get(holder.getAdapterPosition()));
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(parent, v, holder.getAdapterPosition(), mDataList.get(holder.getAdapterPosition()));
                    return false;
                }
            });
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public abstract void onBindViewHolder(RecyclerViewCommonViewHolder viewHolder, T data);

    /**
     * Interface definition for a callback to be invoked when an item in this
     * RecyclerView has been clicked.
     */
    public interface OnItemClickListener<T> {
        void onItemClick(ViewGroup parent, View view, int position, T data);
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held.
     */
    public interface OnItemLongClickListener<T> {
        boolean onItemLongClick(ViewGroup parent, View view, int position, T data);
    }

}
