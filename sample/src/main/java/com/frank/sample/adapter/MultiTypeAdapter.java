package com.frank.sample.adapter;

import android.content.Context;

import com.frank.commonadapter.MultiTypeCommonAdapter;
import com.frank.commonadapter.R;
import com.frank.sample.bean.TimelineBean;

import java.util.List;

public class MultiTypeAdapter extends MultiTypeCommonAdapter<TimelineBean> {

    public MultiTypeAdapter(Context context, List<TimelineBean> dataList) {
        super(context, dataList);
    }

    @Override
    public int getItemViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int layoutId, int position, TimelineBean data) {
        switch (layoutId) {
            case R.layout.listitem_game:
                return 0;
            case R.layout.listitem_video:
                return 1;
        }
        return 0;
    }

    @Override
    public int getLayoutId(int position, TimelineBean data) {
        switch (data.getEventType()) {
            case TimelineBean.TYPE_GAME:
                return R.layout.listitem_game;
            case TimelineBean.TYPE_VIDEO:
                return R.layout.listitem_video;
        }
        return R.layout.listitem_game;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, TimelineBean data) {
        switch (holder.getLayoutId()) {
            case R.layout.listitem_game:
                holder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getGameBean().getImg_url()));
                holder.setText(R.id.tv_name, data.getGameBean().getName());
                break;
            case R.layout.listitem_video:
                holder.setImageResource(R.id.iv_video, Integer.valueOf(data.getVideoBean().getVideo_logo_url()));
                holder.setText(R.id.tv_name, data.getVideoBean().getName());
                holder.setText(R.id.tv_desc, data.getVideoBean().getDesc());
                break;
        }
    }
}
