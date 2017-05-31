package com.example.searchview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.example.searchview.R;
import com.example.searchview.widget.search.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * SearchView的使用示例
 */

public class MyActivity extends Activity {
    ListView listview;
    MaterialSearchView search_view;
    List<String> list = new ArrayList<String>();
    ArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        initData();
        initEvent();

    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        search_view = (MaterialSearchView) findViewById(R.id.search_view);
    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            int num = (int) (Math.random() * 30);
            list.add(i + "number：" + num);
        }
        list.add("快乐");
        list.add("我");
        list.add("你");
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listview.setAdapter(adapter);

        search_view.setVoiceSearch(false); //or true    ，是否支持声音的

        search_view.setSubmitOnClick(true);  //设置为true后，单击ListView的条目，search_view隐藏。实现数据的搜索
        search_view.setEllipsize(true);   //搜索框的ListView中的Item条目是否是单显示
        String[] array = list.toArray(new String[list.size()]);
        search_view.setSuggestions(array);
    }

    private void initEvent() {
        //数据的监听（在自定义类中已经做了些处理）
        search_view.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            //数据提交时
            //1.点击ListView的Item条目会回调这个方法
            //2.点击系统键盘的搜索/回车后回调这个方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MyActivity.this, "你要搜索的是：" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            //文本内容发生改变时
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    /**
     * 右上角的输入框的显示和隐藏选择
     *
     * @param view
     */
    public void search(View view) {
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();//隐藏搜索框
        } else {
            search_view.showSearch(true);//显示搜索框
        }
    }

    /**
     * 监听回退事件
     */
    @Override
    public void onBackPressed() {
        if (search_view.isSearchOpen()) {
            search_view.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


}
