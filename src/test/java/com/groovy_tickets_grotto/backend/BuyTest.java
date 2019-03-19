// package com.groovy_tickets_grotto.backend;
// import com.groovy_tickets_grotto.backend.transactions.*;


// import junit.framework.*;

// public class BuyTest extends TestCase
// {
//     protected Session session;
//     protected Buy buy;
//     protected String seller;
//     protected String eventName;
//     protected float cost;
//     protected int num;
//     protected User userBuyer;
//     protected User userSeller;
//     protected TicketBatch ticketBatch;

//     /** setUp
//      * Called before each test defined below is ran, this means that each test also tests what's in here.
//      */
//     public void setUp() {
//         //Sets up the session
//         session = new Session();

//         //Sets up the invloved users
//         userBuyer = new User("userFS          FS 001000.00");
//         userSeller = new User("admin           AA 001000.00");
//         session.addUser(userBuyer);
//         session.addUser(userSeller);
        
//         //Sets up the involved tickets
//         ticketBatch = new TicketBatch("Test Event Title          admin           100 025.00");
//         session.addTicketBatch(ticketBatch);

//         //Sets the transaction details
//         seller = "admin";
//         eventName = "Test Event Title";
//         cost = 25.0f;
//         num = 100;

//         session.setCurrentUser(userBuyer);
        
//         buy = new Buy();
//         buy.setTransactionString("04 Test Event Title          admin           100 025.00");
//     }
//     public void testRunTransaction(){
//         //Gets values for both balances and number of tickets in event
//         float prevCreditSeller =session.getUser(seller).getBalance();
//         float prevCreditBuyer =session.getCurrentUser().getBalance();
//         int prevNumTickets = session.getTicketBatch(eventName + seller).getAmountAvailable();
        
//         buy.RunTransaction(session);

//         //Gets values for both balances and number of tickets in event
//         float creditSeller =session.getUser(seller).getBalance();
//         float creditBuyer =session.getCurrentUser().getBalance();
//         int numTickets = session.getTicketBatch(eventName + seller).getAmountAvailable();

//         assertEquals(cost*num, creditSeller-prevCreditSeller);
//         assertEquals(-1.0f*(cost*num), creditBuyer-prevCreditBuyer);
//         assertEquals(num, prevNumTickets-numTickets);
//     }
    
// }    