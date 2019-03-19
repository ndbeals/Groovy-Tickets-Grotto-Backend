package com.groovy_tickets_grotto.backend;
import com.groovy_tickets_grotto.backend.transactions.*;


import junit.framework.*;

public class TransactionTest extends TestCase
{
    protected Session session;
    protected Transaction transaction;
    protected String transactionString;
    protected String className;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the session
        session = new Session();
    }
    public void testCreateTransaction(){
        //Decision Coverage for CreateTransaction
        transactionString = "00 admin           AA 001000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("EndSession", className);
        
        transactionString = "01 test            FS 001000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Create", className);

        transactionString = "02 userSS          FS 001000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Delete", className);

        transactionString = "03 tEve                      admin           5   016.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Sell", className);

        transactionString = "04 Test Event Title          admin           100 025.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Buy", className);

        transactionString = "05 admin           userFS          1000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Refund", className);

        transactionString = "06 admin           FS 002000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("AddCredit", className);
    }
    
}    