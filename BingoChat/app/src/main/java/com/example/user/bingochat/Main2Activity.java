package com.example.user.bingochat;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.zip.Inflater;

public class Main2Activity extends AppCompatActivity {

   private mainFragment mainFragment;
   private ChatsFragment chatsFragment;
   private RequestsFragment requestsFragment;
   private FriendsFragment friendsFragment;
   private FrameLayout mMainFrame;
    private FirebaseAuth mAuth;
    private Toolbar mToolbar;
    private DatabaseReference mUserRef;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_location:
                    Intent locationIntent=new Intent(Main2Activity.this,LocationActivity.class);
                    startActivity(locationIntent);

                    return  true;
                case R.id.navigation_request:
                    setFragment(requestsFragment);


                    return true;
                case R.id.navigation_chats:
                    setFragment(chatsFragment);

                    return true;
                case R.id.navigation_friends:
                    setFragment(friendsFragment);

                    return true;
                case R.id.navigation_profile:
                    Intent settingIntent=new Intent(Main2Activity.this,SettingsActivity.class);
                    startActivity(settingIntent);
                    return  true;

            }
            return false;
        }
    };

    private void setFragment(android.support.v4.app.Fragment fragment) {
   android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Bingo");
        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }

        chatsFragment =new ChatsFragment();
        requestsFragment=new RequestsFragment();
        friendsFragment=new FriendsFragment();
        mainFragment =new mainFragment();
        mMainFrame=(FrameLayout)findViewById(R.id.main_frame);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_chats);





    }




    @Override
    public void onStart() {
        super.onStart();
        //Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //mAuth.addAuthStateListener(mAuthListener);

        if(currentUser == null){

            sendToStart();
            /*Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(startIntent);
            finish();*/
        } else {

            mUserRef.child("online").setValue("true");

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }

    }

    private void sendToStart() {
        Intent startIntent = new Intent(Main2Activity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout_btn){

            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }

        if(item.getItemId() == R.id.main_settings_btn) {

            Intent settingsIntent = new Intent(Main2Activity.this, SettingsActivity.class);
            startActivity(settingsIntent);

        }

        if (item.getItemId() == R.id.main_all_btn) {

            Intent settingsIntent = new Intent(Main2Activity.this, UsersActivity.class);
            startActivity(settingsIntent);

        }
        if (item.getItemId() == R.id.main_location_btn) {

            Intent settingsIntent = new Intent(Main2Activity.this, LocationActivity.class);
            startActivity(settingsIntent);

        }

        return true;
    }

}
