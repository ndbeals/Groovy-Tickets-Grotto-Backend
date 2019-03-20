package com.groovy_tickets_grotto.backend;

import org.junit.Test;

import junit.framework.*;

public class TransactionTest extends TestCase
{
    // protected Session session;
    
    protected Transaction transaction;
    protected String transactionString;
    protected String className;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp()
    {
        //Sets up the session
        // session = new Session();
    }

    @Test
    public void test_Transaction_CreateTransaction(){
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

    @Test
    public void test_Transaction_ExtractMethods() {
        transactionString = "00 admin           AA 001234.56";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        assertEquals( 0 , transaction.getTransactionNumber() );
        assertEquals( "admin" , transaction.ExtractUsername() );
        assertEquals( User.UserType.AA.toString() , transaction.ExtractUsertype() );
        assertEquals( 1234.56f , transaction.ExtractCredit() );


        transactionString = "03 tEve                      admin           5   016.78";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        assertEquals( "tEve" , transaction.ExtractEventTitle() );
        assertEquals( "admin" , transaction.ExtractUsername() );
        assertEquals( 5 , transaction.ExtractTicketAmount() );
        assertEquals( 16.78f, transaction.ExtractTicketPrice() );
    }
    
}    