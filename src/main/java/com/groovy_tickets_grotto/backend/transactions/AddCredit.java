package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** AddCredit - class extension of Transaction
 * AddCredit implements the functionality specific to the 'addcredit' transaction
 */
public class AddCredit extends Transaction
{
    /**
     * Constructor, set session member to the passed argument
     */
    public AddCredit(Session session)
    {
        this.session = session;
    }

    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction(){
        System.out.println("RUNNING ADDCREDIT");
    }
}