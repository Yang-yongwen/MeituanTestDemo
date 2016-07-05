package com.example.yangyongwen.meituantestdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.yangyongwen.meituantestdemo.R;
import com.example.yangyongwen.meituantestdemo.adapter.NewsAdapter;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.network.NewsFetchCallback;
import com.example.yangyongwen.meituantestdemo.network.NewsServer;
import com.example.yangyongwen.meituantestdemo.utils.Utils;

import java.util.List;

public class NewsListActivity extends BaseActivity implements NewsFetchCallback{

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private ProgressBar progressBar;
    private Button retryButton;
    private NewsServer newsServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        mActionBarToolbar=getActionBarToolbar();
        getSupportActionBar().setTitle(R.string.news_list);
        initViews();
        initLogic();
        adapter=new NewsAdapter(this,null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsServer=NewsServer.getInstance();
        if (!Utils.isNetworkavailable()){
            noNetwork();
        }else {
            startFetchNews();
        }

    }

    public void setNewsServer(NewsServer newsServer){
        this.newsServer=newsServer;
    }



    private void initLogic(){
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFetchNews();
            }
        });
    }


    public void startFetchNews(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);
        newsServer.fetchNewsList(NewsListActivity.this);
    }

    @Override
    public void onSuccess(News news){
        setSuccessViewState();
        adapter.addNews(news);
    }

    public void setNewsList(List<News> newsList){
        adapter.setnewsList(newsList);
    }

    @Override
    public void onSuccess(List<News> newsList){
        setSuccessViewState();
        setNewsList(newsList);
    }

    @Override
    public void onFailure(String  reason){
        showMessage(reason);
        setFailureViewState();
    }

    public void noNetwork(){
        showMessage(R.string.no_network);
        setFailureViewState();
    }


    @Override
    protected void initViews(){
        recyclerView=(RecyclerView)findViewById(R.id.news_list_recyclerview);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        retryButton=(Button)findViewById(R.id.retry_button);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.news_list_menu,menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create_news) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSuccessViewState(){
        retryButton.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void setFailureViewState(){
        retryButton.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

}
