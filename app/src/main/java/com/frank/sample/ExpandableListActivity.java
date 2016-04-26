package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.frank.commonadapter.CommonAdapter;
import com.frank.commonadapter.ExpandableListCommonAdapter;
import com.frank.commonadapter.R;
import com.frank.sample.bean.GameBean;
import com.frank.sample.bean.GameTypeBean;
import com.frank.sample.widget.ExpandedGridView;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListActivity extends AppCompatActivity {

    public final static String[] imageUrls = new String[]{
            "http://img.my.csdn.net/uploads/201508/05/1438760758_3497.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760758_6667.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760757_3588.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760756_3304.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760755_6715.jpeg",
            "http://img.my.csdn.net/uploads/201508/05/1438760726_5120.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760726_8364.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760725_4031.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760724_9463.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760724_2371.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760707_4653.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760706_6864.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760706_9279.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760704_2341.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760704_5707.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760685_5091.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760685_4444.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760684_8827.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760683_3691.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760683_7315.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760663_7318.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760662_3454.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760662_5113.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760661_3305.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760661_7416.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760589_2946.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760589_1100.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760588_8297.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760587_2575.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760587_8906.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760550_2875.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760550_9517.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760549_7093.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760549_1352.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760548_2780.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760531_1776.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760531_1380.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760530_4944.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760530_5750.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760529_3289.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760500_7871.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760500_6063.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760499_6304.jpeg",
            "http://img.my.csdn.net/uploads/201508/05/1438760499_5081.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760498_7007.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760478_3128.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760478_6766.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760477_1358.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760477_3540.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760476_1240.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760446_7993.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760446_3641.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760445_3283.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760444_8623.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760444_6822.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760422_2224.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760421_2824.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760420_2660.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760420_7188.jpg",
            "http://img.my.csdn.net/uploads/201508/05/1438760419_4123.jpg",
    };

    private ExpandableListView elv_main;
    private List<GameTypeBean> gameTypeBeanList = new ArrayList<>();
    private ExpandableListCommonAdapter gameTypeBeanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);
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
        gameTypeBeanAdapter = new ExpandableListCommonAdapter<GameTypeBean>(this, gameTypeBeanList, R.layout.listitem_game_group, R.layout.listitem_game_child) {

            @Override
            public void onBindGroupViewHolder(int groupPosition, CommonAdapter.CommonViewHolder viewHolder, GameTypeBean groupData) {
                viewHolder.setText(R.id.tv_game_type, groupData.getName());
            }

            @Override
            public void onBindChildViewHolder(int groupPosition, int childPosition, CommonAdapter.CommonViewHolder viewHolder, GameTypeBean groupData) {
                List<GameBean> gameBeanList = groupData.getGameBeanList();
                ExpandedGridView gridView = viewHolder.getView(R.id.gv_game);
                CommonAdapter adapter = (CommonAdapter) gridView.getAdapter();
                if (adapter == null) {
                    gridView.setAdapter(new CommonAdapter<GameBean>(ExpandableListActivity.this, gameBeanList, R.layout.grid_item_game) {
                        @Override
                        public void onBindViewHolder(CommonViewHolder viewHolder, GameBean data) {
                            ImageView iv = viewHolder.getView(R.id.iv_game);
                            Glide.with(ExpandableListActivity.this).load(data.getImg_url()).centerCrop().into(iv);
                        }
                    });
                } else {
                    if (gameBeanList != null) {
                        adapter.setDataList(gameBeanList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public int getChildrenCount(int groupPosition, GameTypeBean groupData) {
                return 1;
            }

            @Override
            public Object getChild(int groupPosition, int childPosition, GameTypeBean groupData) {
                return groupData.getGameBeanList();
            }
        };
        elv_main.setAdapter(gameTypeBeanAdapter);
        getData();
    }

    private void getData() {
        GameTypeBean gameTypeBean1 = new GameTypeBean();
        List<GameBean> gameBeanList1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            GameBean gameBean = new GameBean();
            gameBean.setName("1:" + i);
            gameBean.setImg_url(imageUrls[i % imageUrls.length]);
            gameBeanList1.add(gameBean);
        }
        gameTypeBean1.setName("game Type0");
        gameTypeBean1.setGameBeanList(gameBeanList1);
        gameTypeBeanList.add(gameTypeBean1);

        for (int j = 1; j < 10; j++) {
            GameTypeBean gameTypeBean = new GameTypeBean();
            List<GameBean> gameBeanList = new ArrayList<>();
            for (int i = 0; i < 15; i++) {
                GameBean gameBean = new GameBean();
                gameBean.setName("game" + i);
                gameBean.setImg_url(imageUrls[i % imageUrls.length]);
                gameBeanList.add(gameBean);
            }
            gameTypeBean.setName("game Type" + j);
            gameTypeBean.setGameBeanList(gameBeanList);
            gameTypeBeanList.add(gameTypeBean);
        }
        gameTypeBeanAdapter.notifyDataSetChanged();
    }

}
