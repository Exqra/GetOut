package Model;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * This class has methods.
 * This methods provide to work on events.
 * Also, events' and users' knowledge is edited.
 * @author Sena Korkut and Muhammed Naci dalkıran
 * @version : 18.12.2018
 * @version 23.12.2018 (Last Version)
 */
public class EventManager {

    //Properties
    private List<Event> topFive;
    private List<Event> runOutOfParticipant;
    private List<Event> allEvents;
    private User user;
    private Event event;

    /**
     * This is constructor method.
     * @param event is specific event.
     * @param user is user who is currently using application
     * @param allEvents is list of all events.
     */
    public EventManager(Event event, User user, List<Event> allEvents) {
        this.event = event;
        this.user = user;
        this.allEvents = allEvents;
    }
    public EventManager(Event event, User user) {
        this.event = event;
        this.user = user;
    }

    /**
     * This is constructor method.
     * @param user is user who is currently using application.
     */
    public EventManager(User user){
        this.user = user;
    }

    /**
     * This is constructor method.
     * @param eventList is list of all events.
     */
    public EventManager(List<Event> eventList){
        this.allEvents = eventList;
    }

    /**
     * This method sorts all events according to their number of participants.
     * @return sorted list with top 5 events.
     */
    public List<Event> getTopFive( ) {

        //Properties
        int counter;
        int numberOfEvent;

        //Program Code
        counter = 0;
        List<Event> sortedList = new ArrayList<Event>();

        if (allEvents.size() < 5){
            numberOfEvent = allEvents.size();
        }
        else
            numberOfEvent = 5;

        int[] numberOfParticipants = new int[allEvents.size()];
        for (int i = 0; i < allEvents.size(); i ++){
            numberOfParticipants[i] = allEvents.get(i).getNumberOfParticipants();
        }
        Arrays.sort(numberOfParticipants);
        for (int i = numberOfParticipants.length - 1 ; i >  numberOfParticipants.length - numberOfEvent - 1 ; i--){
            for (int j = 0; j < allEvents.size(); j++){
                if (counter < 5){
                    if (numberOfParticipants[i] == allEvents.get(j).getNumberOfParticipants()){
                        sortedList.add(allEvents.get(j));
                        counter++;
                    }
                }

            }

        }
        return sortedList;

    }

    /**
     * This method sorts all events according to rate of current participants to allowed participants
     * @return sorted list with top 3 events.
     */
    public List<Event> getRunOutOfParticipant() {
        //Properties
        int counter;
        int numberOfEvent;

        //Program Code
        if (allEvents.size() < 5){
            numberOfEvent = allEvents.size();
        }
        else
            numberOfEvent = 5;
        counter = 0;
        List<Event> sortedList = new ArrayList<Event>();
        double[] rateOfParticipants = new double[allEvents.size()];
        Collections.shuffle(allEvents);
        for (int i = 0; i < allEvents.size(); i ++){
            rateOfParticipants[i] = allEvents.get(i).getRateOfParticipants();
        }

        Arrays.sort(rateOfParticipants);

        for (int i = rateOfParticipants.length - 1 ; i > rateOfParticipants.length - numberOfEvent - 1; i--){

            for (int j = 0; j < allEvents.size(); j++){
                if (counter < 5) {
                    if (rateOfParticipants[i] == allEvents.get(j).getRateOfParticipants()){

                        sortedList.add(getAllEvents().get(j));
                        counter ++;
                    }
                }
            }

        }
        return sortedList;
    }


    /**
     * this is a getter method of all events
     * @return list of all events
     */
    public List<Event> getAllEvents( ) {
        return allEvents;
    }

    public User getUser() {
        return user;
    }

    public Event getEvent() {
        return event;
    }

