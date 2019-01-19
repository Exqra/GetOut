package Model;

/**
 * This class provides static methods to be used for join, delete, leave and edit events
 * @author Tolga Catalpinar
 * @date 19.01.2019
 */

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;


public class FirebaseClass {


    /**
     *
     * @param ref takes type reference of the event
     * @param title
     * @return
     */
//    public static void pullKey(DatabaseReference ref, final String title)
//    {
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//                    for( DataSnapshot child : children)
//                    {
//                        Event temp = child.getValue(Event.class);
//                        if(temp.getTitle().equals( title))
//                        {
//                            System.out.println("************** TITLE FOUND");
//                            key = child.getRef().getKey();
//                            System.out.println(" Key: " + key);
//                            setKey( key);
//                        }
//                    }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                System.out.println(" cancelled");
//            }
//        });
//    }

    /**
     *
     * @param ref takes the type of event
     * @param event
     */
    public static void updateEvent( final DatabaseReference ref, final Event event)
    {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> events = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : events){
                    Event temp = snapshot.getValue(Event.class);
                    if(temp.getTitle().equals(event.getTitle()) && temp.getDescription().equals(event.getDescription())) {
//                        snapshot.getRef().setValue(event);
                        String key = snapshot.getKey();
                        DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("events").child(event.getType()).child(key);
                        myref.setValue( event);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     *
     * @param ref takes reference of users
     * @param user
     */
    public static void updateUser(DatabaseReference ref, User user)
    {
        ref.child(user.getName()).setValue( user); // StackOverflow
    }
    public static void updateCreator( DatabaseReference usersRef, final Event event)
    {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for( DataSnapshot child : children)
                {
                    User creator = child.getValue(User.class);
                    if( creator.getName().equals( event.getUserName()))
                    {
                        List<Event> list = child.getValue(User.class).getCreated_events();
                        for( int i = 0; i < list.size(); i ++)
                        {
                            if( list.get(i).getTitle().equals(event.getTitle()))
                            {
                                list.set(i, event);
                                creator.setCreated_events(list);
                            }
                        }
                        child.getRef().setValue( creator);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void updateAttenders( DatabaseReference usersRef, final Event event)
    {
        final List<String> allAttenders = event.getUser_list();
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( int i = 0; i < allAttenders.size(); i ++)
                {
                    User temp = dataSnapshot.child(allAttenders.get(i)).getValue(User.class);
                    List<Event> list = temp.getAttending_events();
                    for( int j = 0; j < list.size(); j ++)
                    {
                        if( list.get(j).getTitle().equals( event.getTitle()))
                        {
                            temp.getAttending_events().set( j, event);
                            dataSnapshot.child(allAttenders.get(i)).getRef().setValue( temp);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void joinEvent( DatabaseReference ref, User user)
    {
        ref.push().setValue( user);
    }
    public static void removeEvent( DatabaseReference eventsRef, final Event event)
    {
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> events = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : events){
                    Event temp = snapshot.getValue(Event.class);
                    if(temp.getTitle().equals(event.getTitle())){
                        snapshot.getRef().setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static void removeFromAllAttenders(DatabaseReference usersRef, final Event event)
    {
        final ArrayList<String> userlist = event.getUser_list();
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( int i = 0; i < userlist.size(); i ++)
                {
                    User userToBeRemoved = dataSnapshot.child(userlist.get(i)).getValue(User.class);
//                    System.out.println( "User: " + userToBeRemoved.getName());
                    for( int j = 0; j < userToBeRemoved.getAttending_events().size(); j ++)
                    {
//                        System.out.println("Attending events: size - " + userToBeRemoved.getAttending_events().size() + " and name: " + userToBeRemoved.getAttending_events().get( j).getTitle() );
                        if( userToBeRemoved.getAttending_events().get(j).getTitle().equals( event.getTitle()))
                        {
                            userToBeRemoved.getAttending_events().remove( j);
                            dataSnapshot.child(userlist.get(i)).getRef().setValue(userToBeRemoved);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
