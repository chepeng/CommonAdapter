package com.frank.sample;

import android.content.Context;

import com.frank.commonadapter.MultiItemCommonAdapter;
import com.frank.commonadapter.MultiItemTypeSupport;
import com.frank.commonadapter.R;
import com.frank.sample.bean.TimelineBean;

import java.util.List;

public class TimelineAdapter extends MultiItemCommonAdapter<TimelineBean> {
    public TimelineAdapter(Context context, List<TimelineBean> datas) {
        super(context, datas, new MultiItemTypeSupport<TimelineBean>() {
            @Override
            public int getLayoutId(int position, TimelineBean timelineBean) {
                switch (timelineBean.getEventType()) {
                    case TimelineBean.TYPE_GAME:
                        return R.layout.listitem_game;
                    case TimelineBean.TYPE_VIDEO:
                        return R.layout.listitem_video;
                    default:
                        return R.layout.listitem_game;
                }
            }

            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int postion, TimelineBean timelineBean) {
                switch (timelineBean.getEventType()) {
                    case TimelineBean.TYPE_GAME:
                        return 0;
                    case TimelineBean.TYPE_VIDEO:
                        return 1;
                }
                return 0;
            }
        });
    }

    @Override
    public void bindData(CommonViewHolder holder, TimelineBean timelineBean) {

        switch (holder.getLayoutId()) {
            case R.layout.listitem_game:
                holder.setImageResource(R.id.iv_logo, Integer.valueOf(timelineBean.getGameBean().getImg_url()));
                holder.setText(R.id.tv_name, timelineBean.getGameBean().getName());
                break;
            case R.layout.listitem_video:
                holder.setImageResource(R.id.iv_video, Integer.valueOf(timelineBean.getVideoBean().getVideo_logo_url()));
                holder.setText(R.id.tv_name, timelineBean.getVideoBean().getName());
                holder.setText(R.id.tv_desc, timelineBean.getVideoBean().getDesc());
                break;
        }
    }
}
