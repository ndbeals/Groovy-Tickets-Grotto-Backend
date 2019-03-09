package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Sell extends Transaction{
    public Sell(Session session)
    {
        this.session = session;
    }
    public void runTransaction(){
        System.out.println("RUNNING SELL");
    }
}