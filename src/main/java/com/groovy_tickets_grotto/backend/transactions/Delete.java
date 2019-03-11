package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Delete - extension of Transaction
 * implements the functionality specific to the 'delete' transaction
 */
public class Delete extends Transaction
{
    /**
     * Constructor, set session member to the passed argument
     */
    public Delete(Session session)
    {
        this.session = session;
    }

    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction(){
        System.out.println("RUNNING DELETE");
    }
}