package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getName() {
        Users testUsers = setupUsers();
        assertEquals("Tee", testUsers.getName());
    }

    @Test
    public void setName() {
        Users testUsers = setupUsers();
        testUsers.setName("Tee");
        assertNotEquals("Joyce", testUsers.getName());
    }

    @Test
    public void setId() {
        Users testUsers = setupUsers();
        testUsers.setId(5);
        assertEquals(5, testUsers.getId());
    }

    // helper
    public Users setupUsers(){
        return new Users("Tee", "214 NE Broadway","97232", "503-402-9874", "tee@tee.com", "Editor", "Edit", "Forensics" );
    }

    @Test
    public void getAddressReturnsCorrectAddress() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("214 NE Broadway", testUsers.getAddress());
    }

    @Test
    public void getZipReturnsCorrectZip() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("97232", testUsers.getZipcode());
    }
    @Test
    public void getPhoneReturnsCorrectPhone() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("503-402-9874", testUsers.getPhone());
    }


    @Test
    public void getEmailReturnsCorrectEmail() throws Exception {
        Users testUsers = setupUsers();
        assertEquals("tee@tee.com", testUsers.getEmail());
    }

    @Test
    public void setAddressSetsCorrectAddress() throws Exception {
        Users testUsers = setupUsers();
        testUsers.setAddress("6600 NE Indore");
        assertNotEquals("214 NE Broadway", testUsers.getAddress());
    }

    @Test
    public void setZipSetsCorrectZip() throws Exception {
        Users testUsers = setupUsers();
        testUsers.setZipcode("78902");
        assertNotEquals("97232", testUsers.getZipcode());
    }
    @Test
    public void setPhoneSetsCorrectPhone() throws Exception {
        Users testUsers = setupUsers();
        testUsers.setPhone("971-555-567");
        assertNotEquals("503-402-9874", testUsers.getPhone());
    }


    @Test
    public void setEmailSetsCorrectEmail() throws Exception {
        Users testUsers = setupUsers();
        testUsers.setEmail("tee@ngugi.com");
        assertNotEquals("joyce@joyce.com", testUsers.getEmail());
    }


}