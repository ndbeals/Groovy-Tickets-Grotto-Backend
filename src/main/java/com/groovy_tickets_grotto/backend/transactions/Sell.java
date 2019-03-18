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
        String eventName = transactionString.substring(3, 28).trim();
        String sellerName = transactionString.substring(29, 42).trim();

        //Removes leading 0s whitespace etc
        String numString = transactionString.substring(43, 46).trim().replaceFirst("^0+(?!$)", "");
        String priceString = transactionString.substring(49, 55).trim().replaceFirst("^0+(?!$)", "");
        int num = Integer.parseInt(numString);
        float price = Float.parseFloat(priceString);

        session.getTickets().put(eventName+sellerName, new TicketBatch(eventName, sellerName, num, price) );
        
        System.out.println("RAN SELL");
    }
}