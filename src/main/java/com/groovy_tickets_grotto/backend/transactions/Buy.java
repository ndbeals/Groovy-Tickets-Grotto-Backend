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
        String eventName = ExtractEventTitle();
        String sellerName = ExtractUsername();
        int num = ExtractTicketAmount();
        float price = ExtractTicketPrice();

        
        TicketBatch currTicket = Session.getTicketBatchByName(eventName+sellerName);
        currTicket.setAmountAvailable(currTicket.getAmountAvailable()-num); //Might need to change this depending on implementation

        //Subtracts money from buyer
        User loggedIn = session.getCurrentUser();
        loggedIn.setBalance(loggedIn.getBalance()-(price*num));
        

        //Adds money to seller
        User seller = Session.GetUserByName(sellerName);
        seller.setBalance(seller.getBalance()+(price*num));;
        

        System.out.println("RUNNING BUY");
    }
}