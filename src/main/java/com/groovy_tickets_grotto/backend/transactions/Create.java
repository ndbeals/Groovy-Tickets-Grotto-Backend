package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Create - extension of Transaction
 * implements the functionality specific to the 'create' transaction
 */
public class Create extends Transaction
{
    /**
     * Constructor, set session member to the passed argument
     */
    public Create(Session session)
    {
        this.session = session;
    }

    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction(){
        System.out.println("RUNNING CREATE");
    }
}