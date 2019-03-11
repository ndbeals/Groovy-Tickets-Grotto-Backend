package com.groovy_tickets_grotto.backend;
import java.util.*;
import com.groovy_tickets_grotto.backend.transactions.*;

/** Transaction
 * Parent class for all the actual transaction implementations
 */
public abstract class Transaction
{   
    protected Session session;
    protected String transactionString;
    

    /**
     * Runs the specific actions related to each transaction ie deleting users from map
     * adding new users to map, subtracting balances etc
     */
    public abstract void RunTransaction( );

    /**
     * Returns the proper subclass of Transaction depending on the code entered
     */
    public static Transaction CreateTransactionFromString(String transaction, Session session)
    {   
        Transaction t;
        String transactionCode = transaction.substring(0, 2);

        if (transactionCode.equals("00"))
        {
            t = new Delete(session , transaction );
        }
        else if ( transactionCode.equals("01") )
        {
            t = new Create(session);
        }
        else if (transactionCode.equals("02"))
        {
            t = new Delete(session);
        }
        else if (transactionCode.equals("03"))
        {
            t = new Sell(session);
        }
        else if (transactionCode.equals("04"))
        {
            t = new Buy(session);
        }
        else if (transactionCode.equals("05"))
        {
            t = new Refund(session);
        }
        else if (transactionCode.equals("06"))
        {
            t = new AddCredit(session);
        }
        else
        {
            t = null;
        }

        return t;
    }
}