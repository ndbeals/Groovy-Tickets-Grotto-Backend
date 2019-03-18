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
        String eventName = transactionString.substring(3, 28).trim();
        String sellerName = transactionString.substring(29, 44).trim();

        //Removes leading 0s whitespace etc
        String numString = transactionString.substring(45, 48).trim().replaceFirst("^0+(?!$)", "");
        String priceString = transactionString.substring(49, 55).trim().replaceFirst("^0+(?!$)", "");
        int num = Integer.parseInt(numString);
        float price = Float.parseFloat(priceString);
        
        TicketBatch currTicket = session.getTicketBatch(eventName+sellerName);
        currTicket.setAmountAvailable(currTicket.getAmountAvailable()-num); //Might need to change this depending on implementation

        // //Subtracts money from buyer
        User loggedIn = session.getUser(session.getCurrentUser().getUsername());
        loggedIn.setBalance(loggedIn.getBalance()-(price*num));
        

        // //Adds money to seller
        User seller = session.getUser(sellerName);
        seller.setBalance(seller.getBalance()+(price*num));;
        

        System.out.println("RUNNING BUY");
    }
}