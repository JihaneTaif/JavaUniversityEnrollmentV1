package model;

public class Course {

    private int id;
    private String title;


    //Constructor
    public Course(int id, String title){
        this.id = id;
        this.title = title;


    }


    //getters & settes
    public int getId(){   return id; }
    public void setId( int id){  this.id = id;}

    //getters & settes
    public String getTitle(){   return title; }
    public void setTitle(String title){  this.title = title;}



}
