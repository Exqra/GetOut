package Model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FirebaseClass {

    private static String key;
    private static boolean isEntered = false;


    public static void pushEvent( DatabaseReference ref, Event event)
    {
        ref.push().setValue( event);
    }

    /**
     *
     * @param ref takes type reference of the event
     * @param title
     * @return
     */
    public static String getKey(final MyCallback myCallback, DatabaseReference ref, String title)
    {
        final String finalTitle = title;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                    for( DataSnapshot child : children)
                    {
                        Event temp = child.getValue(Event.class);
                        if(temp.getTitle().equals( finalTitle))
                        {
                            isEntered = true;
                            System.out.println("************** TITLE FOUND");
                            key = child.getRef().getKey();
                            System.out.println(" Key: " + key);
                        }
                    }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        while( !isEntered)
        {

        }
        return key;
    }

    /**
     *
     * @param ref takes the type of event
     * @param event
     */
    public static void updateEvent( DatabaseReference ref, Event event)
    {
        final Event finalEvent = event;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> events = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : events){
                    Event temp = snapshot.getValue(Event.class);
                    if(temp.getTitle().equals(finalEvent.getTitle()) && temp.getDescription().equals(finalEvent.getDescription())){
                        snapshot.getRef().setValue(finalEvent);
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
    public static void removeEvent( DatabaseReference ref, Event event)
    {
        final Event finalEvent = event;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> events = dataSnapshot.getChildren();
                for(DataSnapshot snapshot : events){
                    Event temp = snapshot.getValue(Event.class);
                    if(temp.getTitle().equals(finalEvent.getTitle()) && temp.getDescription().equals(finalEvent.getDescription())){
                        snapshot.getRef().setValue(null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
