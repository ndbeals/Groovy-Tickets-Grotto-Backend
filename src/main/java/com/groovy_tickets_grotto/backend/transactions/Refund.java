package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Refund - extension of Transaction
 * implements the functionality specific to the 'refund' transaction
 */
public class Refund extends Transaction
{
    /**
     * Constructor, set session member to the passed argument
     */
    public Refund(Session session)
    {
        this.session = session;
    }
    
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
    */
    public void RunTransaction( Session session ){
        System.out.println("RUNNING REFUND");
    }
}