package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** AddCredit - class extension of Transaction
 * AddCredit implements the functionality specific to the 'addcredit' transaction
 */
public class AddCredit extends Transaction
{

    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction( Session session ){
        User user = Session.GetUserByName(ExtractUsername());
        ExtractUsertype();
        float credit = ExtractCredit();
		user.setBalance(user.getBalance()+credit);
        System.out.println("RUNNING ADDCREDIT");
    }
}