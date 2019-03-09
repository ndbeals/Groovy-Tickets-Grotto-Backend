package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Create extends Transaction{
    public Create(Session session)
    {
        this.session = session;
    }
    public void runTransaction(){
        System.out.println("RUNNING CREATE");
    }
}