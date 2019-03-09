package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Buy extends Transaction{
    public Buy(Session session)
    {
        this.session = session;
    }
    public void runTransaction(){
        System.out.println("RUNNING BUY");
    }
}