package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

public class Buy extends Transaction{

    public void runTransaction( Session sess ){
        System.out.println("RUNNING BUY");
    }
}