package com.example.yangyongwen.meituantestdemo.network;

import android.content.Context;
import android.os.Handler;

import com.example.yangyongwen.meituantestdemo.App;
import com.example.yangyongwen.meituantestdemo.R;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.utils.Utils;
import com.example.yangyongwen.meituantestdemo.utils.NewsRepositoryUtils;

import java.util.Random;

/**
 * Created by samsung on 2016/7/4.
 */
public class NewsServer {


    private static NewsServer instance;


    public static NewsServer getInstance(){
        if (instance==null){
            synchronized (NewsServer.class){
                if (instance==null){
                    instance=new NewsServer();
                }
            }
        }
        return instance;
    }


    private NewsServer(){

    }



    private Context context= App.getInstance();  // 实例代码，实际应该以依赖注入

    public void setContext(Context mContext){
        context=mContext;
    }


    private Random random=new Random();

    private Handler handler=new Handler(context.getMainLooper());

    public void publishNews(final News news, final NewsSaveCallback callback){

        if (!NewsValidator.isValidated(news)){
            callback.onFailure(App.getInstance().getString(R.string.validate_news_failed));
            return;
        }

        if (!Utils.isNetworkavailable()){
            callback.onFailure(context.getString(R.string.no_network));
            return;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!Utils.successOnProbability(2,3)){  //  1/3的几率失败，模拟网络异常
                    callback.onFailure(context.getString(R.string.publish_fail_for_network));
                }else {
                    callback.onSuccess();
                    NewsRepositoryUtils.getInstance().saveNews(news);
                }
            }
        },1000);

    }


    public void fetchNews(final int index, final NewsFetchCallback callback){
        if (!Utils.isNetworkavailable()){
            callback.onFailure(context.getString(R.string.no_network));
            return;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Utils.successOnProbability(5,6)){  //  1/6的几率失败，模拟网络异常
                    callback.onFailure(context.getString(R.string.fetch_fail_for_network));
                }else {
                    News news= NewsRepositoryUtils.getInstance().getNews(index);
                    callback.onSuccess(news);
                }
            }
        },300);

    }

    public void fetchNewsList(final NewsFetchCallback callback){

        if (!Utils.isNetworkavailable()){
            callback.onFailure(context.getString(R.string.no_network));
            return;
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Utils.successOnProbability(2,3)){  //  1/3的几率失败，模拟网络异常
                    callback.onFailure(context.getString(R.string.fetch_fail_for_network));
                }else {
                    callback.onSuccess(NewsRepositoryUtils.getInstance().getNewsList());
                }
            }
        },1000);
    }



}
