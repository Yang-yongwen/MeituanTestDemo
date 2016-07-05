package com.example.yangyongwen.meituantestdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.yangyongwen.meituantestdemo.R;

/**
 * Created by samsung on 2016/6/29.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar mActionBarToolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    protected void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void showMessage(int resId){
        Toast.makeText(this,resId,Toast.LENGTH_SHORT).show();
    }


    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar == null) {
            mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
            if (mActionBarToolbar != null) {
                setSupportActionBar(mActionBarToolbar);
            }
        }
        return mActionBarToolbar;
    }


    protected abstract void initViews();


}
