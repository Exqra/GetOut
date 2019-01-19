/**
 * The activity that shows the event user touched
 *
 * @author Çağlar Çankaya
 */
package com.bilkent.subfly.getout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.CurrentParticipantsAdapter;
import Adapter.DeleteButtonClickListener;
import Adapter.EditButtonClickListener;
import Adapter.JoinButtonClickListener;
import Adapter.LeaveButtonClickListener;
import Model.Event;
import Model.EventManager;
import Model.User;

public class DetailsActivity extends AppCompatActivity {

    //View properties
    private TextView title;
    private TextView hour;
    private TextView date;
    private TextView description;
    private TextView location;
    private TextView participants;
    private TextView creatorName;
    private Bundle extras;
    private RecyclerView recyclerView2;
    private CurrentParticipantsAdapter adapter2;
    private List<Event> eventList;
    private List<User> userList;
    private Button edit;
    private Button join;
    private Button delete;
    private Button leave;

    //Added by Ali Taha Dinçer
    private FirebaseUser mUser;
    private String currentUserMail;
    private String currentUserName;
    private EventManager eventManager;
    private Event event;
    private User dummyUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        extras = getIntent().getExtras();

        //Initializing all view properties
        title = findViewById(R.id.dTitle);
        hour = findViewById(R.id.dHour);
        date = findViewById(R.id.dDate);
        description = findViewById(R.id.dDescription);
        location = findViewById(R.id.dLocation);
        participants = findViewById(R.id.currentNumber);
        creatorName = findViewById(R.id.creatorName);
        eventList = new ArrayList<Event>();
        edit = findViewById(R.id.edit);
        join = findViewById(R.id.join);
        delete = findViewById(R.id.delete);
        leave = findViewById(R.id.leave);

        //Taking data from database
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserMail = mUser.getEmail();
        currentUserName = currentUserMail.substring(0, 1).toUpperCase() + currentUserMail.substring(1, currentUserMail.indexOf('.')) + " " + currentUserMail.substring(currentUserMail.indexOf('.') + 1, currentUserMail.indexOf('.') + 2).toUpperCase() + currentUserMail.substring(currentUserMail.indexOf('.') + 2, currentUserMail.indexOf('@'));

//        dummyUser = new User(currentUserMail);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot snapshot : children) {
                    Iterable<DataSnapshot> childrenOfChildren = snapshot.getChildren();
                    for (DataSnapshot snapshot1 : childrenOfChildren) {
                        if (snapshot1.getValue(Event.class).getTitle().equals(title.getText().toString()))
                            event = snapshot1.getValue(Event.class);
                    }
                }
//                for (int i = 0; i < eventList.size(); i++) {
//                    if (eventList.get(i).getTitle().equals(title.getText().toString()) &&
//                            eventList.get(i).getDescription().equals(description.getText().toString())) {
//                        event = eventList.get(i);
//                    }
//                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Do nothing...
            }
        });

        DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
        firebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("events").child(event.getType()).getChildren();
                for (DataSnapshot child : children) {
                    if (child.getValue(Event.class).getTitle().equals(title.getText().toString()))
                        event = child.getValue(Event.class);
                    recyclerView2 = findViewById(R.id.participantRecycler);
                    recyclerView2.setHasFixedSize(true);
                    recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter2 = new CurrentParticipantsAdapter(getApplicationContext(), event.getUser_list());
                    recyclerView2.setAdapter(adapter2);
                    participants.setText(event.getNumberOfCurrentParticipants() + "/" + event.getNumberOfParticipants());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /**
         * Edited by Tolga Catalpinar 19.01.2019
         */
        DatabaseReference firebaseRef2 = FirebaseDatabase.getInstance().getReference();
        firebaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.child("events").child(event.getType()).getChildren();
                for (DataSnapshot child : children) {
                    if (child.getValue(Event.class).getTitle().equals(title.getText().toString()))
                        event = child.getValue(Event.class);
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    final String name = email.substring(0, 1).toUpperCase() + email.substring(1, email.indexOf('.')) + " " + email.substring(email.indexOf('.') + 1, email.indexOf('.') + 2).toUpperCase() + email.substring(email.indexOf('.') + 2, email.indexOf('@'));


                    User user = dataSnapshot.child("users").child(name).getValue(User.class);
                    EventManager manager = new EventManager(event, user);
                    System.out.println("event is: " + event.getTitle());
                    System.out.println("user is: " + user.getName());

                    if (user.getName().equals(event.getUserName())) {
                        edit.setOnClickListener(new EditButtonClickListener(event));
                        delete.setOnClickListener(new DeleteButtonClickListener(manager));
                        join.setVisibility(View.INVISIBLE);
                        leave.setVisibility(View.INVISIBLE);
                    } else {
                        boolean flag = false;
                        edit.setVisibility(View.INVISIBLE);
                        delete.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < event.getUser_list().size(); i++) {
                            System.out.println("Eventteki " + (i+1) + ". adam: " + event.getUser_list().get(i));
                            if (event.getUser_list().get(i).equals(user.getName())) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            join.setVisibility(View.INVISIBLE);
                            leave.setVisibility(View.VISIBLE);
                            leave.setOnClickListener(new LeaveButtonClickListener(manager, participants, leave, join));

                        } else {
                            leave.setVisibility(View.INVISIBLE);
                            join.setVisibility(View.VISIBLE);
                            join.setOnClickListener(new JoinButtonClickListener(manager, participants, leave, join));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        if (extras != null) {
            title.setText(extras.getString("title"));
            hour.setText(extras.getString("hour"));
            date.setText(extras.getString("date"));
            description.setText(extras.getString("description"));
            location.setText(extras.getString("location"));
            creatorName.setText(extras.getString("author"));
        }
    }
}
