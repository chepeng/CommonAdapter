package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.frank.commonadapter.CommonAdapter;
import com.frank.commonadapter.R;
import com.frank.sample.bean.GameBean;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<GameBean> gameBeanList = new ArrayList<>();
    CommonAdapter<GameBean> gameBeanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv_1);
        gameBeanAdapter = new CommonAdapter<GameBean>(this, gameBeanList, R.layout.listitem_game) {
            @Override
            public void bindData(CommonViewHolder holder, GameBean gameBean) {
                holder.setImageResource(R.id.iv_logo, Integer.valueOf(gameBean.getImg_url()));
                holder.setText(R.id.tv_name, gameBean.getName());
            }
        };
        listView.setAdapter(gameBeanAdapter);
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
