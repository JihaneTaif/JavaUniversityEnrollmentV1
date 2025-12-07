package model;

public class Student {
    private  int id;
    private  String name;
    private  String email;

    //Constructor
    public Student(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;

    }


    //getters & settes
    public  int getId(){   return id; }
    public void setId( int id){  this.id = id;}

    //getters & settes
    public  String getName(){   return name; }
    public void setName(String name){  this.name = name;}

    //getters & settes
    public  String getEmail(){   return email; }
    public void setEmail( String email){  this.email = email;}

}
