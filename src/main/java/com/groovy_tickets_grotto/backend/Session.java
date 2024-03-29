/** Groovy-Tickets-Grotto Backend ticket purchasing system.
 * 
 * This is a backend application for a ticket purchasing system. 
 * The system is meant to be built with maven, and run using the provided run.sh script.
 *
 * This ticket purchasing system handles the following transactions:
 * 		00 - End of session
 * 		01 - Create
 * 		02 - Delete
 * 		03 - Sell
 * 		04 - Buy
 * 		05 - Refund
 * 		06 - AddCredit
 * 
 * 
 * Input Files:
 * 	currentusers.txt
 * 		File which contains the current user list.
 *  availabletickets.txt
 * 		File which contains the details of the tickets available for purchase
 *  mergedtransactions.txt
 * 		File which contains the details of all the transactions to run
 * 
 * Output Files:
 * When the backend is finished processing all transactions, it outputs updated versions of the following files
 *  currentusers.txt
 *  availabletickets.txt
 *  mergedtransactions.txt
 * 
 * @author		Nathan Beals, Quinton Belcastro
 * @version		2019.proto
 * @since		March 2019
 *
 */
package com.groovy_tickets_grotto.backend;

import java.util.*;
import java.util.concurrent.*;
import java.io.*;

/**
 * Session Class This is the entrypoint to the system, this class is what
 * "brings it all together". it contains methods for file input and output,
 * which are the event drivers for the backend.
 *
 */
public class Session implements Runnable {
	// Constants section
	static private String oldAvailableUsersFile = "CurrentUserAccounts.txt"; // Available users file default location
	static private String newAvailableUsersFile = "new_CurrentUserAccounts.txt"; // Available users file default location
	static private String oldAvailableTicketsFile = "AvailableTickets.txt"; // Available tickets file default location
	static private String newAvailableTicketsFile = "new_AvailableTickets.txt"; // Available tickets file default location
	static private String mergedTransactionsFile = "mergedtransactions.trn";

	static private final String ERROR_PROMPT = "ERROR: "; // prompt before all errors
	static private final String END_OF_FILE_STRING = "END"; // end of file flag

	static private Map<String, User> Users = new HashMap<String, User>(); // Map of all users in the system, loaded from file
	static private Map<String, TicketBatch> Tickets = new HashMap<String, TicketBatch>(); // Map of all tickets in the system, loaded from file
	
	// Member section
	private User currentUser; // current user this session is for, aka the user running all the transactions

	public BlockingQueue<String> readQueue; // Queue that the read thread pushes each line it reads to, as fast as
	// public BlockingQueue<Transaction> readQueue; // Queue that the read thread pushes each line it reads to, as fast as
											// possible.
	private Deque<Transaction> transactionQueue; // queue (deque implementation) of transactions to be ran.

	/**											END OF VARIABLE DECLARATION
	 * ----------------------------------------------------------------------------------------------------------------
	 */
	 
	/** Session constructor
	 * Creates initializes the read and transaction queues 
	 */
	public Session()
	{
		transactionQueue = new LinkedList<Transaction>();
		readQueue = new LinkedBlockingQueue<String>();
	}

	// Getters / Setters

