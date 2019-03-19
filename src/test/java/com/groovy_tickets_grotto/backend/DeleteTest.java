package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


import junit.framework.*;

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
        session.addUser(testUser);

        //Sets the transaction details
        username = "test";
        credit = 1000.0f;
        type = "FS";
        
        delete = new Delete();
        delete.setTransactionString("02 userFS          FS 901000.00");
    }
    public void testRunTransaction(){
        int sizePrev = session.getUsers().size();
        
        delete.RunTransaction(session);

        int size = session.getUsers().size();

        assertEquals(-1, size-sizePrev);
    }
    
}    