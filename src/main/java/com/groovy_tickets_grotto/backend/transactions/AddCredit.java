package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class AddCredit extends Transaction{
    public AddCredit(Session session)
    {
        this.session = session;
    }
    public void runTransaction(){
        System.out.println("RUNNING ADDCREDIT");
    }
}