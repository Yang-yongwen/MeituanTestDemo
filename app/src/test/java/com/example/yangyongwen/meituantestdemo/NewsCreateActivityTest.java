package com.example.yangyongwen.meituantestdemo;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import static org.junit.Assert.*;
import com.example.yangyongwen.meituantestdemo.activity.NewsCreateActivity;
import com.example.yangyongwen.meituantestdemo.activity.NewsListActivity;
import com.example.yangyongwen.meituantestdemo.dao.News;
import com.example.yangyongwen.meituantestdemo.network.NewsServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowToast;

import static org.robolectric.Shadows.shadowOf;
import static org.mockito.Mockito.*;

/**
 * Created by samsung on 2016/7/1.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class NewsCreateActivityTest {

    private NewsCreateActivity newsCreateActivity;
    private TextView timeTV;
    private EditText titleEditTV;
    private EditText contentEditTV;
    private CheckBox freeCheckBox;
    private EditText valuedEditTV;
    private Button publishButton;
    private Menu menu;
    private Context context;
    private View mainContent;
    private ProgressBar progressBar;

    @Before
    public void setUp(){
        newsCreateActivity= Robolectric.setupActivity(NewsCreateActivity.class);
        timeTV=(TextView)newsCreateActivity.findViewById(R.id.time_text_view);
        titleEditTV=(EditText)newsCreateActivity.findViewById(R.id.title_edit_text);
        contentEditTV=(EditText)newsCreateActivity.findViewById(R.id.news_content_edit_text);
        valuedEditTV=(EditText)newsCreateActivity.findViewById(R.id.value_edit_text);
        freeCheckBox=(CheckBox)newsCreateActivity.findViewById(R.id.publish_free_checkbox);
        publishButton=(Button)newsCreateActivity.findViewById(R.id.publish_button);
        mainContent=newsCreateActivity.findViewById(R.id.main_content);
        progressBar=(ProgressBar)newsCreateActivity.findViewById(R.id.progress_bar);
        context=newsCreateActivity;
        menu=shadowOf(newsCreateActivity).getOptionsMenu();


    }
    @After
    public void validate() {
        validateMockitoUsage();
    }

    @Test
    public void testWriteNews(){
        assertTrue(!publishButton.isEnabled());

        titleEditTV.setText(R.string.title_for_test);
        assertEquals(titleEditTV.getText(),context.getString(R.string.title_for_test));

        contentEditTV.setText(R.string.content_for_test);
        assertEquals(contentEditTV.getText(),context.getString(R.string.content_for_test));

        valuedEditTV.setText(R.string.value_for_test);
        assertEquals(valuedEditTV.getText(),context.getString(R.string.value_for_test));

        assertTrue(publishButton.isEnabled());

        valuedEditTV.setText("");
        assertTrue(!publishButton.isEnabled());
        freeCheckBox.setChecked(true);
        assertTrue(publishButton.isEnabled());

    }

    @Test
    public void testValue(){
        freeCheckBox.setChecked(true);
        assertTrue(!valuedEditTV.isEnabled());

        freeCheckBox.setChecked(false);
        assertTrue(valuedEditTV.isEnabled());

        valuedEditTV.setText("20.3364");
        assertEquals("value should only has double-digit",valuedEditTV.getText(),"20.33");

    }

    @Test
    public void testMenuForTrunNewsList(){
        MenuItem item=menu.findItem(R.id.action_open_news_list);
        assertEquals(item.getTitle(),context.getString(R.string.news_list));

        menu.performIdentifierAction(R.id.action_open_news_list,0);

        Intent expectedIntent=new Intent(context, NewsListActivity.class);
        Intent actualIntent= ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent,actualIntent);
    }


    @Test
    public void testNewsPush(){
        NewsServer newsServer= Mockito.mock(NewsServer.class);
        newsCreateActivity.setNewsServer(newsServer);
        timeTV.setText(R.string.publish_time_for_test);

        titleEditTV.setText(R.string.title_for_test);
        assertTrue(!publishButton.isEnabled());

        contentEditTV.setText(R.string.content_for_test);
        assertTrue(!publishButton.isEnabled());

        valuedEditTV.setText(R.string.value_for_test);
        assertTrue(publishButton.isEnabled());

        publishButton.performClick();

        assertEquals(mainContent.getVisibility(),View.INVISIBLE);
        assertEquals(progressBar.getVisibility(),View.VISIBLE);


        Mockito.verify(newsServer).publishNews(newsCreateActivity.getNews(),newsCreateActivity);

    }

    @Test
    public void testPushSuccessed(){
        newsCreateActivity.publishSuccess();
        assertEquals(titleEditTV.getText(),"");
        assertEquals(contentEditTV.getText(),"");
        assertEquals(valuedEditTV.getText(),"");
        assertTrue(!freeCheckBox.isChecked());

        assertEquals(ShadowToast.getTextOfLatestToast(),context.getString(R.string.publish_success));

    }

    @Test
    public void testPushFailed(){
        newsCreateActivity.publishFailed(context.getString(R.string.publish_fail_for_network));
        assertEquals(ShadowToast.getTextOfLatestToast(),context.getString(R.string.publish_fail_for_network));
    }


    private void clearAllState(){
        timeTV.setText("");
        titleEditTV.setText("");
        contentEditTV.setText("");
        freeCheckBox.setChecked(false);
        valuedEditTV.setText("");
    }



}
