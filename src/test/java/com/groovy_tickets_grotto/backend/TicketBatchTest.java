package com.groovy_tickets_grotto.backend;
import org.junit.Test;

import junit.framework.*;

public class TicketBatchTest extends TestCase
{
    protected Session session;

    protected TicketBatch ticketBatch;
    protected String fileLine;
    protected TicketBatch fTicketBatch;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        //Sets up the invloved users
        User user = new User("New seller", "FS", 0);
        User admin = new User("admin", "AA", 0);
        
        //Sets up the session
        session = new Session();
        Session.addUser( user );
        Session.addUser( admin );
        // session.setCurrentUser(userBuyer);
        

        ticketBatch = new TicketBatch("Test Event Title", "admin", 100, 25.0f);
    }

    @Test
    public void test_TicketBatch_FileConstructor() {
        fileLine = "Test Event Title          admin           100 025.00";
        fTicketBatch = new TicketBatch(fileLine);
    }

    @Test
    public void test_TicketBatch_ToString() {
        fileLine = "Test Event Title          admin           100 025.00";
        fTicketBatch = new TicketBatch(fileLine);
        
        assertEquals( fileLine, fTicketBatch.toString() );
        assertEquals( "Test Event Title          admin           100 025.00", ticketBatch.toString() );
    }

    @Test
    public void test_TicketBatch_Attributes() {
        assertEquals( "Test Event Title" , ticketBatch.getEventName() );
        assertEquals( 25.0f , ticketBatch.getCost() );
        assertEquals( 100 , ticketBatch.getAmountAvailable() );
        assertEquals( "admin" , ticketBatch.getSeller().getUsername() );


        ticketBatch.setAmountAvailable(110);
        assertEquals( 110 , ticketBatch.getAmountAvailable());
        ticketBatch.setAmountAvailable("120");
        assertEquals( 120 , ticketBatch.getAmountAvailable());
        ticketBatch.setCost("26.00");
        assertEquals( 26.00f , ticketBatch.getCost());
        ticketBatch.setCost(27.00f);
        assertEquals( 27.00f , ticketBatch.getCost());
        ticketBatch.setEventName("New name");
        assertEquals( "New name" , ticketBatch.getEventName() );
        ticketBatch.setSeller("New seller");
        assertEquals( "New seller" , ticketBatch.getSeller().getUsername() );
    }
}    