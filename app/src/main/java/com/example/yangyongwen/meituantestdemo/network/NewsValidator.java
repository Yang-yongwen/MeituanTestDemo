package com.example.yangyongwen.meituantestdemo.network;

import com.example.yangyongwen.meituantestdemo.dao.News;

/**
 * Created by samsung on 2016/7/4.
 */
public class NewsValidator {

    public static boolean isValidated(News news){

        if (news==null){
            return false;
        }
        if (isEmpty(news.getContent())||isEmpty(news.getTitle())||news.getPublish_time()<=0){          //  标题和内容不能为空
            return false;
        }

        if (news.getTitle().length()>20){           //标题不超过二十个字
            return false;
        }

        if (news.getValue()<0){                    //金额不能为负
            return false;
        }

        String value=news.getValue().toString();

        if (value.length()-value.indexOf(".")>3){   //金额只有两位小数
            return false;
        }


        return true;
    }


    private static boolean isEmpty(String string){
        if (string==null||string.length()==0){
            return true;
        }
        return false;
    }


}
