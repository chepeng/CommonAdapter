package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.frank.commonadapter.CommonAdapter;
import com.frank.commonadapter.ExpandableListCommonAdapter;
import com.frank.commonadapter.R;
import com.frank.commonadapter.SectionExpandableListAdapter;
import com.frank.sample.bean.GameBean;
import com.frank.sample.bean.GameTypeBean;

import java.util.ArrayList;
import java.util.List;

public class SectionExpandableListActivity extends AppCompatActivity {

    private ExpandableListView elv_main;
    private List<GameTypeBean> gameTypeBeanList = new ArrayList<>();
    private ExpandableListCommonAdapter mExpandableListCommonAdapter;
    private SectionExpandableListAdapter mSectionExpandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_expandable_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        elv_main = (ExpandableListView) findViewById(R.id.elv_main);
        mExpandableListCommonAdapter = new ExpandableListCommonAdapter<GameTypeBean>(this, gameTypeBeanList, R.layout.listitem_game_group, R.layout.listitem_game) {
            @Override
            public void onBindGroupViewHolder(int groupPosition, CommonAdapter.CommonViewHolder viewHolder, GameTypeBean groupData) {
                viewHolder.setText(R.id.tv_game_type, groupData.getName());
            }

            @Override
            public void onBindChildViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, GameTypeBean groupData) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(groupData.getGameBeanList().get(childPosition).getImg_url()));
                viewHolder.setText(R.id.tv_name, groupData.getGameBeanList().get(childPosition).getName());
            }

            @Override
            public int getChildrenCount(int groupPosition, GameTypeBean groupData) {
                return groupData.getGameBeanList().size();
            }

            @Override
            public Object getChild(int groupPosition, int childPosition, GameTypeBean groupData) {
                return groupData.getGameBeanList().get(childPosition);
            }
        };
        mSectionExpandableListAdapter = new SectionExpandableListAdapter<GameTypeBean>(this, mExpandableListCommonAdapter, R.layout.listitem_section, R.id.tv_section) {
            @Override
            public String getSectionTitle(GameTypeBean data) {
                int num = Integer.valueOf(data.getName().substring(data.getName().length() - 1, data.getName().length()));
                if (num < 3) {
                    return "Section 1";
                } else {
                    return "Section 2";
                }
            }
        };
        elv_main.setAdapter(mSectionExpandableListAdapter);
        getData();
    }

    private void getData() {
        for (int j = 0; j < 10; j++) {
            GameTypeBean gameTypeBean = new GameTypeBean();
            List<GameBean> gameBeanList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                GameBean gameBean = new GameBean();
                gameBean.setName("game" + i);
                gameBean.setImg_url(String.valueOf(android.R.drawable.presence_audio_online));
                gameBeanList.add(gameBean);
            }
            gameTypeBean.setName("game Type" + j);
            gameTypeBean.setGameBeanList(gameBeanList);
            gameTypeBeanList.add(gameTypeBean);
        }
        mSectionExpandableListAdapter.notifyDataSetChanged();
    }

}
