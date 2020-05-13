package models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class News implements Comparable<News> {
    private String content;
    private String writtenBy;
    private int rating;
    private String department;
    private int id;
    private int departmentId; //will be used to connect Restaurant to News (one-to-many)
    private long createdat;
    private String formattedCreatedAt;

    public News(String content, String writtenBy, int rating,String department,  int departmentId) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.department = department;
        this.departmentId = departmentId;
        this.createdat = System.currentTimeMillis();
        setFormattedCreatedAt(); //we'll make me in a minute
    }

    @Override
    public int compareTo(News newsObject) {
        if (this.createdat < newsObject.createdat)
        {
            return -1; //this object was made earlier than the second object.
        }
        else if (this.createdat > newsObject.createdat){ //this object was made later than the second object
            return 1;
        }
        else {
            return 0; //they were made at the same time, which is very unlikely, but mathematically not impossible.
        }
    }

    public long getCreatedat() {
        return createdat;
    }

    public void setCreatedat() {
        this.createdat = System.currentTimeMillis();

    }

    public String getFormattedCreatedAt(){
        Date date = new Date(createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a"; //see https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        return sdf.format(date);
    }

    public void setFormattedCreatedAt(){
        Date date = new Date(this.createdat);
        String datePatternToUse = "MM/dd/yyyy @ K:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(datePatternToUse);
        this.formattedCreatedAt = sdf.format(date);
    }


    public String getContent() {
        return content;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public int getRating() {
        return rating;
    }

    public String getDepartment() {
        return department;
    }
    public int getId() {
        return id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartment(String department){
        this.department = department;
    }
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return rating == news.rating &&
                id == news.id &&
                departmentId == news.departmentId &&
                Objects.equals(content, news.content) &&
                Objects.equals(writtenBy, news.writtenBy)&&
                Objects.equals(department, news.department);
    }


    @Override
    public int hashCode() {
        return Objects.hash(content, writtenBy, rating, id,departmentId, departmentId);
        
    }
}
