package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.Test;

import junit.framework.TestCase;


public class DeleteTest extends TestCase
{
    protected Session session;
    protected Delete delete;
    protected String username;
    protected float credit;
    protected String type;


    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Adds a test user to delete
        User testUser = new User("userFS          FS 901000.00");
        Session.addUser(testUser);

        //Sets the transaction details
        username = "test";
        credit = 1000.0f;
        type = "FS";
    }
    
    @Test
    public void test_DeleteTransaction()
    {
        delete = new Delete();
        delete.setTransactionString("02 userFS          FS 901000.00");

        delete.RunTransaction(session);

        assertNull( Session.GetUserByName(username) );
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