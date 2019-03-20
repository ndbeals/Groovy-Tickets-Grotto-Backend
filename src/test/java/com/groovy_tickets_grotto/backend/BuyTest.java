package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


import junit.framework.*;

public class BuyTest extends TestCase
{
    protected Session session;
    protected Buy buy;
    protected String seller;
    protected String eventName;
    protected float price;
    protected float totalCost;
    protected int num;
    protected User userBuyer;
    protected User userSeller;
    protected TicketBatch ticketBatch;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp()
    {
        //Sets the transaction details
        seller = "admin";
        eventName = "Test Event Title";
        price = 25.0f;
        num = 100;
        totalCost = price*num;
        
        
        //Sets up the invloved users
        userBuyer = new User("userFS", "FS", totalCost);
        userSeller = new User(seller, "AA", 0);
        

        //Sets up the session
        session = new Session();
        Session.addUser( userBuyer );
        Session.addUser( userSeller );
        session.setCurrentUser(userBuyer);
        
        
        //Sets up the involved tickets
        ticketBatch = new TicketBatch(eventName, seller , num, price);
        Session.addTicketBatch(ticketBatch);
        
        buy = new Buy();
        buy.setTransactionString("04 Test Event Title          admin           100 025.00");
    }


    public void test_BuyTransaction()
    {
        //Gets values for both balances and number of tickets in event
        float prevCreditSeller = userSeller.getBalance();
        float prevCreditBuyer = userBuyer.getBalance();
        int prevNumTickets = ticketBatch.getAmountAvailable();
        System.out.println("prev: " + userSeller.getBalance());
        
        buy.RunTransaction(session);

        //Gets values for both balances and number of tickets in event
        float creditSeller = userSeller.getBalance();
        float creditBuyer = userBuyer.getBalance();
        int numTickets = ticketBatch.getAmountAvailable();

        System.out.println("post: " + userSeller.getBalance());

        assertEquals(price*num, creditSeller-prevCreditSeller);
        assertEquals(-1.0f*(price*num), creditBuyer-prevCreditBuyer);
        assertEquals(num, prevNumTickets-numTickets);
    }
    
}    