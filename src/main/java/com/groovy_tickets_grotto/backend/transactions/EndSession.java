package com.groovy_tickets_grotto.backend.transactions;

import com.groovy_tickets_grotto.backend.*;

/** EndSession - extension of Transaction
 * implements the functionality specific to the 'endsession' transaction
 */
public class EndSession extends Transaction
{
    /** RunTransaction
     * runs the implementation specific functionality of this transaction
     */
    public void RunTransaction( Session session ){
        // EndSession a new User, passing the extracted name, type, and credit from the transaction string.
        User user = Session.GetUserByName( ExtractUsername() );
        // set the sessions user to the user supplied by this transaction
        session.setCurrentUser(user);
    }
}