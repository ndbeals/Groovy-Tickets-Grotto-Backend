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

import org.apache.commons.cli.*;

import com.groovy_tickets_grotto.*;

/**
 * Session Class
 * This is the entrypoint to the system, this class is what "brings it all together". it contains methods for file input and output, 
 * which are the event drivers for the backend.
 *
 */
public class Session implements Runnable
{
	// Constants section
	static private final String AVAILABLE_USERS_FILE = "CurrentUserAccounts.txt";		// Available users file default location
	static private final String AVAILABLE_TICKETS_FILE = "AvailableTickets.txt";    	// Available tickets file default location

	static private final String ERROR_PROMPT = "ERROR: ";								// prompt before all errors
	static private final String END_OF_FILE_STRING = "END";								// end of file flag


	static private Map<String,User> Users = new HashMap<String,User>();					// Map of all users in the system, loaded from file
	static private Map<String,TicketBatch> Tickets = new HashMap<String,TicketBatch>();	// Map of all tickets in the system, loaded from file
	
	// Member section
	private User currentUser;						// current user this session is for, aka the user running all the transactions

	public BlockingQueue<String> readQueue;		// Queue that the read thread pushes each line it reads to, as fast as possible.
	private Deque<Transaction> transactionQueue;	// queue (deque implementation) of transactions to be ran.

	public String CurrentTransactions;				// the string of all transactions (will be queue later)
	public boolean finishedReading = false;

	public Session()
	{
		transactionQueue 	= new LinkedList<Transaction>();
		readQueue			= new LinkedBlockingQueue<String>();
	}

	public Session( String endOfSessionLine , Queue<String>sessionTransactions )
	{
		super();
		this.currentUser = GetUserByName( endOfSessionLine.substring(3,19).trim() );

		parseTransactions( sessionTransactions );
	}

	public User getCurrentUser() {
		return this.currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}


	/**
	 * Creates the appropriate transaction object from the parsed string
	 * @param type the type to set
	 */
	private void parseTransactions( Queue<String>sessionTransactions )
	{
		System.out.println("parse trn " + this.currentUser.getUsername() );
		while ( !sessionTransactions.isEmpty() ) {

			Transaction curTrn = Transaction.CreateTransactionFromString( sessionTransactions.poll() );

			curTrn.RunTransaction( this );
		}
		// BufferedReader bufReader = new BufferedReader(new StringReader(CurrentTransactions));
		// String transactionString = null;
		// while((transactionString = bufReader.readLine()) != null)
		// {
		// 	 Transaction t = Transaction.CreateTransactionFromString(transactionString, this);
		// 	 t.RunTransaction( Session session );
		// }
	}

	private void runTransactions()
	{
		while ( !transactionQueue.isEmpty() )
		{
			transactionQueue.removeFirst().RunTransaction( this );
		}
	}

	private void addTransaction( String trn )
	{
		Transaction newTrn = Transaction.CreateTransactionFromString( trn );

		// If the transaction is an end of session (aka begin of sesion)
		if (newTrn.getTransactionNumber() == 0 )
		{
			// then add it to the start of the queue
			transactionQueue.addFirst(newTrn);
			// then run the queue, after this has ran the queue will be empty, and ready to accept new transactions from the next user session
			runTransactions();
		}
		else
		{
			transactionQueue.addLast(newTrn);
		}
	}

	public void run()
	{
		try {
			File transactionFile = new File("mergedtransactions.trn");
			BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
			// ^ Create the file, file reader, and buffered reader we need.
	
			String line;
			// loop over transaction file
			while ( (line = reader.readLine()) != null )
			{
				// session.addTransaction( line );
				// Thread.sleep(1);
				System.out.println("\tthread read: " + line);
				readQueue.put(line);
			}
			reader.close();
		}
		catch (Exception e)
		{
			PrintError(e);
			e.printStackTrace();
		}
		finishedReading = true;
		System.out.println("  THREAD: GOODBYE");
	}

