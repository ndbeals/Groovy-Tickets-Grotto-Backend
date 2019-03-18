package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


import junit.framework.*;

public class CreateTest extends TestCase
{
    protected Session session;
    protected Create create;
    protected String username;
    protected float credit;
    protected String type;


    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Sets the transaction details
        username = "test";
        credit = 1000.0f;
        type = "FS";

        
        create = new Create();
        create.setTransactionString("01 test            FS 001000.00");
    }
    public void testRunTransaction(){
        int sizePrev = session.getUsers().size();
        
        create.RunTransaction(session);

        int size = session.getUsers().size();

        assertEquals(1, size-sizePrev);
    }
    
}    