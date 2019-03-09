package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Delete extends Transaction{
    public Delete(Session session)
    {
        this.session = session;
    }

    public Delete( Session session , String transactionString )
    {
        this.session = session;
        this.transactionString = transactionString;
    }

    public void runTransaction(){
        System.out.println("RUNNING DELETE");
    }
}