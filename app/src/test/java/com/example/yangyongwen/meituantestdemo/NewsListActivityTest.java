package com.example.yangyongwen.meituantestdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yangyongwen.meituantestdemo.activity.NewsListActivity;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.network.NewsServer;
import com.example.yangyongwen.meituantestdemo.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by samsung on 2016/7/4.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class NewsListActivityTest {

    private NewsListActivity newsListActivity;
    Context context;
    private Menu menu;

    private Button retryButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private List<News> newsList;



    @Before
    public void setUp(){
        newsListActivity= Robolectric.setupActivity(NewsListActivity.class);
        menu=shadowOf(newsListActivity).getOptionsMenu();
        context=newsListActivity;
        initViews();
        Utils.setContext(context);
        newsList=Utils.getMockNewsList();
    }
    @After
    public void validate() {
        validateMockitoUsage();
    }


    @Test
    public void testMenuForTrunCreateNews(){
        MenuItem item=menu.findItem(R.id.action_create_news);
        assertEquals(item.getTitle(),context.getString(R.string.create_news));

        menu.performIdentifierAction(R.id.action_create_news,0);
        assertTrue(shadowOf(newsListActivity).isFinishing());
    }


    @Test
    public void testNewsListNoNetwork(){
        setNetworkEnable(false);
        reCreateActivity();
        assertEquals(retryButton.getVisibility(), View.VISIBLE);
        assertEquals(recyclerView.getVisibility(), View.INVISIBLE);
        assertEquals(progressBar.getVisibility(), View.INVISIBLE);
    }

    @Test
    public void testGetNewsWhenNetwork(){
        NewsServer newsServer=Mockito.mock(NewsServer.class);
        newsListActivity.setNewsServer(newsServer);
        setNetworkEnable(true);
        newsListActivity.startFetchNews();
        assertEquals(retryButton.getVisibility(), View.INVISIBLE);
        assertEquals(recyclerView.getVisibility(), View.INVISIBLE);
        assertEquals(progressBar.getVisibility(), View.VISIBLE);

        Mockito.verify(newsServer).fetchNewsList(newsListActivity);

    }

    @Test
    public void testGetNewsFailed(){
        newsListActivity.onFailure(getString(R.string.fetch_fail_for_network));
        assertEquals(retryButton.getVisibility(), View.VISIBLE);
        assertEquals(recyclerView.getVisibility(), View.INVISIBLE);
        assertEquals(progressBar.getVisibility(), View.INVISIBLE);

        assertEquals(ShadowToast.getTextOfLatestToast(),getString(R.string.fetch_fail_for_network));

    }

    @Test
    public void testGetNewsSuccess(){
        newsListActivity.onSuccess(newsList);
        assertEquals(retryButton.getVisibility(), View.INVISIBLE);
        assertEquals(recyclerView.getVisibility(), View.VISIBLE);
        assertEquals(progressBar.getVisibility(), View.INVISIBLE);
    }


    @Test
    public void testSetNews(){
        newsListActivity.setNewsList(newsList);
        recyclerView.measure(0,0);
        recyclerView.layout(0,0,100,100);
        assertEquals(newsList.size(),recyclerView.getChildCount());
        News news=null;
        View view=null;
        for (int i=0;i<newsList.size();++i){
            news=newsList.get(i);
            view=recyclerView.getChildAt(i);
            String title=((TextView)view.findViewById(R.id.news_title)).getText().toString();
            assertEquals(title,news.getTitle());
            String content=((TextView)view.findViewById(R.id.news_content)).getText().toString();
            assertEquals(content,news.getContent());
            String time=((TextView)view.findViewById(R.id.news_publish_time)).getText().toString();
            assertEquals(time,Utils.getDay(news.getPublish_time()));
        }
    }


    @Test
    public void testNewsDialog(){
        newsListActivity.setNewsList(newsList);
        recyclerView.measure(0,0);
        recyclerView.layout(0,0,100,100);
        View view=recyclerView.getChildAt(0);
        view.performLongClick();

        AlertDialog alertDialog= ShadowAlertDialog.getLatestAlertDialog();

        assertNotNull(alertDialog);
    }

    private void initViews(){
        progressBar=(ProgressBar)newsListActivity.findViewById(R.id.progress_bar);
        retryButton=(Button)newsListActivity.findViewById(R.id.retry_button);
        recyclerView=(RecyclerView)newsListActivity.findViewById(R.id.news_list_recyclerview);
    }


    private void setNetworkEnable(boolean enable){
        ConnectivityManager cm=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        shadowOf(networkInfo).setConnectionStatus(enable);
        shadowOf(cm).setActiveNetworkInfo(networkInfo);
    }

    private void reCreateActivity(){
        shadowOf(newsListActivity).recreate();
        initViews();
    }

    private String getString(int resId){
        return context.getString(resId);
    }


}
