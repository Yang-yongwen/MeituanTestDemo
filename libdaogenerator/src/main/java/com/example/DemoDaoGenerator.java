package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DemoDaoGenerator {

    public static void main(String[] args) throws Exception{
        Schema schema=new Schema(1,"com.example.yangyongwen.meituantestdemo.dao");

        addNews(schema);

        new DaoGenerator().generateAll(schema,"app/src/main/java");

    }

    private static void addNews(Schema schema){
        Entity news=schema.addEntity("News");

        news.addLongProperty("publish_time").primaryKey();
        news.addStringProperty("title");
        news.addStringProperty("content");
        news.addFloatProperty("value");


    }


}
