package com.groovy_tickets_grotto.backend;
import java.util.*;
import com.groovy_tickets_grotto.backend.transactions.*;

/** Transaction
 * Parent class for all the actual transaction implementations
 */
public abstract class Transaction
{
	protected String    transactionString;
	/**
	 * Runs the specific actions related to each transaction ie deleting users from map
	 * adding new users to map, subtracting balances etc
	 */
	public abstract void RunTransaction( Session session );
	public void setTransactionString(String tString){
		transactionString = tString;
	}
	/**
	 * Returns the proper subclass of Transaction depending on the code entered
	 */
	public static Transaction CreateTransactionFromString( String transaction )
	{   
		Transaction newTrn;
		System.out.println("CREATING TRANSACTION: " + transaction);
		String transactionCode = transaction.substring(0, 2);

		if ( transactionCode.equals("00") )
		{
			newTrn = new EndSession();
		}
		else if ( transactionCode.equals("01") )
		{
			newTrn = new Create();
		}
		else if ( transactionCode.equals("02") )
		{
			newTrn = new Delete();
		}
		else if (transactionCode.equals("03"))
		{
			newTrn = new Sell();
		}
		else if (transactionCode.equals("04"))
		{
			newTrn = new Buy();
		}
		else if (transactionCode.equals("05"))
		{
			newTrn = new Refund();
		}
		else if (transactionCode.equals("06"))
		{
			newTrn = new AddCredit();
		}
		else
		{
			return null;
		}

		newTrn.setTransactionString( transaction );

		return newTrn;
	}
}