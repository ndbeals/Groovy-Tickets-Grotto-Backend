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
     * Parses the transaction file stopping at entries stoping at
     * 00 entries to parse them.
     * @param fileName the name of the file with the transactions
     */
    private void parseTransactionFile(String fileName)throws IOException 
    {
        BufferedReader reader = null;
        File transactionFile = new File(fileName);
        reader = new BufferedReader(new FileReader(transactionFile));
        String transaction;
        while((transaction = reader.readLine())!= null)
        {
            CurrentTransactions += transaction;
            if(transaction.substring(0, 2).equals("00"))
            {
                parseTransactions();
            }
        }
        reader.close();
        saveUsers();
        saveTickets();
    }
    /**
     * Creates the appropriate transaction.
     * @param type the type to set
     */
    private void parseTransactions() throws IOException
    {
        BufferedReader bufReader = new BufferedReader(new StringReader(CurrentTransactions));
        String transactionString = null;
        while((transactionString = bufReader.readLine()) != null)
        {
             Transaction t = Transaction.parse(transactionString);
             t.runTransaction(this);
        }

    }
    private void saveUsers()
    {

    }
    private void saveTickets()
    {

    }
}
