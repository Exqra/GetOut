package com.bilkent.subfly.getout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.SeeEventsAdapter;
import Model.Event;
import Model.EventManager;

public class EventViewerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeeEventsAdapter adapter1;
    private List<Event> eventsList;
    private Bundle bundle;
    private String bundle1;
    private EventManager eventManager;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.event_list_view);
        eventsList = new ArrayList<Event>();
        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        bundle = getIntent().getExtras();
        bundle1 = bundle.getString("type");

    }
    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              eventsList.clear();
              Iterable<DataSnapshot> children = dataSnapshot.getChildren();
              for (DataSnapshot snapshot : children){
                  Iterable<DataSnapshot> childrenOfChildren = snapshot.getChildren();
                  for(DataSnapshot snapshot1 : childrenOfChildren){
                      eventsList.add(snapshot1.getValue(Event.class));
                  }
              }
              eventManager = new EventManager(eventsList);
              List<Event> a = eventManager.getEventByType(bundle1);
              recyclerView = findViewById(R.id.recyclerViewID);
              recyclerView.setHasFixedSize(true);
              recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
              adapter1 = new SeeEventsAdapter(getApplicationContext(), a);
              recyclerView.setAdapter(adapter1);
          }
          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
              //Do Nothing...
          }

        });



    }

}

