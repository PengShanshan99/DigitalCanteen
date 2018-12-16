package com.istd.digitalcanteen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
//A bottom navigation activity for stall owner UI. Different fragments would be loaded when different menu option is selected.
public class MainActivity extends AppCompatActivity {
    //todo_done 1.2. the tutorial has changed the whole MainActivity extends mOnNavigationItemSelectedListener, but I didnt do it. Think about the difference.
    //TODO_done 1. change the UI without switching to another activity
//TODO 1.2. write the suggested cooking sequence fragment
    //TODO 3. implemenet add-on/toppings for the orders
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
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.navigation_home:
                    fragment = new OrdersFragment();
                    break;
                case R.id.navigation_menu:
                    fragment = new MenuFragments();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new SuggestedQueueFragment();
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
        loadFragments(new OrdersFragment());
        Log.i("orderqueueDebug","order fragment created");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
