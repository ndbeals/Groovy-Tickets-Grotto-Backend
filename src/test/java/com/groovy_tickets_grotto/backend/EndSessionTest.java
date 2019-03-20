package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;
import java.lang.reflect.*;
import java.util.*;
import org.junit.Test;
import junit.framework.*;


public class EndSessionTest extends TestCase
{
    protected Session session;
    protected User user;

    protected EndSession endSession;
    protected String userName;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Sets the transaction details
        userName = "admin";

        user = new User(userName, "AA", 1000.0f);
        Session.addUser(user);
    }
    
    @Test
    public void test_EndSessionTransaction()
    {
        endSession = new EndSession();
        endSession.setTransactionString("00 admin           AA 001000.00");
        
        endSession.RunTransaction(session);

        assertNotNull( session.getCurrentUser() );
        assertEquals( user , session.getCurrentUser() );
        assertEquals( userName , session.getCurrentUser().getUsername() );
    }
    
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