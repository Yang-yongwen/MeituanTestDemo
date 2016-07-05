package com.example.yangyongwen.meituantestdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.example.yangyongwen.meituantestdemo.App;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by samsung on 2016/7/5.
 */
public class Utils {

    private static Random random=new Random();

    private static Context context=App.getInstance();    // 实例代码，实际应该以依赖注入

    public static void setContext(Context mContext){
        context=mContext;
    }

    public static boolean isNetworkavailable(){
        ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=manager.getActiveNetworkInfo();
        return activeNetworkInfo!=null&&activeNetworkInfo.isConnected();
    }

    public static String getDay(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(time);
        return sdf.format(cal.getTime());
    }

    public static boolean successOnProbability(int nume,int deno){

        if (nume>=deno){
            return true;
        }

        int i=random.nextInt(deno);
        if (i<nume){
            return true;
        }
        return false;
    }


    public static List<News> getMockNewsList(){
        Gson gson=new Gson();
        String json=getStringFromRes("/json/newslist.json");
        News[] newses=gson.fromJson(json,News[].class);
        return Arrays.asList(newses);
    }

    private static String getStringFromRes(String fileName){
        String result=null;
        try {
            String path=context.getClass().getResource(fileName).toURI().getPath();
            result=readFile(path,"UTF-8").toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }


}
