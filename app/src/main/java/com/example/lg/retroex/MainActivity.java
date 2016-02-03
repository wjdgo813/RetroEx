package com.example.lg.retroex;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    ListView listView;
    ImageAdapter mAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView keyword;
    boolean isLastItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        keyword=(TextView)findViewById(R.id.edit_keyword);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swype);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorScheme(R.color.color1, R.color.color2, R.color.color3, R.color.color4);

        mAdapter = new ImageAdapter();
        listView.setAdapter(mAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(isLastItem && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    getMoreItem();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(totalItemCount >0 && (firstVisibleItem + visibleItemCount >= totalItemCount-1)){
                    isLastItem = true;
                }else{
                    isLastItem = false;
                }
            }
        });

        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchResult(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    public void getMoreItem(){
        String keyword = mAdapter.getKeyword();
        int startIndex = mAdapter.getStartIndex();
        if(!TextUtils.isEmpty(keyword) && startIndex != -1){

            MyService myService =NetworkManager.getInstance().getRetrofit(MyService.class);
            Call<SearchResult> call = myService.contributors(Config.API_KEY,keyword,10,startIndex,Config.OUTPUT);

            call.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Response<SearchResult> response, Retrofit retrofit) {
                    for(ImageItem items : response.body().channel.item){
                            mAdapter.add(items);
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }


    public void searchResult(String input) {
        if (!input.equals("")) {
            MyService myService = NetworkManager.getInstance().getRetrofit(MyService.class);
            Call<SearchResult> call = myService.contributors(Config.API_KEY, input, 10, 1, Config.OUTPUT);
            mAdapter.setKeyword(input);

            call.enqueue(new Callback<SearchResult>() {
                @Override
                public void onResponse(Response<SearchResult> response, Retrofit retrofit) {
                    mAdapter.setTotalCount(response.body().channel.totalCount);
                    mAdapter.clear();
                    for (ImageItem item : response.body().channel.item) {
                        mAdapter.add(item);
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("imageTest", "network failure");
                    Log.e("imageTest", t.getMessage());
                    Log.e("imageTest", t.toString());
                    t.printStackTrace();
                }
            });
        }else{
            mAdapter.clear();
            mAdapter.setKeyword(input);
        }
    }

    @Override
    public void onRefresh() {
        String keyword = mAdapter.getKeyword();
        if(keyword.equals("")){
            Toast.makeText(MainActivity.this, "검색어를 입력해 주십시오.", Toast.LENGTH_SHORT).show();
        }
        else{
            searchResult(keyword);
        }
        refreshLayout.setRefreshing(false);
    }
}
