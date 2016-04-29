package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.frank.commonadapter.DividerItemDecoration;
import com.frank.commonadapter.R;
import com.frank.commonadapter.RecyclerViewCommonAdapter;
import com.frank.sample.bean.GameBean;

import java.util.ArrayList;
import java.util.List;

public class SimpleRecyclerViewActivity extends AppCompatActivity {

    RecyclerView rv_1;
    List<GameBean> gameBeanList = new ArrayList<>();
    RecyclerViewCommonAdapter<GameBean> mRecyclerViewCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_recycler_view);
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
        rv_1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerViewCommonAdapter = new RecyclerViewCommonAdapter<GameBean>(this, R.layout.listitem_game, gameBeanList) {
            @Override
            public void onBindViewHolder(RecyclerViewCommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        mRecyclerViewCommonAdapter.setOnItemClickListener(new RecyclerViewCommonAdapter.OnItemClickListener<GameBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position, GameBean data) {
                Log.e("shang", view+","+position+","+data.getName());
            }
        });
        rv_1.setAdapter(mRecyclerViewCommonAdapter);
        getData();
    }

    private void getData() {
        for (int i = 0; i < 40; i++) {
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(R.mipmap.ic_launcher));
            gameBeanList.add(gameBean);
        }
        mRecyclerViewCommonAdapter.notifyDataSetChanged();
    }
}
