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
    public void RunTransaction( Session session )
    {
        User buyer = Session.GetUserByName( ExtractUsername() );
        User seller = Session.GetUserByName( ExtractUsername() );
        float refundAmount = ExtractCredit();

        System.out.println("PRE: " + buyer + "   " + seller + "  " + refundAmount);
        
        buyer.setBalance( buyer.getBalance() + refundAmount );
        seller.setBalance( seller.getBalance() - refundAmount );
        System.out.println("POST: " + buyer + "   " + seller + "  " + refundAmount);
        
        System.out.println("RAN REFUND");
    }
}