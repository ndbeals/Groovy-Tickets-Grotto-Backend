package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


import junit.framework.*;

public class RefundTest extends TestCase
{
    protected Session session;
    protected Refund refund;
    protected String buyer;
    protected String seller;
    protected float credit;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Adds a test user to delete
        User buyerUser = new User("admin           AA 001000.00");
        User sellerUser = new User("userFS          FS 901000.00");
        session.addUser(buyerUser);
        session.addUser(sellerUser);

        //Sets the transaction details
        buyer = "admin";
        seller = "userFS";
        credit = 1000.0f;
        
        refund = new Refund();
        refund.setTransactionString("05 admin           userFS          1000.00");
    }
    public void testRunTransaction(){
        float prevBuyerCredit = session.getUser(buyer).getBalance();
        float prevSellerCredit = session.getUser(seller).getBalance();
        
        refund.RunTransaction(session);

        float buyerCredit = session.getUser(buyer).getBalance();
        float sellerCredit = session.getUser(seller).getBalance();

        assertEquals(credit, prevSellerCredit-sellerCredit);
        assertEquals(credit, buyerCredit-prevBuyerCredit);
    }
    
}    