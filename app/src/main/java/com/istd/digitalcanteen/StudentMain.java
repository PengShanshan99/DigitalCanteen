package com.istd.digitalcanteen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class StudentMain extends AppCompatActivity {


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
                case R.id.navigation2_explore:
                    fragment = new ExploreFragments();
                    break;
                case R.id.navigation2_shoppingCart:
//                    Toast.makeText(StudentMain.this, "ShoppingCart coming soon.",Toast.LENGTH_LONG).show();
                    fragment = new ShoppingCFragment();

                    break;
                case R.id.navigation2_wallet:
                    fragment = new WalletFragments();
                    break;

            }
            return loadFragments(fragment);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        Log.i("orderqueueDebug","creating order fragment");
        loadFragments(new ExploreFragments());
        Log.i("orderqueueDebug","order fragment created");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
