# GetOutGrup number and name :Group_9 : Plato++

Muhammed Naci Dalkiran
Tolga Catalpinar 
Caglar Cankaya
Ali Taha Dincer
Sena Korkut

GetOut
//Description
This application is for students at university who want to find other students to socialize
and save money. Users of this app, particularly students, can create an event in various
categories such as group work (studying, making models for projects etc.), sports (basketball,
football, tennis etc.), meals (ordering meals with someone to get a discount at different
restaurants), transportation (getting taxi with other people to pay less for transportation).
Having been created by students, events can be seen by other users of this application and
they can attend these events that have specific time-duration and place.

//Brief information
Until now, general structure of application was nearly complicated. The modules was defined and we began making project. Designing GUI(.xml files) was nearly complicated; however in the modules 
and GUI, there are some unclear and not complicated parts. In this week, we hope that we are going to handle with these part and make our code more effective.

//After First checkpoint
We fixe some exception. 
Now, We are able to create user and add it into firebase. 
ALso, we can take events from user. 
We handled with GUInamely profile screen, main screen, enhancing login screen.
Adittion to screens, we add some animations for atractive user. As we mentined above, Our essential progress is connection firebase for events. 
Additionally, We write the leave, join etc addapters and some addapters is connected with buttoms.
The user can add profile photo with taking photo. 
We decided to remove profile class in the module. We combine the user and profile class into user class.

//AUTHORS
What did so far
Ali Taha Din�er : FragmentCategories, FragmentCreate, FragmentMain, fragment_layout_catogories.xml, fragment_layout_create.xml, fragment_layout_main.xml, DataBase(FireBase)
�a�lar �ankaya : DetailsActivity, EventViewerActivity, CurrentParticipantsAdapter, SectionStatePagerAdapter, SeeEventsAdapter, details_activity.xml
Tolga �atalpinar : ParticipantsActivity, ProfileController, participants_list.xml, get_out_toolbar.xml, list_row.xml, participants_list.xml, profile_layout, DataBase(FireBase)
Sena Korkut : (abstract) Event, EventHandler, MealEvent, Profile, SportEvent, TransportationEvent, event_list_view.xml
Muhammed Naci Dalk�ran : MainActiviy, Eventlist, GameEvent, GroupWorkEvent, User, UserList, activity_main.xml, FragmentCatogories

The model 	( Event, EventHandler, EventList, GameEvent, GroupWorkEvent,      ) 
	  	( MealEvent, SportEvent, TransportationEvent, User, UserLisT      ) is made by Sena Korkut and Naci Dalk�ran.

The Fragments   ( FragmentCategories, FragmentCreate, FragmentMain,               ) is made by Ali Taha Din�er.
		( fragment_layout_catogories.xml, fragment_layout_create.xml,     ) 
		( fragment_layout_main.xml                                        )

Some Activities ( DetailsActivity, EventViewerActivity, CurrentParticipantsAdapter) is made by �a�lar �ankaya.
Some Adapters	( SectionStatePagerAdapter, SeeEventsAdapter, details_activity.xml)

Some Activities ( articipantsActivity, ProfileController, participants_list.xml, ) is made by Tolga �atalp�nar
		( get_out_toolbar.xml, list_row.xml, participants_list.xml,  	 )
		( profile_layout, LoginActivity, 				 )

An Activity	( MainActivity, activity_main.xml, event_list_view.xml		 ) is made by Sena Korkut and Naci Dalk�ran and is made by �a�lar �ankaya.
Fragment 	( FragmentCatogories                                             ) is made by Naci Dalk�ran and Ali Taha Din�er

		(SignUpActivity and StartActivity				 ) is made by Ali Taha Din�er and Tolga �atalp�nar

Ali Taha Din�er and Tolga �atalp�nar worked on DataBase(FireBase) for putting and taking Event and user.
Naci Dalk�ran, Sena Korkut and �a�lar �ankaya worked on connecting and entegrating model and Activities.
Also, the listeners is fixed by Naci Dalk�ran, Sena Korkut and �a�lar �ankaya.
Naci Dalkiran and Sena Korkut fixed and enhanced the models method and its logic.

//What we are going to
1-Profile Screen, 
2-Adding listeners to just a few buttons,
3-Adding images and button's icon,
4-E-mail - password processing, 
5-Login 
6-For enhancing the efficiency of some method. 
7-Look at some exceptions and fix it.

//Extra Features( If we have a time for preparing to make demo)
8-settings function, 
9-logout Function

//How to Code is orgabized
For no mistake and misunderstanding, every group member connect same Database(FireBase).
And also, generally, the group members work together and share their code instaneously.
We use Firebase core1601, Database core1601Auth, Codlin-stdlib-jdk7  1.2.71, MPAndroidChart 2.v3.1.0-alpha, okHttp 3.12.0, androidStudio 3.0, 
android SDK 28.0 (pie) libraries.
We add this libraries into build.gradle and app.gradle.


