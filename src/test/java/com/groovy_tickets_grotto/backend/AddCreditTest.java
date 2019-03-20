package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


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
    public void testRunTransaction(){
        User user = Session.GetUserByName( "admin" );

        addCredit = new AddCredit();
        addCredit.setTransactionString("06 admin           FS 002000.00");

        float previousCredit = user.getBalance();
        
        addCredit.RunTransaction(session);
        
        float newCredit = user.getBalance();
        
        assertEquals(credit, newCredit-previousCredit);
    }
    
}    