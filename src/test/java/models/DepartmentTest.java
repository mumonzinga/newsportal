package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Department testDepartment = setupDepartment();
        assertEquals("Criminology", testDepartment.getName());
    }


    public Department setupDepartment (){
        return new Department("Criminology", "Write Stories concerning criminal activites");
    }


}