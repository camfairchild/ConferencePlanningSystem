# Conference Planning System

Convention Planning System is a program that facilitates the organization of events 
like conferences, that include attendees, speakers, and organizers. All users must
 have an account in order to make use of the functionalities of this system. 
 Through use of these accounts, users will be able to interact with a role-specific 
 menu that will allow them to accomplish a lot, including the organization, scheduling and 
 registration of events, as well as the communication between users.

#### Conference Planning System requires the following to run:

    jdk 8

## Installation

After cloning this repository, make sure phase1/ConventionPlanningProject/src is 
marked as the source root.

You may also need to mark the test folder in ConventionPlanningProject as excluded,
 if the program fails to compile on your first try.

To run the program, run the Main class in the default package of the src folder.

## Notes about Program

 ### Added Features
 
 #### Mandatory
 
 1. Different Types of Events
 
    A one-speaker event is the same as a "talk" 
    from Phase 1. You can have multi-speaker events, like a panel discussion, and no-speaker 
    events, like a party. Events can last as long as you want. You can measure the duration 
    of an event in hours, or minutes. You get to decide.
    
 2. Canceling Events
 
    Events can be canceled by at least one organizer.
    
 3. More Users
 
     At least one more type of user will be included in your program. For example, an Admin
     user who can delete messages or events with no attendees, or a VIP user who can access 
     VIP-only events.
     
 4. Organizers creating Accounts
 
    Organizers can also create Speaker accounts, Attendee accounts, and any other type of 
    user accounts that are in your program.
    
 5. Prevention from Exceeding Room Capacity
 
    Each event has a maximum number of people who can attend it. This amount can be set 
    when the event is created and also changed later, by an organizer. Your program should 
    check the maximum capacity for the room where the event will be held, and prevent the 
    number of attendees from going above the room's capacity.
 
 #### Optional
 
 1. User Requests
 
    All users are able to view their requests and its status. They would also be able
    to add or remove requests. ALl organizers are able view the requests with the option
    of organizing said requests based on the category or the status. They are also able
    to update the status of a request once they address it. 
    
 2. Enhancing User Messaging Experience
    
    Provided users with the ability to mark messages as unread.
 
 3. Schedule Formatting
 
 4. Logging in and out
    
    Users are able to log in and log out **without** the program terminating.
    
 5. Users can login using their Google account through the OAuth2 standard.   
    Note: Users' Google account must be added to Google API Console first.
    
## Improvements Since Phase 1

   Seeing as how there weren't any design issues in Phase 1 we were able to add to the program
   rather than modify these initial design choices. Some issues that were brought up in the evaluation 
   had to do with clarity in terms of naming. We attempted to keep that in mind while 
   throughout this section. One such instance was when the UserRequestCategory was
   originally named UserRequestType, which is not nearly as clear. We added Javadoc to all 
   public methods, including those that were overridden, to ensure that the functionality of each
   of these methods were clear. 

    
## Testing Help
When creating accounts/events/messages, information that you could use is provided
 for ease of testing. However, you are free to put anything you want while following the prompts)

1. Create an organizer
    (role: ORGANIZER, username: organ1, password: P@ssw0rd)

2. log in as organizer, entering in username and password

3. Select option to create a speaker account, and create a speaker account
        (username: speak1, password: P@ssw0rd)

4. Create a room
        (roomid:1, start time: 09:00, end time: 17:00)

5. View list of Rooms

6. Schedule Event
        (title: Coding101, start time: 12:30, end time 13:30, speaker: speak1, roomid: 1)

7. View list of Events
    1. View more information about the event you just created
    2. Sign yourself up for the event

8. View My Events (see the events you've signed up for)
    1. Go back to view list of events menu
    2. view more information again (don't sign up for the event again)
    3. Cancel the event
    
9. For messaging
    1. Click on the user you would like to message
    2. type the message in the "send message" prompt on the bottom right of the screen (hello there)
    3. To mark a message as unread, click on the message and then click the "Mark Unread" button. Once clicked, 
     this will mark the message as unread and clear the messages column so that you are ready to click on another user 
     to message.
10. Printing a schedule
    1. to print the schedule to a txt file, you can first sort the events in all events, then to print it, click the 
     Schedule button on the bottom, then click the print button. This creates a txt file that can be opened by the user.
## Authors
Cameron Fairchild (Me), 
Petra Fayad, 
Safa Hussein, 
Ritvik Juttukonda, 
Ha Young Kim, 
Seohyun Park, 
Tian Sirchich, 
[Johanna Zhang](https://github.com/johannazhang)

