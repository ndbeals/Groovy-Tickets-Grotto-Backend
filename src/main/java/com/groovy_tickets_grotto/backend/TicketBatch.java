package com.groovy_tickets_grotto.backend;

public class TicketBatch
{
    private String eventName;
    private User seller;
    private int availableTickets;
    private float cost;
    

    /**
     * Empty stub constructor - required
     */
    public TicketBatch()
    {

    }

    /** TicketBatck Constructor
     * Takes a line from the file input "AvailableTickets.txt" as an input and parses that string into values
     * @param line the string line from the file
     */
    public TicketBatch( String line )
    {
        setEventName( line.substring( 0 , 25 ) );
        setSeller( line.substring( 26 , 41 ) );
        setAmountAvailable( line.substring( 42 , 45 ) );
        setCost( line.substring( 46 , 52 ) );
    }

    /** getEventName
     * Gets the event name of event these tickets are for
     * @return event name
     */
    public String getEventName() {
        return this.eventName;
    }
    /** setEventName
     * Sets the event name of these tickets are for
     * @param eventName the new name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName.trim();
    }

    public User getSeller() {
        return this.seller;
    }
    /** setSeller
     * sets the seller of this batch to the provided user class Seller
     * @param seller the seller
     */
    public void setSeller(User seller) {
        this.seller = seller;
    }
    /** setSeller
     * sets the seller of this batch to the provided string seller, takes string as input 
     * @param seller the seller
     */
    public void setSeller(String seller) {
        this.seller = Session.GetUserByName( seller );
        // System.out.println( "seller: " + this.seller.getUsername() );
    }

    public int getAmountAvailable() {
        return this.availableTickets;
    }
    /** setAmountAvailable
     * sets the amount of available tickets
     * @param availableTickets int number of available tickets
     */
    public void setAmountAvailable(int availableTickets) {
        this.availableTickets = availableTickets;
    }
    /** setAmountAvailable
     * sets the amount of available tickets, takes string as input
     * @param availableTickets string number of available tickets
     */
    public void setAmountAvailable(String availableTickets) {
        this.availableTickets = Integer.parseInt( availableTickets );
    }

    public float getCost() {
        return this.cost;
    }
    /** setCost
     * sets the cost of each individual ticket
     * @param cost float cost of ticket
     */
    public void setCost(float cost) {
        this.cost = cost;
    }
    /** setCost
     * sets the cost of each individual ticket, takes string as input
     * @param cost string cost of ticket
     */
    public void setCost(String cost) {
        this.cost = Float.parseFloat( cost );
    }
    
}