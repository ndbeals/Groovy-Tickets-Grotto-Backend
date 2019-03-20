package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;

import org.junit.Test;

import junit.framework.TestCase;

public class CreateTest extends TestCase
{
    protected Session session;
    protected Create create;

    protected String username;
    protected float credit;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Sets the transaction details
        username = "test";
        credit = 123456.78f;
    }
    
    @Test
    public void test_CreateTransaction(){
        create = new Create();
        create.setTransactionString("01 test            FS 123456.78");
    
        create.RunTransaction(session);

        assertNotNull( Session.GetUserByName(username) );

        User user = Session.GetUserByName(username);
        assertEquals( username , user.getUsername() );
        assertEquals( User.UserType.FS , user.getType() );
        assertEquals( credit , user.getBalance() );
        }
    
}    