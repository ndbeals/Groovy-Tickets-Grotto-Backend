package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Delete extends Transaction{
    public Delete(Session session)
    {
        this.session = session;
    }
    public void runTransaction(){
        System.out.println("RUNNING DELETE");
    }
}