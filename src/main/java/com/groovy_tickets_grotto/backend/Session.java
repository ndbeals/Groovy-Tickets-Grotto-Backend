package com.groovy_tickets_grotto.backend;
import java.util.*;
import java.io.*;

import org.apache.commons.cli.*;

import com.groovy_tickets_grotto.*;

/**
 * Session Class
 * This is the entrypoint to the system, this class is what "brings it all together". it contains methods for file input and output, 
 * which are the event drivers for the backend.
 *
 */
public class Session 
{
    // Constants section
    private static final String AVAILABLE_TICKETS_FILE = "AvailableTickets.txt";
    private static final String AVAILABLE_USERS_FILE = "CurrentUserAccounts.txt";


    static private Map<String,User> Users = new HashMap<String,User>();
    static private Map<String,TicketBatch> Tickets;
    
    private User CurrentUser;
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

    /**
     * ParseUsersFile
     *  Parse the available users file into User class instances and store them on the static "Users" map.
     */
    static private void ParseUsersFile()
    {
        BufferedReader reader = null;
        // create a buffered reader, and then try to open a file and read the file line by line
        try {
            File file = new File( AVAILABLE_USERS_FILE );
            reader = new BufferedReader(new FileReader(file));

            String line;
            // Loop over each line, creating a user object and placing that object in the Users map
            while ((line = reader.readLine()) != null) {
                System.out.println("|" + line.trim() + "|");
                
                // parse line string into user if it isn't the end line
                if ( !line.trim().equals("END") ) {
                    User aUser = new User( line );
                    // Users[ aUser.getUsername() ] = aUser;
                    System.out.println( "a user: " + aUser.getUsername() );
                    Users.put( aUser.getUsername(), aUser);
                    
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ParseTicketsFile
     *  Parse the available tickets file and parse them into TicketBatch instances, populatice the static "Tickets" map
     */
    static private void ParseTicketsFile()
    {
        BufferedReader reader = null;
        // create a buffered reader, and then try to open a file and read the file line by line
        try {
            File file = new File( AVAILABLE_TICKETS_FILE );
            reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        // ParseTicketsFile();
        ParseUsersFile();
        

        thisSession.parseCommandLineArguments( args );
    }
}
