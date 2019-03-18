package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** Create - extension of Transaction
 * implements the functionality specific to the 'create' transaction
 */
public class Create extends Transaction
{
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction( Session session ){
        // Create a new User, passing the extracted name, type, and credit from the transaction string.
        String username = transactionString.substring(3, 18).trim();
        String type = transactionString.substring(19, 21);
        float credit = Float.parseFloat(transactionString.substring(22, 31));

        User newUser = new User( username, type, credit );
        // // Add newly created user to the map of all users.
        session.getUsers().put(username, newUser);
        
        System.out.println("Ran Create!");
    }
}