    /**
     * This method provides user to join event.
     //* @param event is created by other users
     * @return boolean value
     * Added by Tolga Catalpinar 19.01.2019
     */
    public boolean joinEvent(){
        if ( event.getNumberOfCurrentParticipants() == event.getNumberOfParticipants() ){
            return false;
        }
        else {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference events = database.getReference().child( "events");
            DatabaseReference users = database.getReference().child( "users");

            String type = event.getType();
            DatabaseReference event_type = events.child( type);

            event.add( user.getName());
            event.setNumberOfCurrentParticipants(event.getNumberOfCurrentParticipants() + 1);
            event.setRateOfParticipants();

            FirebaseClass.updateEvent( event_type, event);

            user.getAttending_events().add( event);


            String name = user.getName();
            List<Event> list = user.getAttending_events();
            System.out.println( "al : " + name + list.size());

            FirebaseClass.updateUser( users, user);


            // Update creator's created_events list
            FirebaseClass.updateCreator( users, event);

            // Update all attenders
            FirebaseClass.updateAttenders( users, event);

            return true;
        }
    }

    /**
     * This methods remove the user from the event
    //* @param event is an event.
     //* @param user is user who wants to drop the event.
     * @return boolean variable
     * Added by Tolga Catalpinar 19.01.2019
     */
    public boolean dropEvent(){

        // Remove event from the user
        for(int i = 0; i < user.getAttending_events().size(); i ++)
        {
            if( user.getAttending_events().get(i).getTitle().equals( event.getTitle()))
            {
                user.getAttending_events().remove( i);
            }
        }
        // Remove the user from the event
        for(int i = 0; i < event.getUser_list().size(); i ++)
        {
            if( event.getUser_list().get( i).equals( user.getName()))
                event.getUser_list().remove( i);
        }

        event.setNumberOfCurrentParticipants(event.getNumberOfCurrentParticipants() - 1);
        event.setRateOfParticipants();

        // Update event
        DatabaseReference databaseEvents = FirebaseDatabase.getInstance().getReference("events").child(event.getType());
        FirebaseClass.updateEvent( databaseEvents, event);

        // Update the user
        DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference( "users");
        FirebaseClass.updateUser( databaseUsers, user);

        // Update all attenders
        FirebaseClass.updateAttenders( databaseUsers, event);

        // Update the creator
        FirebaseClass.updateCreator( databaseUsers, event);

        return true;
    }

    /**
     * This method deletes event.
     //* @param event is event created by user in the parameter.
     //* @param user is a user who creates this event.
     * @return is a boolean value.
     * Added by Tolga Catalpinar 19.01.2019
     */
    public boolean deleteEvent(){

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference( "users");
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference( "events");

        // Remove event from creator
        FirebaseClass.removeEvent(usersRef.child(event.getUserName()).child("created_events"), event);
        // Remove event from all attenders
        FirebaseClass.removeFromAllAttenders( usersRef, event);
        // Delete event from events on Firebase
        FirebaseClass.removeEvent( eventsRef.child(event.getType()), event);
        return true;
    }

    public void setAllEvents(List<Event> allEvents) {
        this.allEvents = allEvents;
    }

    /**
     * This method show type events which is wanted by user.
     * @param type is type of events.
     * @return list of determined type of events.
     */
    public List<Event> getEventByType( String type ){

        //Properties
        List<Event> typeEvent;

        //Program Code
        typeEvent = new ArrayList<Event>( );
        if (type.equals("AllEvents")){
            typeEvent = allEvents;
        }
        else{
            for ( int i = 0; i < allEvents.size( ); i++ ){
                if ( type.equals( allEvents.get(i).getType( ) ) ) {
                    typeEvent.add( allEvents.get(i) );
                }
            }
        }

        return typeEvent;
    }

    /**
     * This method provides user to edit its event.
     * @param event is a event created by this user.
     * @param title is a new title of event.
     * @param place is a new place of events.
     * @param date is a new date of events.
     * @param deadline is a new deadline of events.
     * @param numberOfParticipants is a new number of participants of events.
     * @param description is a new description of events.
     * @return boolean value.
     */
    public boolean editEvent(Event event, String title, String place, String date, String deadline, int numberOfParticipants, String description){
        event.setDate(date);
        event.setDeadline(deadline);
        event.setNumberOfParticipants(numberOfParticipants);
        event.setDescription(description);
        event.setTitle(title);
        event.setPlace(place);
        return true;
    }
}
