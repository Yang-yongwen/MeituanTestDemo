package com.example.yangyongwen.meituantestdemo.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.yangyongwen.meituantestdemo.App;
import com.example.yangyongwen.meituantestdemo.dao.DaoMaster;
import com.example.yangyongwen.meituantestdemo.dao.DaoSession;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.dao.NewsDao;

import java.util.List;

/**
 * Created by samsung on 2016/6/30.
 */
public class NewsRepositoryUtils {

    private static final String NEWS_NUM_PRE="news_num_pre";
    private static final String NEWS_PRE_PREFIX="news_pre_prefix";

    private DaoSession daoSession;
    private NewsDao newsDao;


    private static Context context= App.getInstance();  // 实例代码，实际应该以依赖注入

    public static void setContext(Context mContext){
        context=mContext;
    }

    private static NewsRepositoryUtils instance;


    private List<News> newsList;

    public static NewsRepositoryUtils getInstance(){
        if (instance==null){
            synchronized (NewsRepositoryUtils.class){
                if (instance==null){

                    instance=new NewsRepositoryUtils();
                }
            }
        }
        return instance;
    }

    private NewsRepositoryUtils(){

        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,"DemoDb",null);
        SQLiteDatabase db=helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        daoSession=daoMaster.newSession();
        newsDao=daoSession.getNewsDao();
        newsList=newsDao.queryBuilder()
                .orderDesc(NewsDao.Properties.Publish_time)
                .list();

    }


    public void saveNews(News news){
        newsList.add(0,news);
        newsDao.insert(news);
    }




    public void deleteNews(News news){
        newsDao.delete(news);
    }


    public List<News> getNewsList(){
        return newsList;
    }


    public News getNews(int index){

        if (index<0||index>=newsList.size()){
            return null;
        }

        return newsList.get(index);
    }



}
