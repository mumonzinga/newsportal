package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getContent() {
        News testNews = setupNews();
        assertEquals("Forensics Today", testNews.getContent());
    }

    @Test
    public void setContent() {
        News testNews = setupNews();
        testNews.setContent("Black is real");
        assertNotEquals("Forensics today", testNews.getContent());
    }

    @Test
    public void getWrittenBy() {
        News testNews = setupNews();
        assertEquals("Tee", testNews.getWrittenBy());
    }

    @Test
    public void setWrittenBy() {
        News testNews = setupNews();
        testNews.setWrittenBy("Mike");
        assertNotEquals("Tee", testNews.getWrittenBy());
    }

    @Test
    public void getRating() {
        News testNews = setupNews();
        assertEquals(5, testNews.getRating());
    }

    @Test
    public void setRating() {
        News testNews = setupNews();
        testNews.setRating(1);
        assertNotEquals(4, testNews.getRating());
    }

    @Test
    public void getDepartmentId() {
        News testNews = setupNews();
        assertEquals(1, testNews.getDepartmentId());
    }

    @Test
    public void setDepartmentId() {
        News testNews = setupNews();
        testNews.setDepartmentId(10);
        assertNotEquals(1, testNews.getDepartmentId());
    }

    @Test
    public void setId() {
        News testNews = setupNews();
        testNews.setId(5);
        assertEquals(5, testNews.getId());
    }

    // helper
    public News setupNews (){
        return new News("Forensics Today", "Tee",5, "Criminology", 234);
    }

}