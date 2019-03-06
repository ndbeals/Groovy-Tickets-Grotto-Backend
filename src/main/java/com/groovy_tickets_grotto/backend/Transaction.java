package com.groovy_tickets_grotto.backend;
import java.util.*;
public abstract class Transaction
{   
    private User CurrentUser;
    
    abstract void runTransaction( Session sess );
    public static void parse(String transaction)
    {

    }
}