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
    public void RunTransaction( Session session )
    {
        String eventName = ExtractEventTitle();
        String sellerName = ExtractUsername();

        TicketBatch batch = Session.getTicketBatch( eventName + sellerName );
        
        int amount = ExtractTicketAmount();
        float price = batch.getCost();// ExtractTicketPrice();
        
        
        User seller = batch.getSeller();
        User buyer = session.getCurrentUser();

        float cost = amount*price;
        System.out.println("pre cred: " + buyer.getBalance() + "  pr: " + seller.getBalance());

        seller.setBalance( seller.getBalance() + cost );
        batch.setAmountAvailable( batch.getAmountAvailable() - amount );
        buyer.setBalance( buyer.getBalance() - cost );

        if ( batch.getAmountAvailable() < 1 ) {
            Session.RemoveTicketBatch( batch );
        }
        
        System.out.println("post cred: " + buyer.getBalance() + "  po: " + seller.getBalance());

        System.out.println("RUNNING BUY  " + Session.GetUsers() + " \n\n");
    }
}