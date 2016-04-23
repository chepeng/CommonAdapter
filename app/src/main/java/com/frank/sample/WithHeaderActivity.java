package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.frank.commonadapter.R;
import com.frank.commonadapter.WithHeaderAdapter;
import com.frank.sample.bean.GameBean;

import java.util.ArrayList;
import java.util.List;

public class WithHeaderActivity extends AppCompatActivity {

    ListView listView;
    List<GameBean> gameBeanList = new ArrayList<>();
    WithHeaderAdapter<GameBean> gameBeanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_header);
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
        gameBeanAdapter = new WithHeaderAdapter<GameBean>(this, R.layout.listitem_header,
                R.layout.listitem_game, gameBeanList) {

            @Override
            public void onBindHeader(CommonViewHolder viewHolder) {
                viewHolder.setImageResource(R.id.iv_header, android.R.drawable.ic_menu_camera);
                viewHolder.setText(R.id.tv_header, "I am HeaderView!");
            }

            @Override
            public void onBindViewHolder(CommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        listView.setAdapter(gameBeanAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                gameBeanList.remove(position - 1);
                gameBeanAdapter.notifyDataSetChanged();
            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 40; i++) {
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(R.mipmap.ic_launcher));
            gameBeanList.add(gameBean);
        }
        gameBeanAdapter.notifyDataSetChanged();
    }
}
