package com.frank.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        listView = (ListView) findViewById(R.id.lv_1);
        gameBeanAdapter = new CommonAdapter<GameBean>(this, gameBeanList, R.layout.listitem_game) {
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
                Toast.makeText(MainActivity.this,""+position,Toast.LENGTH_SHORT).show();
            }
        });
        getData();
    }

    private void getData() {
        for (int i = 0; i < 40; i++) {
            GameBean gameBean = new GameBean();
            gameBean.setName("game" + i);
            gameBean.setImg_url(String.valueOf(android.R.drawable.ic_btn_speak_now));
            gameBeanList.add(gameBean);
        }
        gameBeanAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Intent intent;
            switch (menuItem.getItemId()) {
                case R.id.menu_multi_type:
                    intent = new Intent(MainActivity.this, MultiTypeActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case R.id.menu_with_header:
                    intent = new Intent(MainActivity.this, WithHeaderActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case R.id.menu_expandable:
                    intent = new Intent(MainActivity.this, ExpandableListActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case R.id.menu_section:
                    intent = new Intent(MainActivity.this, SectionActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case R.id.menu_section_expandable:
                    intent = new Intent(MainActivity.this, SectionExpandableListActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                case R.id.menu_simple_recyclerview:
                    intent = new Intent(MainActivity.this, SimpleRecyclerViewActivity.class);
                    MainActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
            return true;
        }
    };
}
