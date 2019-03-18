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
 * Session Class This is the entrypoint to the system, this class is what
 * "brings it all together". it contains methods for file input and output,
 * which are the event drivers for the backend.
 *
 */
public class Session{
	// Constants section
	static private final String AVAILABLE_USERS_FILE = "CurrentUserAccounts.txt"; // Available users file default
	static private final String AVAILABLE_TICKETS_FILE = "AvailableTickets.txt"; // Available tickets file default

	static private Map<String, User> Users = new HashMap<String, User>(); // Map of all users in the system, loaded from
	static private Map<String, TicketBatch> Tickets = new HashMap<String, TicketBatch>(); // Map of all tickets in the
	static private final String END_OF_FILE_STRING = "END"; // end of file flag
	private User currentUser; // current user this session is for, aka the user running all the transactions

	public String CurrentTransactions = ""; 

	public Session(){
		
	}
	public User getCurrentUser() {
		return this.currentUser;
	}
	public User getUser(String username){
		return Users.get(username);
	}
	public void addUser(User user){
		Users.put(user.getUsername(), user);
	}
	public void addTicketBatch(TicketBatch ticketbatch){
		Tickets.put(ticketbatch.getEventName()+ticketbatch.getSeller(), ticketbatch);
	}
	public TicketBatch getTicketBatch(String eventKey){
		return Tickets.get(eventKey);
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	public Map<String, User> getUsers(){
		return Users;
	}
	public Map<String, TicketBatch> getTickets(){
		return Tickets;
	}
	/**
	 * Creates the appropriate transaction object from the parsed string
	 * 
	 * @param type the type to set
	 */
	private void parseTransactions() throws IOException{

		BufferedReader bufReader = new BufferedReader(new StringReader(CurrentTransactions));
		String transactionString = "";
		while((transactionString = bufReader.readLine()) != null)
		{	
			Transaction t = Transaction.CreateTransactionFromString(transactionString);
			t.RunTransaction( this );
		}
		//Clears transactions
		CurrentTransactions = "";
	}

	private void readTransactionFile() {
		try {
			File transactionFile = new File("mergedtransactions.trn");
			BufferedReader reader = new BufferedReader(new FileReader(transactionFile));
			// ^ Create the file, file reader, and buffered reader we need.

			String line; // String var to store each line we read in
			while ((line = reader.readLine()) != null) { // loop over transaction file
				System.out.println("ADDING: "+line);
				CurrentTransactions+=line+"\n";
				if(line.substring(0,2).equals("00")){
					setCurrentUser(Users.get(line.substring(3,18).trim()));
					parseTransactions();
				}
			}
			reader.close();
		} catch (Exception e) {
			PrintError(e);
			e.printStackTrace();
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
	static private void saveTickets()
	{
		// this function will save the tickets to the new available tickets file
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter("finished_ticket.txt"));
			//Iterates over the map
			for (Map.Entry<String, TicketBatch> entry : Tickets.entrySet())
			{
				writer.write(entry.getValue().toString()+"\n");
			}
			writer.write("END");
			writer.close();
		}catch(IOException e){

		}
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
					Tickets.put( aBatch.getEventName() + aBatch.getSeller() , aBatch );
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
	 * The entry point for the backend of the system.
	 * 
	 * @param args the file names for the daily transaction file, users file and
	 *             tickets file
	 */
	static public void main(String[] args)
	{
		Session session = new Session();
		// Read in the user file into the map
		session.parseUsersFile();
		
		// Read the available tickets into the map
		session.parseTicketsFile();

		// parse merged transactions and execute them.
		session.readTransactionFile();
	
		// save users
		session.saveUsers();

		// also save tickets
		session.saveTickets();
	}
	/** PrintError
	 * wrapper to make printing errors easier
	 */
	static public void PrintError( Object obj )
	{
	System.out.println( "ERROR:	" + obj.toString() );
	}
}
