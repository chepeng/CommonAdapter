package com.frank.commonadapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

public abstract class NonRecyclableCommonAdapter<T> extends CommonAdapter<T> {

    private Context mContext;
    private LinearLayout mContainer;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int mDividerHeight;

    public NonRecyclableCommonAdapter(Context context, LinearLayout container, List<T> dataList, int layoutId) {
        super(context, dataList, layoutId);
        this.mContext = context;
        this.mContainer = container;
        this.mContainer.setOrientation(LinearLayout.VERTICAL);
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        mDividerHeight = mDivider.getIntrinsicHeight();
        a.recycle();
        if (dataList != null && !dataList.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (mContainer != null) {
            mContainer.removeAllViews();
            for (int i = 0; i < mDataList.size(); i++) {
                View itemView = mInflater.inflate(mLayoutId, mContainer, false);
                CommonViewHolder viewHolder = new CommonViewHolder(mLayoutId, itemView, i);
                onBindViewHolder(viewHolder, mDataList.get(i));
                if (isEnabled(i)) {
                    if (mOnItemClickListener != null) {
                        final int finalI = i;
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnItemClickListener.onItemClick(mContainer, v, finalI);
                            }
                        });
                    }
                    if (mOnItemLongClickListener != null) {
                        final int finalI = i;
                        itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                mOnItemLongClickListener.onItemLongClick(mContainer, v, finalI);
                                return false;
                            }
                        });
                    }
                }
                mContainer.addView(itemView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                if (mDivider != null && mDividerHeight > 0) {
                    View dividerView = new View(mContext);
                    dividerView.setBackgroundDrawable(mDivider);
                    mContainer.addView(dividerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDividerHeight));
                }
            }
        }
    }

    public Drawable getDivider() {
        return mDivider;
    }

    public void setDivider(Drawable divider) {
        this.mDivider = divider;
    }

    public int getDividerHeight() {
        return mDividerHeight;
    }

    public void setDividerHeight(int dividerHeight) {
        this.mDividerHeight = dividerHeight;
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

    public interface OnItemClickListener {
        void onItemClick(LinearLayout parent, View view, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(LinearLayout parent, View view, int position);
    }

}
