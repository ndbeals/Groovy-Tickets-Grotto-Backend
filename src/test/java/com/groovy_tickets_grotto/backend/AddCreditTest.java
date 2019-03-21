package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.Test;

import junit.framework.*;

public class AddCreditTest extends TestCase
{
    protected Session session;
    protected User user;
    protected AddCredit addCredit;
    protected float credit;

    // protected String username;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        session = new Session();

        // //Adds the involved users
        User user = new User( "admin", "AA", 1000.0f );
        Session.addUser(user);

        credit = 2000.0f;
    }

    @Test
    public void test_AddCreditTransaction(){
        User user = Session.GetUserByName( "admin" );

        addCredit = new AddCredit();
        addCredit.setTransactionString("06 admin           FS 002000.00");

        float previousCredit = user.getBalance();
        
        addCredit.RunTransaction(session);
        
        float newCredit = user.getBalance();
        
        assertEquals(credit, newCredit-previousCredit);
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