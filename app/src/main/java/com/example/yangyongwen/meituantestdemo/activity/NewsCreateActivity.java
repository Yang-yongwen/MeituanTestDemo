package com.example.yangyongwen.meituantestdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.yangyongwen.meituantestdemo.R;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.network.NewsSaveCallback;
import com.example.yangyongwen.meituantestdemo.network.NewsServer;
import com.example.yangyongwen.meituantestdemo.utils.Utils;

public class NewsCreateActivity extends BaseActivity implements NewsSaveCallback {


    private TextView timeTextView;
    private EditText titleEditText;
    private EditText contentEditText;
    private CheckBox publishFreeCBox;
    private EditText newsValueEditText;
    private Button publishButton;
    private View mainContent;
    private ProgressBar progressBar;

    private boolean isPublish;

    private NewsServer newsServer;

    private Context context;

    private News news;

    public void setNewsServer(NewsServer newsServer){
        this.newsServer=newsServer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.activity_create_news);
        mActionBarToolbar=getActionBarToolbar();
        getSupportActionBar().setTitle(R.string.create_news);
        initViews();
        newsServer=NewsServer.getInstance();
        timeTextView.setText(Utils.getDay(System.currentTimeMillis()));

        initPublishLogic();

    }

    public void onSuccess() {
        publishSuccess();
    }

    @Override
    public void onFailure(String reason) {
        publishFailed(reason);
    }

    @Override
    protected void onResume(){
        super.onResume();
        progressBar.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);

        if (isPublish){
            isPublish=false;
            titleEditText.requestFocus();
            clearAll();
        }

    }




    private void changePublishButtonState(){
        if (canPublishNews()){
            publishButton.setEnabled(true);
        }else {
            publishButton.setEnabled(false);
        }
    }


    private void initPublishLogic(){
        publishFreeCBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    newsValueEditText.setEnabled(false);
                    newsValueEditText.setError(null);
                }else {
                    newsValueEditText.setEnabled(true);
                }
                changePublishButtonState();
            }
        });



        titleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus&&titleEditText.getText().length()<=0) {
                    titleEditText.setError(getString(R.string.news_title_input_tip));
                }
            }
        });

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changePublishButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contentEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus&&contentEditText.getText().length()<=0) {
                    contentEditText.setError(getString(R.string.input_news_content));
                }
            }
        });

        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changePublishButtonState();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newsValueEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus&&newsValueEditText.getText().length()<=0&&!publishFreeCBox.isChecked()) {
                    newsValueEditText.setError(getString(R.string.news_value_tip));
                }
            }
        });

        newsValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changePublishButtonState();
                if (s.toString().contains(".")){
                    if ((s.length()-1-s.toString().indexOf("."))>2){
                        s=s.toString().subSequence(0,
                                s.toString().indexOf(".")+3);
                        newsValueEditText.setText(s);
                        newsValueEditText.setSelection(s.length());
                    }

                }
                if (s.toString().trim().equals(".")){
                    s="0"+s;
                    newsValueEditText.setText(s);
                    newsValueEditText.setSelection(2);
                }

                if(s.toString().startsWith("0")&&s.toString().trim().length()>1){
                    if (!s.toString().substring(1,2).equals(".")){
                        newsValueEditText.setText(s.subSequence(0,1));
                        newsValueEditText.setSelection(1);
                        return;
                    }
                }

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news=new News();

                news.setPublish_time(System.currentTimeMillis());
                news.setTitle(titleEditText.getText().toString());
                news.setContent(contentEditText.getText().toString());
                news.setValue(publishFreeCBox.isChecked()?
                        0 : Float.parseFloat(newsValueEditText.getText().toString()));
                NewsCreateActivity.this.news=news;
                mainContent.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                newsServer.publishNews(news,NewsCreateActivity.this);

            }
        });

    }


    public void publishSuccess(){
        Intent intent=new Intent(NewsCreateActivity.this,NewsListActivity.class);
        clearAll();
        startActivity(intent);
        isPublish=true;
        showMessage(R.string.publish_success);
    }


    public News getNews(){
        return this.news;
    }



    public void publishFailed(String reason){
        progressBar.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);
        isPublish=false;
        showMessage(reason);
    }





    private boolean canPublishNews(){
        if(titleEditText.getText().length()>0&&contentEditText.getText().length()>0&&
                (publishFreeCBox.isChecked()||(!publishFreeCBox.isChecked()&&newsValueEditText.getText().length()>0))){
            return true;
        }
        return false;
    }




    @Override
    protected void initViews(){
        timeTextView=(TextView)findViewById(R.id.time_text_view);
        titleEditText=(EditText)findViewById(R.id.title_edit_text);
        contentEditText=(EditText)findViewById(R.id.news_content_edit_text);
        publishFreeCBox=(CheckBox)findViewById(R.id.publish_free_checkbox);
        newsValueEditText=(EditText)findViewById(R.id.value_edit_text);
        publishButton=(Button)findViewById(R.id.publish_button);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mainContent=findViewById(R.id.main_content);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.create_news_menu,menu);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_open_news_list) {
            Intent intent=new Intent(this,NewsListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void clearAll(){
        titleEditText.setText(null);
        titleEditText.setError(null);
        contentEditText.setText(null);
        contentEditText.setError(null);
        newsValueEditText.setText(null);
        newsValueEditText.setError(null);
        publishFreeCBox.setChecked(false);
        newsValueEditText.setEnabled(true);
        newsValueEditText.setText(null);
        newsValueEditText.setError(null);
        publishButton.setEnabled(false);
    }






}
