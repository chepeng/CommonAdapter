package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.frank.commonadapter.MultiTypeCommonAdapter;
import com.frank.commonadapter.R;
import com.frank.sample.bean.GameBean;
import com.frank.sample.bean.TimelineBean;
import com.frank.sample.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class MultiTypeActivity extends AppCompatActivity {

    ListView listView;
    List<TimelineBean> timelineBeanList = new ArrayList<>();
    MultiTypeCommonAdapter<TimelineBean> timelineBeanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = (ListView) findViewById(R.id.lv_1);
        timelineBeanAdapter = new TimelineAdapter(this, timelineBeanList);
        listView.setAdapter(timelineBeanAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 20; i++) {
            TimelineBean timelineBean = new TimelineBean();
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(R.mipmap.ic_launcher));
            timelineBean.setEventType(TimelineBean.TYPE_GAME);
            timelineBean.setGameBean(gameBean);
            timelineBeanList.add(timelineBean);
        }
        for (int i = 0; i < 20; i++) {
            TimelineBean timelineBean = new TimelineBean();
            VideoBean videoBean = new VideoBean();
            videoBean.setName("video" + i);
            videoBean.setVideo_logo_url(String.valueOf(R.mipmap.ic_launcher));
            videoBean.setDesc("decription" + i);
            timelineBean.setEventType(TimelineBean.TYPE_VIDEO);
            timelineBean.setVideoBean(videoBean);
            timelineBeanList.add(timelineBean);
        }
        timelineBeanAdapter.notifyDataSetChanged();
    }
}