	/**
	 * Parses the transaction file stopping at entries stoping at
	 * 00 entries to parse them.
	 * @param fileName the name of the file with the transactions
	 */
	static private void parseTransactionFile(String fileName,BlockingQueue<String> queue)
	{   
		try {
			File transactionFile = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
			// ^ Create the file, file reader, and buffered reader we need.

			// Queue<String> sessionTransactions = new LinkedList<>();
			String line;

			// Create the session object that'll be used to execute all the transactions
			// Session session = new Session();

			// loop over transaction file
			while ( (line = reader.readLine()) != null )
			{
				// session.addTransaction( line );
				// Thread.sleep(1);
				// Thread.sleep(100);
				System.out.println("\tthread read: " + line);
				queue.put(line);
				// CurrentTransactions += transaction;
				// if(transaction.substring(0, 2).equals("00"))
				// {
				// 	System.out.println("End of Session: " + transaction + "\n queue size: " + sessionTransactions.size() );
					
				// 	Session currentSession = new Session( transaction , sessionTransactions );
				// 	// sessionTransactions.clear();
				// }
				// else
				// {
				// 	// Transaction trans =  Transaction.CreateTransactionFromString(transaction, session)
				// 	// sessionTransactions.add( trans );
				// 	sessionTransactions.add( transaction );
				// }
			}

			reader.close();
		}
		catch (Exception e)
		{
			//TODO: handle exception
			// PrintError(e.getLocalizedMessage());
			PrintError(e);
			// PrintError(e.getMessage());
			// PrintError(e.getClass());
			e.printStackTrace();
			// PrintError(e.getCause());

		}
		System.out.println("  THREAD: GOODBYE");
		// saveUsers();
		// saveTickets();
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
	static private void saveTickets()
	{
		// this function will save the tickets to the new available tickets file
	}

	static public void addUser( User newUser )
	{
		Users.put(newUser.getUsername(), newUser);

		// for (Map.Entry<String,User> var : Users.entrySet())
		// {
		// 	PrintError("k: " + var.getKey() + "  v: " + var.getValue());
		// }
	}
	/*
	* Removes the passed in user from the map.
	*/
	static public void deleteUser( User user )
	{
		Users.remove(user.getUsername());
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
	 * parseUsersFile
	 *  Parse the available users file into User class instances and store them on the static "Users" map.
	 */
	static private void parseUsersFile()
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

	static public void addTicketBatch( TicketBatch batch )
	{
		Tickets.put( batch.getEventName() + batch.getSeller().getUsername() , batch );
	}

	/**
	 * parseTicketsFile
	 *  Parse the available tickets file and parse them into TicketBatch instances, populatice the static "Tickets" map
	 */
	static private void parseTicketsFile()
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
	static private void parseCLIArguments( String[] args )
	{
		// this command will parse command line arguments passed to it, or set defualts if non were passed
	}

	/**
	 * The entry point for the backend of the system.
	 * 
	 * @param args the file names for the daily transaction file, users file and
	 *             tickets file
	 * @throws InterruptedException
	 */
	static public void main(String[] args) throws InterruptedException
	{
		// parse the arguments that were passed to the executable
		parseCLIArguments( args );
		
		// Session thisSession = new Session();
		
		// Read in the user file into the map
		parseUsersFile();
		
		// Read the available tickets into the map
		parseTicketsFile();

		
		Session session = new Session();
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		// parse merged transactions and execute them.
		Thread t = new Thread( session );
		// new Thread(new Runnable() {
		// 	public void run()
		// 	{
		// 		session.CurrentTransactions = "testtdststs";
		// 		parseTransactionFile( "mergedtransactions.trn" ,queue);
		// 	}
		// });
		
		
		t.start();
		
		while (!session.finishedReading || !session.readQueue.isEmpty() ) {
			String line = session.readQueue.take();
			// String line = session.readQueue.poll(1,TimeUnit.SECONDS);
			if (line!=null)
			{
				System.out.println("main thread read line: " + line);
				session.addTransaction(line);
			}
		}
		
		// System.out.println("hello world " + session.CurrentTransactions);
		System.out.println("hello world ");

		// save users
		saveUsers();

		// also save tickets
		// SaveTickets();
	}

	/** PrintError
	 * wrapper to make printing errors easier
	 */
	static public void PrintError( Object obj )
	{
		System.out.println( ERROR_PROMPT + obj.toString() );
	}
}
