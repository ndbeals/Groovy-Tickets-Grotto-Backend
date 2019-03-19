// package com.groovy_tickets_grotto.backend;
// import com.groovy_tickets_grotto.backend.transactions.*;


// import junit.framework.*;

// public class SessionTest extends TestCase
// {
//     protected Session session;
//     protected User testUser1;
//     protected User testUser2;
//     protected TicketBatch testTicketBatch1;
//     protected TicketBatch testTicketBatch2;


//     /** setUp
//      * Called before each test defined below is ran, this means that each test also tests what's in here.
//      */
//     public void setUp() {
//         //Sets up the session
//         session = new Session();

//         //Sets some users
//         testUser1 = new User("admin           AA 001000.00");
//         testUser2 = new User("userFS          FS 901000.00");

//         //Sets some tickbatches
//         testTicketBatch1 = new TicketBatch("Test Event Title          admin           100 025.00");
//         testTicketBatch2 = new TicketBatch("Test Event Title          userSS          100 025.00");
//     }
//     public void testFunctions(){
//         // session.setCurrentUser(testUser1);
//         // assertEquals(testUser1.getUsername(), session.getCurrentUser().getUsername());

//         // int size = session.getUsers().size();
//         // session.addUser(testUser1);
//         // session.addUser(testUser2);
//         // assertEquals(size, session.getUsers().size());
//         // assertEquals(session.getUser(testUser1.getUsername()).getBalance(), testUser1.getBalance());

//         // size = session.getTickets().size();
//         // session.addTicketBatch(testTicketBatch1);
//         // session.addTicketBatch(testTicketBatch2);
//         // assertEquals(size+2, session.getUsers().size());
//         // assertEquals(session.getTicketBatch(testTicketBatch1.getEventName()+testTicketBatch1.getSeller()).getEventName(), testTicketBatch1.getEventName());
//     }
// }    