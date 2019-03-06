package com.groovy_tickets_grotto.backend;
enum Type{
    AA,
    FS,
    BS,
    SS
}
public class User
{
    private String Username;
    private Type Type;
    private double Balance;

    public User() {

    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return Balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.Balance = balance;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return Type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.Type = type;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return Username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.Username = username;
    }
}