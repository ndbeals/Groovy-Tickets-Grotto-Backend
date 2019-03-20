package com.groovy_tickets_grotto.backend;
import java.util.*;
import com.groovy_tickets_grotto.backend.transactions.*;

/** Transaction
 * Parent class for all the actual transaction implementations
 */
public abstract class Transaction
{
	protected byte 		readPosition;

	protected String    transactionString;
	protected byte      transactionNumber;


	public Transaction()
	{}

	/** setTransactionString
	 * sets the transaction string
	 */
	public void setTransactionString( String trn )
	{
		this.transactionString = trn;
		setTransactionNumber( Byte.parseByte( trn.substring( readPosition, readPosition+2 ) ) );
		readPosition+=3;
	}
	public String getTransactionString()
	{
		return this.transactionString;
	}

	public byte getTransactionNumber() {
		return this.transactionNumber;
	}
	public void setTransactionNumber(byte transactionNumber) {
		this.transactionNumber = transactionNumber;
	}


	public String ExtractUsername()
	{
		readPosition += 16;
		return this.transactionString.substring( readPosition - 16 , readPosition -1 ).trim();
	}
	
	public String ExtractUsertype()
	{
		// System.out.println("Pos: " + readPosition + "   afterpos: " + (readPosition+3)); 
		readPosition += 3;
		return this.transactionString.substring( readPosition - 3, readPosition - 1 ).trim();
	}

	public float ExtractCredit()
	{
		readPosition += 10;
		return Float.parseFloat( this.transactionString.substring( readPosition - 10, readPosition - 1 ).trim() );
	}

	public String ExtractEventTitle()
	{
		readPosition += 26;
		return transactionString.substring( readPosition - 26, readPosition - 1 ).trim();
	}

	public int ExtractTicketAmount()
	{
		readPosition += 4;
		return Integer.parseInt( transactionString.substring( readPosition - 4, readPosition - 1).trim() );
	}

	public float ExtractTicketPrice()
	{
		readPosition += 7;
		return Float.parseFloat( transactionString.substring( readPosition - 7, readPosition - 1).trim() );
	}


	/**
	 * Runs the specific actions related to each transaction ie deleting users from map
	 * adding new users to map, subtracting balances etc
	 */
	public abstract void RunTransaction( Session session );


	/**
	 * STATIC SECTION
	 */

	/**
	 * Returns the proper subclass of Transaction depending on the code entered
	 */
	public static Transaction CreateTransactionFromString( String transaction )
	{   
		if (transaction==null)
		{
			return null;
		}
		Transaction newTrn;
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