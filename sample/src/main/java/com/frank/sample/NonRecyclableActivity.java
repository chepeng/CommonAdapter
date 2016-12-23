package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.frank.commonadapter.NonRecyclableCommonAdapter;
import com.frank.commonadapter.R;
import com.frank.sample.bean.GameBean;

import java.util.ArrayList;
import java.util.List;

public class NonRecyclableActivity extends AppCompatActivity {

    private LinearLayout ll_list;
    private List<GameBean> gameBeanList = new ArrayList<>();
    private NonRecyclableCommonAdapter<GameBean> gameBeanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_recyclable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ll_list = (LinearLayout) findViewById(R.id.ll_list);
        gameBeanAdapter = new NonRecyclableCommonAdapter<GameBean>(this, ll_list, gameBeanList, R.layout.listitem_game) {
            @Override
            public void onBindViewHolder(CommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        gameBeanAdapter.setOnItemClickListener(new NonRecyclableCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(LinearLayout parent, View view, int position) {
                Toast.makeText(NonRecyclableActivity.this, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 3; i++) {
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(android.R.drawable.presence_audio_online));
            gameBeanList.add(gameBean);
        }
        gameBeanAdapter.notifyDataSetChanged();
    }

}
