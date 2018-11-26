package e.zddd.a1dcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.istd.digitalcanteen.Adapter;

import java.util.ArrayList;
import java.util.List;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

//import firebase


public class Stall1 extends AppCompatActivity {

    private TextView mTextMessage;
    RecyclerView recyclerViewMenu;
    List<FireModel> list;
    RecyclerView recycle;
    Button view;
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase
    Adapter mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    List list;// a list for food objects
    ArrayList<Integer> list_of_id;// a list to remember the id's when the food items are retrieved from firebase, so that when
    //people want to modify a food and click on a food display in the ui and be led to the edit food page, the edittextviews could be filled up automatically
    //with the data retreved from firebase first.
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    double foodId = 00000000 // need to be created / updated in firebase



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            view = (Button) findViewById(R.id.view);
            recycle = (RecyclerView) findViewById(R.id.recycle);
            mFB = FirebaseDatabase.getInstance();
            mRef = database.getReference("message");


            //to display menu in card view
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    list = new ArrayList<FireModel>();
                    for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                        FireModel value = dataSnapshot1.getValue(FireModel.class);
                        FireModel fire = new FireModel();
                        String name = value.getName();
                        String price = value.getPrice();
                        String availability = value.getAvailability();
                        fire.setName(name);
                        fire.setPrice(price);
                        fire.setAvailability(availability);
                        list.add(fire);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Hello", "Failed to read value.", error.toException());
                }
            });


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //not sure what this part does
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(list,Stall1.this);
                    RecyclerView.LayoutManager recyce = new GridLayoutManager(Stall1.this,2);
                    /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
                    // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                    recycle.setLayoutManager(recyce);
                    recycle.setItemAnimator( new DefaultItemAnimator());
                    recycle.setAdapter(recyclerAdapter);


                    //onClick: create food id, write data to firebase
                    DatabaseReference ref = database.getReference().child("inCart");
                    DatabaseReference refFoodName = database.getReference(foodId/ + "/name");
                    refFoodName.setValue(name);
                    DatabaseReference refFoodPrice = database.getReference(foodId/ + "/price");
                    refFoodPrice.setValue(price);
                    


                }
            });


        }
    }
}




