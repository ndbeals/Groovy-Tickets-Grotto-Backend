package com.groovy_tickets_grotto.backend;
import java.util.*;
import java.io.*;
/**
 * Hello world!
 *
 */
public class Session 
{
    private User CurrentUser;
    private ArrayList<User> Users;
    private ArrayList<TicketBatch> Tickets;
    private String CurrentTransactions;

    /**
     * The entry point for the backend of the system. 
     * @param args the file names for the daily transaction file, users file and tickets file
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    /**
     * @param fileName the name of the file with the transactions
     */
    private void parseTransactionFile(String fileName)throws IOException 
    {
        FileInputStream in = null;
        
        in = new FileInputStream(fileName);
        
    }
    /**
     * @param type the type to set
     */
    private void parseTransactions()
    {

    }
    private void saveUsers()
    {

    }
    private void saveTickets()
    {

    }
}
