package com.androidrefugee.journal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.androidrefugee.journal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class AddEntry extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mTitleTextView;
    private TextView mMoodTextView;
    private EditText mMessage;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEntryDatabasereference;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        mToolbar = (Toolbar)findViewById(R.id.tool_bar_edit_entry);
        mTitleTextView = (TextView)findViewById(R.id.entry_text_view_title);
        mMoodTextView = (TextView)findViewById(R.id.entry_text_view_mood);
        mMessage = (EditText)findViewById(R.id.entry_edit_text_message);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEntry.this, MainActivity.class));
            }
        });

        getItemFromDetailActivity();
        attachMoodListener();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mEntryDatabasereference = mFirebaseDatabase.getReference().child("entry");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_entry,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.save_edited_entry:
                addEntry();
                break;
        }
        return true;
    }

    private void attachMoodListener(){
        mMoodTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpMoodPickerDialog();
            }
        });
    }

    private void setUpMoodPickerDialog(){

        final String [] mood = {"Happy","Strange","Lucid","Sad","Emotional"};

        final ArrayList<Integer> selectedItemIndex  = new ArrayList<>();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_mood);
        builder.setMultiChoiceItems(mood, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                // If the user checked the item, add it to the selected items
                if (b){
                    selectedItemIndex.add(i);
                }else if (selectedItemIndex.contains((i))){
                    // Else, if the item is already in the array, remove it
                    selectedItemIndex.remove(Integer.valueOf(i));
                }
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                StringBuilder stringBuilder = new StringBuilder();
                String separator = "";

                for (int a = 0; a < selectedItemIndex.size(); a++){
                    int index = selectedItemIndex.get(a);
                    Log.i("Index",String.valueOf(index));
                        stringBuilder.append(separator);
                        stringBuilder.append(mood[index]);
                        separator = ", ";
                }
                //String selectedMood = builder.toString();
                Toast.makeText(AddEntry.this,stringBuilder + " Selected",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    private void getItemFromDetailActivity(){
        Intent intent = getIntent();
        Log.i("Intent", "intent gotten");
        Bundle bundle = intent.getExtras();
        Log.i("Intent", "bundle gotten");
        if (bundle != null ) {
            String date = bundle.getString("date");
            String title = bundle.getString("title");
            String message = bundle.getString("message");
            String moodOne = bundle.getString("moodOne");
            String moodTwo = bundle.getString("moodTwo");
            String moodThree = bundle.getString("moodThree");

            String mood = moodOne + ", " + moodTwo + ", " + moodThree;

            mToolbar.setTitle(date);
            mTitleTextView.setText(title);
            mMessage.setText(message);
            mMoodTextView.setText(mood);

        }
    }

    private void addEntry() {

        long timeStamp = new Date().getTime();
        String mood = String.valueOf(mMoodTextView.getText());
        String moods = "Happy, good, lovely";
        String title = String.valueOf(mTitleTextView.getText());
        String message = String.valueOf(mMessage.getText());

        UserModel user = new UserModel();
        user.setTimeStamp(timeStamp);
        user.setTitle("Yes, I will make it");
        user.setMoods(moods);
        user.setBodyMessage("hjdhdhjdhdhjdjhdjhdjkdkldmddm");


        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        String userId = currentUser.getUid();
        mEntryDatabasereference.child(userId).push().setValue(user);
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
    }
}
