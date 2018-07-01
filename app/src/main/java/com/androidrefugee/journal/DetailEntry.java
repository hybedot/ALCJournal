package com.androidrefugee.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



public class DetailEntry extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private TextView mMessage;
    private TextView mMoodTextViewOne;
    private TextView mMoodTextViewTwo;
    private TextView mMoodTextViewThree;

    private String date,title,message,moodOne,moodTwo,moodThree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_entry);

        mToolbar = (Toolbar)findViewById(R.id.tool_bar_detail_entry);
        mTitleTextView = (TextView)findViewById(R.id.detail_entry_title);
        mMessage  = (TextView)findViewById(R.id.entry_detail_text_message);
        mMoodTextViewOne = (TextView)findViewById(R.id.entry_detail_mood_one);
        mMoodTextViewTwo = (TextView)findViewById(R.id.entry_detail_mood_two);
        mMoodTextViewThree = (TextView)findViewById(R.id.entry_detail_mood_three);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailEntry.this, MainActivity.class));
            }
        });

        getItemsFromMainActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_edit_entry:
                Bundle bundle = sendItemsToEditActivity();
                Intent i = new Intent(this, AddEntry.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
        }
        return true;
    }

    private void getItemsFromMainActivity(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (!bundle.isEmpty()){
           date = bundle.getString("date");
           title = bundle.getString("title");
           message = bundle.getString("message");
           moodOne = bundle.getString("moodOne");
           moodTwo = bundle.getString("moodTwo");
           moodThree = bundle.getString("moodThree");

            mToolbar.setTitle(date);
            mTitleTextView.setText(title);
            mMessage.setText(message);
            mMoodTextViewOne.setText(moodOne);
            mMoodTextViewTwo.setText(moodTwo);
            mMoodTextViewThree.setText(moodThree);
        }
    }

    private  Bundle sendItemsToEditActivity(){
        Bundle bundle = new Bundle();

        bundle.putString("title", title);
        bundle.putString("title",title);
        bundle.putString("message", message);
        bundle.putString("moodOne", moodOne);
        bundle.putString("moodTwo", moodTwo);
        bundle.putString("moodThree", moodThree);

        return bundle;
    }
}
