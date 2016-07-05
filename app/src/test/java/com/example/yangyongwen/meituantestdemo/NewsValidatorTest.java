package com.example.yangyongwen.meituantestdemo;

import android.content.Context;

import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.network.NewsValidator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import static org.junit.Assert.*;

/**
 * Created by samsung on 2016/7/5.
 */



@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class NewsValidatorTest {

    private Context context;
    private NewsValidator validator;
    private News news;

    @Before
    public void setUp(){
        context= RuntimeEnvironment.application;
        validator=new NewsValidator();
        news=new News();
        news.setContent(getString(R.string.content_for_test));
        news.setPublish_time(System.currentTimeMillis());
        news.setValue(Float.parseFloat(getString(R.string.value_for_test)));
        news.setTitle(getString(R.string.title_for_test));
    }




    @Test
    public void titleTest(){
        assertTrue(validator.isValidated(news));
        news.setTitle(getString(R.string.content_for_test));
        assertTrue(!validator.isValidated(news));

        news.setTitle("");
        assertTrue(!validator.isValidated(news));

    }

    @Test
    public void contentTest(){
        assertTrue(validator.isValidated(news));
        news.setContent("");
        assertTrue(!validator.isValidated(news));
    }

    @Test
    public void tiemTest(){
        assertTrue(validator.isValidated(news));
        news.setPublish_time(-1l);
        assertTrue(!validator.isValidated(news));
    }

    @Test
    public void valueTest(){
        assertTrue(validator.isValidated(news));
        news.setValue(-1f);
        assertTrue(!validator.isValidated(news));
        news.setValue(Float.parseFloat(getString(R.string.value_more_than_two_digit)));
        assertTrue(!validator.isValidated(news));
    }



    private String getString(int resId){
        return context.getString(resId);
    }


}
