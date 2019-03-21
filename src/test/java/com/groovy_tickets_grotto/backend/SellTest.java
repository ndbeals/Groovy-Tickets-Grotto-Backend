package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.Test;
import junit.framework.*;


public class SellTest extends TestCase
{
    protected Session session;
    protected User seller;

    protected Sell sell;
    protected String sellerName;
    protected String eventName;
    protected float cost;
    protected int num;


    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Sets the transaction details
        sellerName = "admin";
        eventName = "tEve";
        cost = 16.0f;
        num = 5;

        seller = new User(sellerName, "AA", 0.0f);
        Session.addUser(seller);
        session.setCurrentUser(seller);
    }
    
    @Test
    public void test_SellTransaction()
    {
        sell = new Sell();
        sell.setTransactionString("03 tEve                      admin           5   016.00");
        
        sell.RunTransaction(session);

        assertNotNull( Session.getTicketBatch(eventName+sellerName) );
        assertEquals( eventName , Session.getTicketBatch(eventName+sellerName).getEventName() );
        assertEquals( sellerName , Session.getTicketBatch(eventName+sellerName).getSeller().getUsername() );
        assertEquals( num , Session.getTicketBatch(eventName+sellerName).getAmountAvailable() );
        assertEquals( cost , Session.getTicketBatch(eventName+sellerName).getCost() );
    }
    
    /**
     * The following code is to access and clean up private variables stored within the Session class. 
     *  using reflection, i get a reference to the private field i want to access, set it's accessibility to true, then poll that field for the data
     *  then I cast that data from an 'Object' back to the map i know it is. 
     * Then the map gets cleared.
     */    
    @SuppressWarnings("unchecked")
    public void tearDown()
    {
        try {
            Field field1 = Session.class.getDeclaredField("Users");
            field1.setAccessible(true);
            
            Map<String,User> Users = (Map<String,User>)field1.get(Session.class);
    
            Users.clear();


            Field field2 = Session.class.getDeclaredField("Tickets");
            field2.setAccessible(true);

            Map<String, TicketBatch> Tickets = (Map<String, TicketBatch>)field2.get(Session.class);
    
            Tickets.clear();
        }
        catch (Exception e) {
            assertNotNull(e);
        }
    }
}    