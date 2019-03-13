package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Sell - extension of Transaction
 * implements the functionality specific to the 'sell' transaction
 */
public class Sell extends Transaction
{
    /**
     * Constructor, set session member to the passed argument
     */
    public Sell(Session session)
    {
        this.session = session;
    }

    /** RunTransaction
     * runs the implementation specific functionality of this transaction
    */
    public void RunTransaction( Session session ){
        System.out.println("RUNNING SELL");
    }
}