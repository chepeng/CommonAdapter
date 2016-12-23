package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frank.commonadapter.DefaultDividerItemDecoration;
import com.frank.commonadapter.RVCommonAdapter;
import com.frank.commonadapter.RVMultiTypeCommonAdapter;
import com.frank.sample.bean.GameBean;
import com.frank.sample.bean.TimelineBean;
import com.frank.sample.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class RVMultiTypeActivity extends AppCompatActivity {

    RecyclerView rv_1;
    List<TimelineBean> timelineBeanList = new ArrayList<>();
    RVMultiTypeCommonAdapter mRVMultiTypeCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvmulti_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rv_1 = (RecyclerView) findViewById(R.id.rv_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_1.setLayoutManager(linearLayoutManager);
        rv_1.addItemDecoration(new DefaultDividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRVMultiTypeCommonAdapter = new RVMultiTypeCommonAdapter<TimelineBean>(this, timelineBeanList) {
            @Override
            public int getLayoutId(int position, TimelineBean data) {
                switch (data.getEventType()) {
                    case TimelineBean.TYPE_GAME:
                        return R.layout.listitem_game;
                    case TimelineBean.TYPE_VIDEO:
                        return R.layout.listitem_video;
                }
                return -1;
            }

            @Override
            public void onBindViewHolder(RVCommonViewHolder viewHolder, TimelineBean data) {
                switch (viewHolder.getLayoutId()) {
                    case R.layout.listitem_game:
                        viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getGameBean().getImg_url()));
                        viewHolder.setText(R.id.tv_name, data.getGameBean().getName());
                        break;
                    case R.layout.listitem_video:
                        viewHolder.setImageResource(R.id.iv_video, Integer.valueOf(data.getVideoBean().getVideo_logo_url()));
                        viewHolder.setText(R.id.tv_name, data.getVideoBean().getName());
                        viewHolder.setText(R.id.tv_desc, data.getVideoBean().getDesc());
                        break;
                }
            }
        };
        mRVMultiTypeCommonAdapter.setOnItemClickListener(new RVCommonAdapter.OnItemClickListener<TimelineBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Toast.makeText(RVMultiTypeActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        rv_1.setAdapter(mRVMultiTypeCommonAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            TimelineBean timelineBean = new TimelineBean();
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(android.R.drawable.presence_audio_online));
            timelineBean.setEventType(TimelineBean.TYPE_GAME);
            timelineBean.setGameBean(gameBean);
            timelineBeanList.add(timelineBean);
        }
        for (int i = 0; i < 20; i++) {
            TimelineBean timelineBean = new TimelineBean();
            VideoBean videoBean = new VideoBean();
            videoBean.setName("video" + i);
            videoBean.setVideo_logo_url(String.valueOf(android.R.drawable.presence_video_online));
            videoBean.setDesc("decription" + i);
            timelineBean.setEventType(TimelineBean.TYPE_VIDEO);
            timelineBean.setVideoBean(videoBean);
            timelineBeanList.add(timelineBean);
        }
        mRVMultiTypeCommonAdapter.notifyDataSetChanged();
    }

}
