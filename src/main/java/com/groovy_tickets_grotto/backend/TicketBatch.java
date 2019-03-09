package com.groovy_tickets_grotto.backend;

public class TicketBatch
{
    private String eventName;
    private float cost;
    private int availableTickets;
    

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
        this.eventName = eventName;
    }

    public float getCost() {
        return this.cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getAmountAvailable() {
        return this.availableTickets;
    }
    public void setAmountAvailable(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}