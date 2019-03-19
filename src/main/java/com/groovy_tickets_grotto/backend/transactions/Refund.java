package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Refund - extension of Transaction
 * implements the functionality specific to the 'refund' transaction
 */
public class Refund extends Transaction
{
    
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
    */
    public void RunTransaction( Session session ){
        String buyerUsername = transactionString.substring(3, 18).trim();
        String sellerUsername = transactionString.substring(19, 34).trim();
        String creditString = transactionString.substring(35, 42).trim().replaceFirst("^0+(?!$)", "");
        float credit = Float.parseFloat(creditString);

        User buyer = session.getUsers().get(buyerUsername);
        User seller = session.getUsers().get(sellerUsername);
        buyer.setBalance(buyer.getBalance()+credit);
        seller.setBalance(seller.getBalance()-credit);

        System.out.println("RAN REFUND");
    }
}