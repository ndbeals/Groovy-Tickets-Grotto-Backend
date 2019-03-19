package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** AddCredit - class extension of Transaction
 * AddCredit implements the functionality specific to the 'addcredit' transaction
 */
public class AddCredit extends Transaction
{
    String username;
    float credit;
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction( Session session ){
        //Gets the variables from the transaction string
        username = transactionString.substring(3, 18).trim();
        User user = session.getUser(username);
        String creditString = transactionString.substring(22, 31).trim().replaceFirst("^0+(?!$)", "");
        float credit = Float.parseFloat(creditString);

        user.setBalance(user.getBalance()+credit);
        
        System.out.println("RAN ADDCREDIT");
    }
}