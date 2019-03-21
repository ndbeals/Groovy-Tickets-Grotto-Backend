package com.groovy_tickets_grotto.backend;

import org.junit.Test;

import junit.framework.*;

public class TransactionTest extends TestCase
{
    // protected Session session;
    
    protected Transaction transaction;
    protected String transactionString;
    protected String className;

    @Test
    public void test_Transaction_CreateEndSession()
    {
        //Decision Coverage for CreateTransaction
        transactionString = "00 admin           AA 001000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("EndSession", className);
    } 

    @Test
    public void test_Transaction_CreateCreateTransaction()
    {
        transactionString = "01 test_Transaction_            FS 001000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Create", className);
    }

    @Test
    public void test_Transaction_CreateDeleteTransaction()
    {
        transactionString = "02 userSS          FS 001000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Delete", className);
    }   

    @Test
    public void test_Transaction_CreateSellTransaction()
    {
        transactionString = "03 tEve                      admin           5   016.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Sell", className);
    }   

    @Test
    public void test_Transaction_CreateBuyTransaction()
    {
        transactionString = "04 Test Event Title          admin           100 025.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Buy", className);
    }

    @Test
    public void test_Transaction_CreateRefundTransaction()
    {
        transactionString = "05 admin           userFS          1000.00";
        transaction = Transaction.CreateTransactionFromString(transactionString);
        className = transaction.getClass().getSimpleName();
        assertEquals("Refund", className);
    }   

    @Test
    public void test_Transaction_CreateAddCreditTransaction()
    {
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