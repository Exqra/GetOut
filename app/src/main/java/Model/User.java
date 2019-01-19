package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Muhammed Naci Dalkıran and Sena Korkut
 * @version  18.12.2018
 * @version 23.12.2018 (Last Version)
 */
public class User {

    //Properties
    private String name;
    private String email;
//    private Event event;
    private List<Event> attending_events = new ArrayList<Event>();
    private List<Event> created_events = new ArrayList<Event>();
//    private String password;

    /**
     * This is a empty constructor
     */
    public User(){

    }

    /**
     * This is a constructor of User class
     * @param email
     */
    public User(String email){
        nameConverter(email);
        this.email = email;
        attending_events = new ArrayList<Event>();
        created_events = new ArrayList<Event>();
    }

//    /**
//     * This is contractor method of this class
//     * @param email is a email address of user.
//     */
//    public User( String email, String password ) {
//        this.email = email;
//        nameConverter( email );
//        attending_events = new ArrayList<Event>();
//        created_events = new ArrayList<Event>();
//        this.password = password;
//    }

    /**
     * This method adds event to attending Event.
     * @param event is a event user went.
     */
    public void addAttendingEvent( String id, Event event ) {
        this.attending_events.add( event );
    }

    /**
     * This method remove event from attending event when user want to drop event.
     * @param event is dropped event by user.
     */
    public void removeAttendingEvent( Event event ){
        this.attending_events.remove( event );
    }
    /**
     * This method calculates and returns rateGame for pieChart.
     * @return rateGame
     */
    public float pieChart( String type ){
        //Properties
        List<Event> eventList;
        float counter;
        //Program Code
        eventList = new ArrayList<Event>();
        eventList.addAll(attending_events);
        eventList.addAll(attending_events.size(),created_events);
        counter = 0;
        for( int i = 0 ; i < eventList.size(); i ++){
            System.out.println("--------------------------------------------------");

            if ( type.equals(eventList.get(i).getType())){
                counter = 10f + counter;
            }
        }
        System.out.println("-------------------------------" + "  " + counter);

        return counter;
    }


    /**
     * This is getter method of name
     * @return name of user.
     */
    public String getName( ) {
        return name;
    }

    /**
     * This is getter method of Email.
     * @return email of user.
     */
    public String getEmail( ) {
        return email;
    }

    /**
     * This is setter method of name
     * @param email is a email address of user.
     */
    private void nameConverter( String email ){
        try
        {
            name = "";
            name += email.substring( 0 , 1 ).toUpperCase( ) + email.substring( 1 , email.indexOf( '.' )) + " " + email.substring(email.indexOf( '.' )+ 1 , email.indexOf( '.' ) + 2 ).toUpperCase( ) + email.substring( email.indexOf( '.' ) + 2 , email.indexOf( '@' ) );
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }

    }

//    /**
//     * This is getter method of event.
//     * @return even of user.
//     */
//    public Event getEvent( ) {
//        return event;
//    }

    /**
     * This is setter method of created event.
     * @param created_events is created by user.
     */
    public void setCreated_events( List<Event> created_events ) {
        this.created_events = created_events;
    }

    /**
     * This is getter method of attending events
     * @return attending event of user.
     */
    public List<Event> getAttending_events( ) {
        return attending_events;
    }

    /**
     * This is getter method of created_events.
     * @return event created by user.
     */
    public List<Event> getCreated_events( ) {
        return created_events;
    }




    /**
     * This is setter event of attending events.
     * @param attending_events is list of attending event.
     */
    public void setAttending_events(List<Event> attending_events) {
        this.attending_events = attending_events;
    }

    /**
     * This is to String method of user.
     * @return name of user.
     */
    public String toString( ){
        return name;
    }

//    public String getPassword() {
//        return password;
//    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setEvent(Event event) {
//        this.event = event;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public void setName(String name) {
        this.name = name;
    }
}
