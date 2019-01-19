package com.bilkent.subfly.getout;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Event;
import Model.EventManager;
import Model.FirebaseClass;
import Model.GameEvent;
import Model.MealEvent;
import Model.MyCallback;
import Model.SportEvent;
import Model.User;


public class StartActivity extends AppCompatActivity {


    private Button signUp;
    private Button logIn;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    // sil
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MealEvent event = new MealEvent( "title", "description", "asd", "bsd", 10, "bb", "cc");
        GameEvent event2 = new GameEvent( "asd", "sss", "kkk", "bs,", 22, "ccc","kkk");
//        FirebaseDatabase.getInstance().getReference().child("events").child("meal_events").push().setValue(event);
//        FirebaseDatabase.getInstance().getReference().child("events").child("game_events").push().setValue(event2);

//        MyCallback myCallback = new MyCallback() {
//            @Override
//            public void onCallback(String value) {
//                System.out.println( "callback is called");
//            }
//        };
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("events").child("sport_events");
//        String title = "deneme";
//        System.out.println( "Key is waiting");
//        FirebaseClass.pullKey( ref, title);
//        String key = FirebaseClass.getKey();
//        System.out.println( "Key is: " + key);




//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference userReference = database.getReference().child("users");
//        userReference.child( "Tolga Catalpinar").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final User userN = dataSnapshot.getValue(User.class);
//                database.getReference().child( "events").child("sport_events").child( "-LWFxXUbLY5rJuShKR26").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Event event = dataSnapshot.getValue(Event.class);
//                        event.add( userN);
//                        DatabaseReference eventsReference = database.getReference().child("events").child( "sport_events");
//                        FirebaseClass.pushEvent( eventsReference, event);
//
//                        EventManager manager = new EventManager( event, userN);
//                        manager.joinEvent();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });




//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference events = database.getReference().child( "events").child( "sport_events");
//        final String key = "-LWFxXUbLY5rJuShKR26";
//        System.out.println( "-----------------key is: " + key);
//        events.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Event event = dataSnapshot.child( key).getValue(Event.class);
//                User userT = new User( "seconddeneme.tolga@ug.bilkent.edu.tr");
//                DatabaseReference usersReference = database.getReference().child( "users");
//                usersReference.child( userT.getName()).setValue( userT);
//                event.add( userT);
//                EventManager manager1 = new EventManager( event, userT);
//                manager1.joinEvent();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user == null){
            setContentView(R.layout.activity_start_screen);

            signUp = findViewById(R.id.sign);
            logIn = findViewById(R.id.loog);

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), SignUpActivity.class);
                    startActivity(intent);
                }
            });

            logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
