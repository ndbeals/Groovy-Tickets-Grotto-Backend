package com.groovy_tickets_grotto.backend;
import java.util.*;
import com.groovy_tickets_grotto.backend.transactions.*;

public abstract class Transaction
{   
    protected User CurrentUser;
    protected Session session;
    
    public abstract void runTransaction( );
    public static Transaction parse(String transaction, Session session)
    {   
        Transaction t;
        String transactionCode = transaction.substring(0, 2);
        if (transactionCode.equals("00"))
        {
            t = new Delete(session);
        }else if (transactionCode.equals("01"))
        {
            t = new Create(session);
        } else if (transactionCode.equals("02"))
        {
            t = new Delete(session);
        }else if (transactionCode.equals("03"))
        {
            t = new Sell(session);
        }else if (transactionCode.equals("04"))
        {
            t = new Buy(session);
        }else if (transactionCode.equals("05"))
        {
            t = new Refund(session);
        }else if (transactionCode.equals("06"))
        {
            t = new AddCredit(session);
        }else
        {
            t = null;
        }
        return t;
    }
}