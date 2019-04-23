package com.groovy_tickets_grotto.backend;

import java.lang.reflect.*;
import java.util.*;
import org.junit.Test;
import junit.framework.*;
import java.io.*;

public class SessionTest extends TestCase {
    protected Session session;
    protected User testUser1;
    protected User testUser2;
    protected TicketBatch testTicketBatch1;
    protected TicketBatch testTicketBatch2;

    /**
     * setUp Called before each test defined below is ran, this means that each test
     * also tests what's in here.
     */
    public void setUp() {
        // Sets up the session
        session = new Session();

        // Sets some users
        testUser1 = new User("admin           AA 001000.00");
        testUser2 = new User("userFS          FS 901000.00");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_Session_TestUser() {
        Field field1;
        Map<String,User> Users;

        try {
            field1 = Session.class.getDeclaredField("Users");
            field1.setAccessible(true);
            
            Users = (Map<String,User>)field1.get(Session.class);
            
            int beforeCount = Users.size();
            Session.addUser(testUser1);
            Session.addUser(testUser2);
            int afterCount = Users.size();
    
            assertEquals(beforeCount+2, afterCount);
            assertEquals(testUser1, Session.GetUserByName("admin") );
            assertEquals(testUser2, Session.GetUserByName("userFS") );
    
            Session.RemoveUser( testUser1.getUsername() );
    
            assertEquals(afterCount-1, Users.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test_Session_TestTicketBatch() {
        Session.addUser(testUser1);

        //Sets some tickbatches
        testTicketBatch1 = new TicketBatch("Test Event Title          admin           100 025.00");
        testTicketBatch2 = new TicketBatch("Test Event Title          userSS          100 025.00");

        Session.AddTicketBatch( testTicketBatch1 );

        assertEquals(testTicketBatch1, Session.getTicketBatch("Test Event Title"+"admin"));
    }


    @Test
    public void test_Session_InstanceMethods() {
        session.setCurrentUser(testUser2);

        assertEquals(testUser2, session.getCurrentUser() );
    }

    
    
    @Test
    /**
     * This is the loop, decision and integration test of the system.
     */
    public void test_Session_Main() {
        String[] fileLocations = {
            "src/test/CurrentUserAccounts.txt",
            "src/test/AvailableTickets.txt",
            "src/test/mergedtransactions.trn",
            "src/test/new_users.txt",
            "src/test/new_tickets.txt"
        };

        Session.main( fileLocations );

        assertTrue( compareFile("src/test/expected_tickets.txt","src/test/new_tickets.txt") );

        assertTrue( compareFile("src/test/expected_users.txt","src/test/new_users.txt") );

        File delme = new File("src/test/new_tickets.txt");
        delme.delete();

        delme = new File("src/test/new_users.txt");
        delme.delete();
    }

    private boolean compareFile( String expectedPath, String actualPath )
    {
        ArrayList<String> expected = readFile( expectedPath );
        ArrayList<String> actual   = readFile( actualPath );

        Collections.sort(expected);
        Collections.sort(actual);

        return expected.equals(actual);
    } 

    private ArrayList<String> readFile( String path )
    {
        ArrayList<String> ret = new ArrayList<String>();

        BufferedReader reader = null;
		// create a buffered reader, and then try to open a file and read the file line by line
		try {
			File file   = new File( path );
			reader      = new BufferedReader(new FileReader(file));
			String line;

			// Loop over each line, creating a user object and placing that object in the Users map
			while ( (line = reader.readLine()) != null ) {
                ret.add( line );
			}
            reader.close();
		} 
		catch (IOException ex)
		{
            fail("error in checking files");
        } 

        return ret;
    }

    /**
     * The following code is to access and clean up private variables stored within the Session class. 
     *  using reflection, i get a reference to the private field i want to access, set it's accessibility to true, then poll that field for the data
     *  then I cast that data from an 'Object' back to the map i know it is. 
     * Then the map gets cleared.
     */    
    @SuppressWarnings("unchecked")
    public void tearDown()
    {
        try {
            Field field1 = Session.class.getDeclaredField("Users");
            field1.setAccessible(true);
            
            Map<String,User> Users = (Map<String,User>)field1.get(Session.class);
    
            Users.clear();


            Field field2 = Session.class.getDeclaredField("Tickets");
            field2.setAccessible(true);

            Map<String, TicketBatch> Tickets = (Map<String, TicketBatch>)field2.get(Session.class);
    
            Tickets.clear();
        }
        catch (Exception e) {
            assertNotNull(e);
        }
    }
}    