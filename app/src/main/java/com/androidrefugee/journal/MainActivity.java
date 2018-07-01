package com.androidrefugee.journal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.androidrefugee.journal.Adapters.JournalAdapter;
import com.androidrefugee.journal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private  RecyclerView recyclerView;
    private ArrayList<UserModel> list;
    FloatingActionButton fab;
    private Toolbar mToolbar;

    private  FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mEntryDatabasereference;
    private  FirebaseAuth.AuthStateListener mAuthStateListener;
    private ChildEventListener mChildEventListener;
    private String userId;

    JournalAdapter adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar)findViewById(R.id.tool_bar_main_activity);
        setSupportActionBar(mToolbar);

        fab = (FloatingActionButton)findViewById(R.id.fab_add_entry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, AddEntry.class);
                 startActivity(intent);
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mEntryDatabasereference = mFirebaseDatabase.getReference().child("entry");

        list = new ArrayList<>();

        attachAuthStateListener();

        JournalAdapter adapter = new JournalAdapter(list);
        recyclerView = (RecyclerView)findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);


    }

    private void generateDummyData(){
        String moods = "happy , goood,  solo";
        for (int i = 0; i< 20; i++){
            Date date = new Date();

//            list.add(new UserModel(date.getTime(),"Good day",
//                    "teggbdggdhdhhd hhdhhdhdhd dhdhdhdd  dhdhd hdhd hdhdh djdhh" +
//                            "hdhdhdhdhdhdh hdhh dhhd ndhhdhhd the boy is the good dad shsh shdhd" +
//                            "hdhdhdhdhdhdhdh dhhdhhd",moods));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out:
                Toast.makeText(this,"Signing out",Toast.LENGTH_SHORT).show();
                mFirebaseAuth.signOut();
                finish();
                Intent intent = new Intent(this,SignIn.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private  void attachAuthStateListener (){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    //onSignedInInitialised();
                }else {
                    Intent intent = new Intent(MainActivity.this, SignIn.class);
                    startActivity(intent);
                }
            }
        };

    }

    private void onSignedInInitialised (){
//        mChildEventListener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    if (dataSnapshot != null){
//                    userId = mFirebaseAuth.getCurrentUser().getUid();
//                    String title = dataSnapshot.child(userId).child("title").getValue(String.class);
//                    //list.add(user);
//                        Log.i("Snap shot","not null " + title);
//                    }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mEntryDatabasereference.addChildEventListener(mChildEventListener);

        mEntryDatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void showData(DataSnapshot dataSnapshot){
        userId = mFirebaseAuth.getCurrentUser().getUid();
        for (DataSnapshot db : dataSnapshot.getChildren()){
 //       Long timeStamp = db.child(userId).getValue(UserModel.class).getTimeStamp();
//       String title =  db.child(userId).getValue(UserModel.class).getTitle();
//        String moods =   db.child(userId).getValue(UserModel.class).getMoods();
//        String message = db.child(userId).getValue(UserModel.class).getBodyMessage();

            UserModel userModel = db.child(userId).getValue(UserModel.class);

//           Log.i("Snap shot", title);
         list.add(userModel);

        }
    }
}
