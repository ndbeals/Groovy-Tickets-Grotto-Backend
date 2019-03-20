package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;

import org.junit.Test;

import junit.framework.TestCase;

public class RefundTest extends TestCase
{
    protected Session session;
    protected Refund refund;
    protected String buyerName;
    protected String sellerName;
    protected float credit;
    protected User buyer;
    protected User seller;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();

        //Sets the transaction details
        buyerName = "admin";
        sellerName = "userFS";
        credit = 1000.0f;
        
        //Adds a test user to delete
        buyer = new User(buyerName,"AA",credit);
        seller = new User(sellerName,"FS",credit);
        Session.addUser(buyer);
        Session.addUser(seller);
    }
    
    @Test
    public void test_RefundTransaction()
    {
        buyer = Session.GetUserByName("admin");
        refund = new Refund();
        refund.setTransactionString("05 admin           userFS          001000.00");

        float prevBuyerCredit = buyer.getBalance();
        float prevSellerCredit = seller.getBalance();
        
        refund.RunTransaction(session);
        
        float buyerCredit = buyer.getBalance();
        float sellerCredit = seller.getBalance();

        assertEquals(credit, buyerCredit-prevBuyerCredit);
        assertEquals(credit, prevSellerCredit-sellerCredit);
    }
    
}    