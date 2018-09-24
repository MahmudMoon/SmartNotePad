package com.example.moon.smartnotepad;

public class ObjectForNotes  {
    private int id;
   private String date;
   private String title;
   private String details;

    public ObjectForNotes() {
    }

    public ObjectForNotes(int id,String date, String title, String details) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.details = details;
    }

    public ObjectForNotes(String date, String title, String details) {
        this.date = date;
        this.title = title;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
