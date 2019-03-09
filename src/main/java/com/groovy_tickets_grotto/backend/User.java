package com.groovy_tickets_grotto.backend;
public class User
{
    public enum UserType{
        AA,
        FS,
        BS,
        SS
    }

    private String username;
    private UserType userType;
    private double balance;

    public User() {

    }

    /** File Line constructor
     * this constructor takes a line of text as found in the Current Users File and parses it into a valid user
     * @param line the line of the file
     */
    public User( String line ) {
        System.out.println("usr constrcutor: " + line);
        setUsername( line.substring(0 , 15) );
        setType( line.substring( 16 , 18) );
        setBalance( line.substring(19, 28) );
    }
    
    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /** setBalance
     * @param balance the balance to set
     */
    public void setBalance(float balance) {
        this.balance = balance;
    }
    /** setBalance
     * @param balance the string balance to parse then set
     */
    public void setBalance(String balance) {
        this.balance = Float.parseFloat( balance );
    }

    /**
     * @return the type
     */
    public UserType getType() {
        return userType;
    }

    /** setUserType
     * @param type the type to set
     */
    public void setType(UserType type) {
        this.userType = type;
    }

    /** setUserType
     * @param type the type to set
     */
    public void setType(String type) {
        // parse passed string into enum
        this.userType = UserType.valueOf( type );
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username.trim();
    }
    /**
     * Returns the users in proper string format to be saved
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
}