	/** getCurrentUser
	 * returns the user this session is running under
	 * @return User currentUser
	 */
	public User getCurrentUser()
	{
		return this.currentUser;
	}
	/** setCurrentUser
	 * sets the user this session runs under
	 */
	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}
	//

	/** runTransactions
	 * runs/executes all of the transactions in a user session, once the user session transaction has been read
	 */
	private void runTransactions()
	{
		while (!transactionQueue.isEmpty()) {
			transactionQueue.removeFirst().RunTransaction(this);
		}
	}

	private boolean addTransaction(String trn)
	{
		Transaction newTrn = Transaction.CreateTransactionFromString(trn);

		// If the transaction is an end of session (aka begin of sesion)
		if (newTrn.getTransactionNumber() == 0) {
			// then add it to the start of the queue
			transactionQueue.addFirst(newTrn);
			// then run the queue, after this has ran the queue will be empty, and ready to accept new transactions from the next user session
			
			return true;
		} else {
			transactionQueue.addLast(newTrn);
			return false;
		}
	}

	private void readTransactionFile()
	{
		try {
			File transactionFile = new File( mergedTransactionsFile );
			BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
			// ^ Create the file, file reader, and buffered reader we need.

			String line; // String var to store each line we read in
			while ((line = reader.readLine()) != null) { // loop over transaction file
				// Thread.sleep(1);
				if ( line.length()>0 ) {
					readQueue.put(line);
				}
			}

			readQueue.put("");	// When we're done executing, pass an empty string to signal we're done
			reader.close();		// finish and close.
		}
		catch (Exception e) {
			PrintError(e);
			// e.printStackTrace();
			// readQueue.put(""); // signal we've met our end.
			readQueue.add("");
		}
	}

	/** run
	 * The 'Runnable' interface requires there to be a 'run' public method to be able to create a thread with an instance of this class
	 * so, this run just calls the readTransactionFile() method
	 */
	public void run()
	{
		readTransactionFile();
	}

	/** readAndExecuteTransaction
	 * this is the "bulk" of the execution. This starts the read thread, and then loops through the queue the read thread fills and runs those transactions it gets
	 */
	private void readAndExecuteTransactions()
	{
		boolean canExecute = false;
		Thread readThread = new Thread( this );
		readThread.start();

		try {
			String transactionLine = readQueue.take();
			
			while ( transactionLine.length()>0 )
			{
				canExecute = addTransaction( transactionLine );
				if ( canExecute ) {
					runTransactions();
				}
				
				transactionLine = readQueue.take();
			}

		} catch (Exception e)
		{
			PrintError(e.toString());
		}
	}


	/******************************************************************
	 *                        STATIC SECTION
	 ******************************************************************/

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

	/** AddUser
	 * adds the specified user to the global user map
	 * @param newUser to be added
	 */
	static public void addUser( User newUser )
	{
		Users.put(newUser.getUsername(), newUser);
	}
	
	/** RemoveUser
	 * removes the specified user (by string name) from the global map
	 * @param userName name of user to be removed
	 */
	static public void RemoveUser( String userName )
	{
		Users.remove( userName );
	}

	/** RemoveTicketBatch
	 * Removes the specified ticket batch from the global map
	 * @param batch to be removed
	 */
	static public void RemoveTicketBatch( TicketBatch batch )
	{
		Tickets.remove(batch.getEventName()+batch.getSeller().getUsername());
	}

	/** AddTicketBatch
	 * Adds the given ticket batch the global map of ticket batches
	 * @param batch TicketBatch to be added to the global map
	 */
	static public void AddTicketBatch( TicketBatch batch )
	{
		Tickets.put( batch.getEventName() + batch.getSeller().getUsername() , batch );
	}

	/** GetTicketBatch
	 * Returns the ticket batch with the same name as the passed string
	 * @param eventName name of the event (+sellername) of the ticket batch
	 * @return TicketBatch batch of tickets for sale
	 */
	static public TicketBatch getTicketBatch( String eventName )
	{
		return Tickets.get(eventName.trim());
	}


	/** parseUsersFile
	 *  Parse the available users file into User class instances and store them on the static "Users" map.
	 */
	static private void readUsersFile()
	{
		BufferedReader reader = null;
		// create a buffered reader, and then try to open a file and read the file line by line
		try {
			File file   = new File( oldAvailableUsersFile );
			reader      = new BufferedReader(new FileReader(file));
			String line;

			// Loop over each line, creating a user object and placing that object in the Users map
			while ( (line = reader.readLine()) != null ) {
				// parse line string into user if it isn't the end line
				if ( !line.trim().equals( END_OF_FILE_STRING ) ) {
					User aUser = new User( line );
					Users.put( aUser.getUsername(), aUser);
				}
			}
		} 
		catch (IOException ex)
		{
			// catch exceptions and print them with error prefix
			PrintError( ex );
		} 
		finally
		{
			try {
				reader.close();
			} catch (Exception ex) {
				// catch exceptions and print them with error prefix
				PrintError( ex );
			}
		}
	}

	/**
	 * Saves the users map to file in the correct format.
	 */
	static private void saveUsers()
	{   
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter( newAvailableUsersFile ));
			//Iterates over the map
			for (Map.Entry<String, User> entry : Users.entrySet())
			{
				writer.write(entry.getValue().toString()+"\n");
			}
			writer.write( END_OF_FILE_STRING );
			writer.close();
		} 
		catch(IOException ex) {
			PrintError( ex );
		}
	}
	
	/**
	 * parseTicketsFile
	 *  Parse the available tickets file and parse them into TicketBatch instances, populatice the static "Tickets" map
	 */
	static private void readTicketsFile()
	{
		BufferedReader reader = null;
		// create a buffered reader, and then try to open a file and read the file line by line
		try {
			// File file = new File( newAvailableTicketsFile );
			reader = new BufferedReader(new FileReader( oldAvailableTicketsFile ));
			String line;

			// Loop over each line, creating a ticketbatch object and placing that object in the tickets map
			while ( (line = reader.readLine()) != null ) {
				// parse line string into ticketbatch if it isn't the end line
				if ( !line.trim().equals( END_OF_FILE_STRING ) ) {
					TicketBatch aBatch = new TicketBatch( line );
					Tickets.put( aBatch.getEventName() + aBatch.getSeller().getUsername() , aBatch );
				}
			}
		}
		catch (IOException ex)
		{
			// catch exceptions and print them with error prefix
			PrintError( ex );
		} 
		finally
		{
			try {
				reader.close();
			} catch (Exception ex) {
				// catch exceptions and print them with error prefix
				PrintError( ex );
			}
		}
	}

	/**
	 * Saves the tickets map to file in the correct format.
	 */
	static private void saveTickets()
	{
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter( newAvailableTicketsFile ));
			//Iterates over the map
			for (Map.Entry<String, TicketBatch> entry : Tickets.entrySet())
			{
				writer.write(entry.getValue().toString()+"\n");
			}
			writer.write( END_OF_FILE_STRING );
			writer.close();
		} 
		catch(IOException ex) {
			PrintError( ex );
		}
	}
	
	/** parseCLIArguments
	 * parse string passed to the executable into paths to the relevant files
	 */
	static private void parseCLIArguments( String[] args )
	{
		// this command will parse command line arguments passed to it, or set defualts if non were passed
		if ( args.length > 0 ) {
			File usersFile = new File( args[0] );
			if (usersFile.exists()) {
				oldAvailableUsersFile = usersFile.getAbsolutePath();
			}
		}
		
		if ( args.length > 1 ) {
			File ticketsFile = new File( args[1] );
			if (ticketsFile.exists()) {
				oldAvailableTicketsFile = ticketsFile.getAbsolutePath();
			}
		}

		if ( args.length > 2 ) {
			File trnFile = new File( args[2] );
			if (trnFile.exists()) {
				mergedTransactionsFile = trnFile.getAbsolutePath();
			}
		}

		if ( args.length > 3 ) {
			File newUsers = new File( args[3] );
			newAvailableUsersFile = newUsers.getAbsolutePath();
		}
		
		if ( args.length > 4 ) {
			File newTickets = new File( args[4] );
			newAvailableTicketsFile = newTickets.getAbsolutePath();
		}
	}

	/**
	 * The entry point for the backend of the system.
	 * 
	 * @param args the file names for the daily transaction file, users file and
	 *             tickets file
	 */
	static public void main(String[] args)
	{
		// parse the arguments that were passed to the executable
		parseCLIArguments( args );
		
		// Read in the user file into the map
		readUsersFile();
		
		// Read the available tickets into the map
		readTicketsFile();
		
		Session session = new Session();
		// parse merged transactions and execute them.
		session.readAndExecuteTransactions();
		
		// save users
		saveUsers();

		// also save tickets
		saveTickets();
	}

	/** PrintError
	 * wrapper to make printing errors easier
	 */
	static public void PrintError( Object obj )
	{
		System.out.println( ERROR_PROMPT + obj.toString() );
	}
}
