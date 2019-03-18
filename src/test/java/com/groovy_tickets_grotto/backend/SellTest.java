package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


import junit.framework.*;

public class SellTest extends TestCase
{
    protected Session session;
    protected Sell sell;
    protected String seller;
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
        seller = "admin";
        eventName = "tEve";
        cost = 16.0f;
        num = 5;

        
        sell = new Sell();
        sell.setTransactionString("03 tEve                      admin           5   016.00");
    }
    public void testRunTransaction(){
        int sizePrev = session.getTickets().size();
        
        sell.RunTransaction(session);

        int size = session.getTickets().size();

        assertEquals(1, size-sizePrev);
    }
    
}    