package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;

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
    
}    