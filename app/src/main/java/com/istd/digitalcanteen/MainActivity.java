package com.istd.digitalcanteen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    //todo_done 1.2. the tutorial has changed the whole MainActivity extends mOnNavigationItemSelectedListener, but I didnt do it. Think about the difference.

    //TODO_done 1. change the UI without switching to another activity

    private boolean loadFragments(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_edit_menu:
//                    mTextMessage.setText(R.string.title_edit_menu);
//                    Intent intent = new Intent(MainActivity.this, Menu.class);
//                    startActivity(intent);
//                    return true;
//            }
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navigation_explore:
                    fragment = new ExploreFragments();
                    break;
                case R.id.navigation_shoppingCart:
                    break;
                case R.id.navigation_wallet:
                    fragment = new WalletFragments();
                    break;

            }
            return loadFragments(fragment);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("orderqueueDebug","creating order fragment");
        loadFragments(new ExploreFragments());
        Log.i("orderqueueDebug","order fragment created");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
