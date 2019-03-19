// package com.groovy_tickets_grotto.backend;
// import com.groovy_tickets_grotto.backend.transactions.*;


// import junit.framework.*;

// public class AddCreditTest extends TestCase
// {
//     protected Session session;
//     protected User user;
//     protected AddCredit addCredit;
//     protected String username;
//     protected float credit;

//     /** setUp
//      * Called before each test defined below is ran, this means that each test also tests what's in here.
//      */
//     public void setUp() {
//         session = new Session();

//         //Adds the involved users
//         user = new User("admin           AA 001000.00");

//         username = "admin";
//         credit = 2000.0f;
//         session.addUser(user);

//         addCredit = new AddCredit();
//         addCredit.setTransactionString("06 admin           FS 002000.00");
//     }
//     public void testRunTransaction(){
//         float previousCredit =session.getUsers().get(username).getBalance();
//         addCredit.RunTransaction(session);
//         float newCredit =session.getUsers().get(username).getBalance();
//         assertEquals(credit, newCredit-previousCredit);
//     }
    
// }    