package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Refund extends Transaction{
    public Refund(Session session)
    {
        this.session = session;
    }
    public void runTransaction(){
        System.out.println("RUNNING REFUND");
    }
}