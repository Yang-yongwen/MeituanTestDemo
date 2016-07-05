package com.example.yangyongwen.meituantestdemo.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yangyongwen.meituantestdemo.R;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.utils.NewsRepositoryUtils;
import com.example.yangyongwen.meituantestdemo.utils.Utils;

import java.util.List;

/**
 * Created by samsung on 2016/6/30.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> newsList;

    private Context context;



    public class NewsViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTV;
        private TextView publishTimewTV;
        private TextView contentTV;

        public NewsViewHolder(View v){
            super(v);
            titleTV=(TextView)v.findViewById(R.id.news_title);
            publishTimewTV=(TextView)v.findViewById(R.id.news_publish_time);
            contentTV=(TextView)v.findViewById(R.id.news_content);
        }

    }


    public NewsAdapter(Context context,List<News> newsList){
        this.context=context;
        this.newsList=newsList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v= LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false);
        RecyclerView.ViewHolder viewHolder=new NewsViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position){
        News news=newsList.get(position);
        NewsViewHolder vh=(NewsViewHolder) holder;
        vh.contentTV.setText(news.getContent());
        vh.titleTV.setText(news.getTitle());
        vh.publishTimewTV.setText(Utils.getDay(news.getPublish_time()));

        vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle(R.string.try_delete_news)
                        .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewsRepositoryUtils.getInstance().deleteNews(newsList.get(position));
                                newsList.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount(){
        return newsList==null? 0 :newsList.size();
    }


    public void addNews(News news){
        newsList.add(0,news);
        notifyDataSetChanged();
    }

    public void setnewsList(List<News> newsList){
        this.newsList=newsList;
        notifyDataSetChanged();
    }


}
