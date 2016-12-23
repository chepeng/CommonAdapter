package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.frank.commonadapter.MultiTypeCommonAdapter;
import com.frank.commonadapter.SectionCommonAdapter;
import com.frank.sample.adapter.MultiTypeAdapter;
import com.frank.sample.bean.GameBean;
import com.frank.sample.bean.TimelineBean;
import com.frank.sample.bean.VideoBean;

import java.util.ArrayList;
import java.util.List;

public class SectionActivity extends AppCompatActivity {

    ListView listView;
    List<TimelineBean> timelineBeanList = new ArrayList<>();
    MultiTypeCommonAdapter<TimelineBean> timelineBeanAdapter;
    SectionCommonAdapter<TimelineBean> mSectionCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
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
        timelineBeanAdapter = new MultiTypeAdapter(this, timelineBeanList);
        mSectionCommonAdapter = new SectionCommonAdapter<TimelineBean>(this, timelineBeanAdapter, R.layout.listitem_section, R.id.tv_section) {
            @Override
            public String getSectionTitle(TimelineBean data) {
                if (TimelineBean.TYPE_GAME.equals(data.getEventType())) {
                    return "All Game";
                } else {
                    return "All Video";
                }
            }
        };
        listView.setAdapter(mSectionCommonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int dataPosition = mSectionCommonAdapter.getDataPosition(position);
                Toast.makeText(SectionActivity.this, "" + dataPosition, Toast.LENGTH_SHORT).show();
            }
        });
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
        mSectionCommonAdapter.notifyDataSetChanged();
    }
}
