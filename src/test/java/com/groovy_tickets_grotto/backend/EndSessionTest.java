package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;

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
    
}    