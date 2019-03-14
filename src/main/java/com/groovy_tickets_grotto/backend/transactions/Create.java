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
        User newUser = new User( ExtractUsername(), ExtractUsertype(), ExtractCredit() );
        // Add newly created user to the map of all users.
        Session.addUser(newUser);
        // System.out.println("Ran Create!");
    }
}