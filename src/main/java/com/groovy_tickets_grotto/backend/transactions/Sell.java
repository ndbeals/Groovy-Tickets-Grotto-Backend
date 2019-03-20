package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Sell - extension of Transaction
 * implements the functionality specific to the 'sell' transaction
 */
public class Sell extends Transaction
{
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
    */
    public void RunTransaction( Session session ){
        String eventName = ExtractEventTitle();
        String sellerName = ExtractUsername();
        int num = ExtractTicketAmount();
        float price = ExtractTicketPrice();

        Session.AddTicketBatch( new TicketBatch(eventName,sellerName,num,price) );
        
        
        // System.out.println("RUNNING SELL event: " + eventName + " seller: " + sellerName + " num: " +num +" price: " + price);
    }
}