package com.example.yangyongwen.meituantestdemo.network;

import com.example.yangyongwen.meituantestdemo.dao.News;

import java.util.List;

/**
 * Created by samsung on 2016/7/5.
 */
public interface NewsFetchCallback {
    void onSuccess(News news);
    void onSuccess(List<News> newsList);
    void onFailure(String reason);
}
