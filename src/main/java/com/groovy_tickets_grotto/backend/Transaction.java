package com.groovy_tickets_grotto.backend;
import java.util.*;
import com.groovy_tickets_grotto.backend.transactions.*;

/** Transaction
 * Parent class for all the actual transaction implementations
 */
public abstract class Transaction
{
    private byte readPosition;

    protected String    transactionString;
    protected byte      transactionNumber;

    // protected Session session;

    public Transaction()
    {
        // readPosition = 3; // set to 3 because first 3 bytes of a transaction is the code, and it's already handled
    }

    // public Transaction( Session session)
    // {
    //     this.session = session;
    // }
    
    public Transaction( String trn )
    {
        super();
        setTransactionString( trn );
    }

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
    public static Transaction CreateTransactionFromString(String transaction)
    {   
        Transaction t;
        String transactionCode = transaction.substring(0, 2);

        if ( transactionCode.equals("00") )
        {
            t = new EndSession();
        }
        else if ( transactionCode.equals("01") )
        {
            t = new Create();
        }
        else if (transactionCode.equals("02"))
        {
            t = new Delete();
        }
        else if (transactionCode.equals("03"))
        {
            t = new Sell();
        }
        else if (transactionCode.equals("04"))
        {
            t = new Buy();
        }
        else if (transactionCode.equals("05"))
        {
            t = new Refund();
        }
        else if (transactionCode.equals("06"))
        {
            t = new AddCredit();
        }
        else
        {
            return null;
        }

        t.setTransactionString( transaction );

        return t;
    }
}