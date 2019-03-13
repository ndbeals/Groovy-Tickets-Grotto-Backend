package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Buy - extension of Transaction
 * implements functionality specific to the 'buy' transaction
 */
public class Buy extends Transaction
{
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction( Session session ){
        System.out.println("RUNNING BUY");
    }
}