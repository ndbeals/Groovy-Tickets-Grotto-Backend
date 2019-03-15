package com.groovy_tickets_grotto.backend;
import junit.framework.*;

public class UserTest extends TestCase
{
    protected User user;
    protected String fileLine;
    protected User fUser;

    /** setUp
     * Called before each test defined below is ran, this means that each test also tests what's in here.
     */
    public void setUp() {
        user = new User("test","AA",1234.56f);

        fileLine = "userDeleteTest  FS 001000.00";
        fUser = new User(fileLine);
    }

    /**
     * Loop coverage for User class.
     */
    public void testToString() {
        assertEquals( fileLine, fUser.toString());
        assertEquals( "test            AA 001234.56", user.toString() );
    }

    public void testAttributes() {
        assertEquals( "userDeleteTest" , fUser.getUsername() );
        assertEquals( User.UserType.FS , fUser.getType() );
        assertEquals( 1000.0f , fUser.getBalance() );

        assertEquals(user.getBalance(), 1234.56f);

        user.setUsername("test2");
        user.setType(User.UserType.BS);
        user.setBalance(64375.62f);

        assertEquals( "test2" , user.getUsername());
        assertEquals( User.UserType.BS , user.getType() );
        assertEquals( 64375.62f , user.getBalance() );
    }
    
    
    public void testUser()
    {
        // fail("test");
    }

    public void testUser2()
    {
        // fail("te2st2");
    }
}