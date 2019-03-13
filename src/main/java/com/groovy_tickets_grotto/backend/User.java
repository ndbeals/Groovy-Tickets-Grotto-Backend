package com.groovy_tickets_grotto.backend;

/** User Class
 * represents the users within the system
 */
public class User
{
	/**
	 * Private backing variables for the class, accessed through getters and setters
	 */
	private String username;		// User name
	private UserType userType;		// User Type
	private float balance;			// Users balance


	/** UserType
	 * Enums to describe user class types
	 */
	static public enum UserType{
		AA,
		FS,
		BS,
		SS
	}
	
	/** Blank stub constructor
	 * Must exist
	 */
	public User() {
	}

	/** File Line constructor
	 * this constructor takes a line of text as found in the Current Users File and parses it into a valid user
	 * @param line the line of the file
	 */
	public User( String line ) {
		setUsername( line.substring(0 , 15) );
		setType( line.substring( 16 , 18) );
		setBalance( line.substring(19, 28) );
	}
	
	public User( String name, String type, float balance )
	{
		setUsername(name);
		setType(type);
		setBalance(balance);
	}

	/** getBalance - returns user balance
	 * @return the user balance
	 */
	public double getBalance() {
		return balance;
	}

	/** setBalance - set the users balance
	 * @param balance the balance to set
	 */
	public void setBalance(float balance) {
		this.balance = balance;
	}
	/** setBalance - set the users balance, taking a string input
	 * @param balance the string balance to parse then set
	 */
	public void setBalance(String balance) {
		this.balance = Float.parseFloat( balance );
	}

	/** getType - return the users type
	 * @return the type
	 */
	public UserType getType() {
		return userType;
	}

	/** setUserType - set the users type
	 * @param type the type to set
	 */
	public void setType(UserType type) {
		this.userType = type;
	}

	/** setUserType - set the users type, taking a string as an input 
	 * @param type the type to set
	 */
	public void setType(String type) {
		// parse passed string into enum
		this.userType = UserType.valueOf( type );
	}

	/** getUserName - return the users name
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

    /**
     * Returns the user as a string formatted proper specification
     */
    public String toString() {
        //Adds spaces to username up to length 15
        String formattedUsername = getUsername();
        for(int i=username.length();i<15;i++){
            formattedUsername += " ";
        }
        String formattedBalance = String.format("%.2f", getBalance());
        return formattedUsername+" "+getType().toString()+" "+("000000000"+formattedBalance).substring(formattedBalance.length());
    }
	/** setUserName - set the users name
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username.trim();
	}
}