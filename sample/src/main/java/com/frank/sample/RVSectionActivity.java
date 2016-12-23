package com.frank.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frank.commonadapter.DefaultDividerItemDecoration;
import com.frank.commonadapter.RVCommonAdapter;
import com.frank.commonadapter.RVSectionCommonAdapter;
import com.frank.sample.bean.GameBean;

import java.util.ArrayList;
import java.util.List;

public class RVSectionActivity extends AppCompatActivity {

    RecyclerView rv_1;
    List<GameBean> gameBeanList = new ArrayList<>();
    RVCommonAdapter<GameBean> mRVCommonAdapter;
    RVSectionCommonAdapter<GameBean> mSectionCommonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvsection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
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
        mRVCommonAdapter = new RVCommonAdapter<GameBean>(this, gameBeanList, R.layout.listitem_game) {
            @Override
            public void onBindViewHolder(RVCommonViewHolder viewHolder, GameBean data) {
                viewHolder.setImageResource(R.id.iv_logo, Integer.valueOf(data.getImg_url()));
                viewHolder.setText(R.id.tv_name, data.getName());
            }
        };
        mRVCommonAdapter.setOnItemClickListener(new RVCommonAdapter.OnItemClickListener<GameBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                int dataPosition = mSectionCommonAdapter.getDataPosition(position);
                Toast.makeText(RVSectionActivity.this, "" + dataPosition + "," + gameBeanList.get(dataPosition).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        mSectionCommonAdapter = new RVSectionCommonAdapter<GameBean>(this, mRVCommonAdapter, R.layout.listitem_section, R.id.tv_section) {
            @Override
            public String getSectionTitle(GameBean data) {
                if (data.getName().startsWith("game1")) {
                    return "section 1";
                } else {
                    return "section";
                }
            }
        };
        rv_1.setAdapter(mSectionCommonAdapter);
        getData();
    }

    private void getData() {
        for (int i = 10; i < 40; i++) {
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(android.R.drawable.presence_audio_online));
            gameBeanList.add(gameBean);
        }
        mSectionCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_op, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        int count = 0;

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menu_add:
                    GameBean gameBean = new GameBean();
                    gameBean.setName("new" + count);
                    gameBean.setImg_url(String.valueOf(R.mipmap.ic_launcher));
                    gameBeanList.add(1, gameBean);
                    mSectionCommonAdapter.notifyDataSetChanged();
                    count++;
                    break;
                case R.id.menu_delete:
                    gameBeanList.remove(2);
                    mSectionCommonAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

}
