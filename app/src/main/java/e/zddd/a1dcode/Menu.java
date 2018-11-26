package com.istd.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    RecyclerView recyclerViewMenu;
    FirebaseDatabase mFB;//a firebase database object
    DatabaseReference mRef;// a reference object for firebase
    Adapter mAdapter;// an adapter that combines the firebase reference to the recycler view in our UI
    List list;// a list for food objects
    ArrayList<Integer> list_of_id;// a list to remember the id's when the food items are retrieved from firebase, so that when
    //people want to modify a food and click on a food display in the ui and be led to the edit food page, the edittextviews could be filled up automatically
    //with the data retreved from firebase first.

    //the Log.i is created for me to debug. You can ignore it.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//for add new food item into the menu
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, EditMenu.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fab_refresh = (FloatingActionButton) findViewById(R.id.fab_refersh);//refresh the list when this button is clicked
        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//retrieve data from firebase and update the UI
                mFB = FirebaseDatabase.getInstance();
                mRef = mFB.getReference("menu");
                recyclerViewMenu = findViewById(R.id.recyclerView_menu);
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Toast toast = Toast.makeText(Menu.this, R.string.databaseChanged, Toast.LENGTH_LONG);
//                        toast.show();
                        list = new ArrayList<Food>();
                        list_of_id = new ArrayList<Integer>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            list_of_id.add(Integer.parseInt(dataSnapshot1.getKey()));
                            Food foodRetrieved = dataSnapshot1.getValue(Food.class);
                            Food localFood = new Food();
                            String name = foodRetrieved.getName();
                            String price = foodRetrieved.getPrice();
                            String prepTime = foodRetrieved.getPrepTime();
                            String availability = foodRetrieved.getAvailability();
                            localFood.setName(name);
                            localFood.setPrice(price);
                            localFood.setPrepTime(prepTime);
                            localFood.setAvailability(availability);
                            list.add(localFood);
                            String theList = list_of_id.toString();
                            Log.i("hello","successfully added one more food");
                            Log.i("listofid",theList);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("hello", "Failed to read value.", databaseError.toException());
                    }
                });
                mAdapter = new Adapter(list, Menu.this, list_of_id);
                recyclerViewMenu.setLayoutManager(new LinearLayoutManager(Menu.this));
                recyclerViewMenu.setAdapter(mAdapter);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("starthay","onStart is called");

    }
}
