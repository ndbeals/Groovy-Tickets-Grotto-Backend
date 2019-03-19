// package com.groovy_tickets_grotto.backend;
// import junit.framework.*;

// public class TicketBatchTest extends TestCase
// {
//     protected TicketBatch ticketBatch;
//     protected String fileLine;
//     protected TicketBatch fTicketBatch;

//     /** setUp
//      * Called before each test defined below is ran, this means that each test also tests what's in here.
//      */
//     public void setUp() {
//         ticketBatch = new TicketBatch("Test Event Title", "admin", 100, 25.0f);
//         fileLine = "Test Event Title          admin           100 025.00";
//         fTicketBatch = new TicketBatch(fileLine);
//     }
//     public void testToString() {
//         //System.out.println(ticketBatch.toString());
//         assertEquals( fileLine, fTicketBatch.toString());
//         assertEquals( "Test Event Title          admin           100 025.00", ticketBatch.toString() );
//     }
//     public void testAttributes() {
//         assertEquals( "Test Event Title" , ticketBatch.getEventName() );
//         assertEquals( 25.0f , ticketBatch.getCost() );
//         assertEquals( 100 , ticketBatch.getAmountAvailable() );
//         assertEquals( "admin" , ticketBatch.getSeller() );


//         ticketBatch.setAmountAvailable(110);
//         assertEquals( 110 , ticketBatch.getAmountAvailable());
//         ticketBatch.setAmountAvailable("120");
//         assertEquals( 120 , ticketBatch.getAmountAvailable());
//         ticketBatch.setCost("26.00");
//         assertEquals( 26.00f , ticketBatch.getCost());
//         ticketBatch.setCost(27.00f);
//         assertEquals( 27.00f , ticketBatch.getCost());
//         ticketBatch.setEventName("New name");
//         assertEquals( "New name" , ticketBatch.getEventName() );
//         ticketBatch.setSeller("New seller");
//         assertEquals( "New seller" , ticketBatch.getSeller() );
//     }
// }    