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
    static private final String AVAILABLE_TICKETS_FILE = "AvailableTickets.txt";
    static private final String AVAILABLE_USERS_FILE = "CurrentUserAccounts.txt";

    static private final String ERROR_PROMPT = "ERROR: ";
    static private final String END_OF_FILE_STRING = "END";


    static private Map<String,User> Users = new HashMap<String,User>();
    static private Map<String,TicketBatch> Tickets = new HashMap<String,TicketBatch>();
    
    private User CurrentUser;
    private String CurrentTransactions;


    /** PrintError
     * wrapper to make printing errors easier
     */
    static public void PrintError( Object obj )
    {
        System.out.println( ERROR_PROMPT + obj.toString() );
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
     * Creates the appropriate transaction object from the parsed string
     * @param type the type to set
     */
    private void parseTransactions() throws IOException
    {
        BufferedReader bufReader = new BufferedReader(new StringReader(CurrentTransactions));
        String transactionString = null;
        while((transactionString = bufReader.readLine()) != null)
        {
             Transaction t = Transaction.CreateTransactionFromString(transactionString, this);
            //  t.runTransaction();
        }

    }
    /**
     * Saves the users map to file in the correct format.
     */
    static private void saveUsers()
    {   
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("finished_users.txt"));
            //Iterates over the map
            for (Map.Entry<String, User> entry : Users.entrySet())
            {
                writer.write(entry.getValue().toString()+"\n");
            }
            writer.write("END");
            writer.close();
        }catch(IOException e){

        }
        
    }
    /**
     * Saves the tickets map to file in the correct format.
     */
    private void saveTickets()
    {
        
    }

    /** GetUserByName
     * returns user class based on the string name provided, if there is one
     * @param name the user name to find
     * @return User class
     */
    static public User GetUserByName( String name )
    {
        // PrintError( name + " looking for");
        return Users.get( name.trim() );
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
            File file   = new File( AVAILABLE_USERS_FILE );
            reader      = new BufferedReader(new FileReader(file));
            String line;

            // Loop over each line, creating a user object and placing that object in the Users map
            while ( (line = reader.readLine()) != null ) {
                // parse line string into user if it isn't the end line
                if ( !line.trim().equals( END_OF_FILE_STRING ) ) {
                    User aUser = new User( line );
                    // Users[ aUser.getUsername() ] = aUser;
                    // System.out.println( "a user: " + aUser.getBalance() );
                    Users.put( aUser.getUsername(), aUser);
                }
            }
        } 
        catch (IOException ex)
        {
            // catch exceptions and print them with error prefix
            PrintError( ex.getLocalizedMessage() );
        } 
        finally
        {
            try {
                reader.close();
            } catch (Exception ex) {
                // catch exceptions and print them with error prefix
                PrintError( ex.getLocalizedMessage() );
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

            // Loop over each line, creating a ticketbatch object and placing that object in the tickets map
            while ( (line = reader.readLine()) != null ) {
                // parse line string into ticketbatch if it isn't the end line
                if ( !line.trim().equals( END_OF_FILE_STRING ) ) {
                    TicketBatch aBatch = new TicketBatch( line );
                    // System.out.println( "batch: " + aBatch.getEventName() + " | " + aBatch.getCost() );
                    Tickets.put( aBatch.getEventName() + aBatch.getSeller().getUsername() , aBatch );
                }
            }

        }
        catch (IOException ex)
        {
            // catch exceptions and print them with error prefix
            PrintError( ex.getLocalizedMessage() );
        } 
        finally
        {
            try {
                reader.close();
            } catch (Exception ex) {
                // catch exceptions and print them with error prefix
                PrintError( ex.getLocalizedMessage() );
            }
        }
    }
    
    /** parseCLIArguments
     * parse string passed to the executable into paths to the relevant files
     */
    private void parseCLIArguments( String[] args )
    {
        // System.out.println("hello\n");
        

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
    static public void main( String[] args )
    {
        // System.out.println( "Hello World!" );
        // System.out.println( "Hello World2" );
        
        
        Session thisSession = new Session();
        thisSession.parseCLIArguments( args );

        // ParseTicketsFile();


        // Read in the user file into the map
        ParseUsersFile();
        
        // Read the available tickets into the map
        ParseTicketsFile();

        


        
        saveUsers();
    }
}
