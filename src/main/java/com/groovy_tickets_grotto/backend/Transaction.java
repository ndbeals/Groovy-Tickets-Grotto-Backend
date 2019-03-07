package com.groovy_tickets_grotto.backend;
import java.util.*;
import com.groovy_tickets_grotto.backend.transactions.*;

public abstract class Transaction
{   
    private User CurrentUser;
    
    public abstract void runTransaction( Session sess );
    public static Transaction parse(String transaction)
    {   
        Transaction t;
        String transactionCode = transaction.substring(0, 2);
        if (transactionCode.equals("00"))
        {
            t = new Delete();
        }else if (transactionCode.equals("01"))
        {
            t = new Create();
        } else if (transactionCode.equals("02"))
        {
            t = new Delete();
        }else if (transactionCode.equals("03"))
        {
            t = new Sell();
        }else if (transactionCode.equals("04"))
        {
            t = new Buy();
        }else if (transactionCode.equals("05"))
        {
            t = new Refund();
        }else if (transactionCode.equals("06"))
        {
            t = new AddCredit();
        }else
        {
            t = null;
        }
        return t;
    }
}