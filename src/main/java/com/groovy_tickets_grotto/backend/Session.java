package com.groovy_tickets_grotto.backend;
import java.util.*;
import java.io.*;

import org.apache.commons.cli.*;
/**
 * Hello world!
 *
 */
public class Session 
{
    private User CurrentUser;
    private Map<String,User> Users;
    private Map<String,TicketBatch> Tickets;
    private String CurrentTransactions;

    
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
             Transaction t = Transaction.parse(transactionString, this);
             t.runTransaction();
        }

    }
    private void saveUsers()
    {
        
    }
    private void saveTickets()
    {
        
    }
    
    private void parseCommandLineArguments( String[] args )
    {
        System.out.println("hello\n");
        

        // Option input = new Option("i", "input", true, "input file path");
        // input.setRequired(true);
        // options.addOption(input);

        // Option output = new Option("o", "output", true, "output file");
        // output.setRequired(true);
        // options.addOption(output);
        
        // CommandLineParser parser = new DefaultParser();
        // HelpFormatter formatter = new HelpFormatter();
        // CommandLine cmd;

        // try {
        //     cmd = parser.parse(options, args);
        // } catch (ParseException e) {
        //     System.out.println("HESHESH");
        //     System.out.println(e.getMessage());
        //     formatter.printHelp("utility-name", options);
            
        //     System.exit(1);
        // }
        
        // // String inputFilePath = cmd.getOptionValue("input");
        // // String outputFilePath = cmd.getOptionValue("output");
        
        // System.out.println("test" + cmd.getArgList());
        
        // System.out.println(inputFilePath);
        // System.out.println(outputFilePath);
        
    }

    /**
     * The entry point for the backend of the system. 
     * @param args the file names for the daily transaction file, users file and tickets file
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println( "Hello World2" );


        Session thisSession = new Session();
        

        thisSession.parseCommandLineArguments( args );
    }
}
