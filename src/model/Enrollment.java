package model;

public class Enrollment {

    private int id;
    private int studentId;
    private int courseId;

    //Constructor
    public Enrollment(int id, int studentId, int courseId){
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;

    }

    // contructor override in case id was genereted automatically
    public Enrollment ( int studentId, int courseId){
        this.studentId = studentId;
        this.courseId = courseId;

    }


    //getters & settes
    public int getId(){   return id; }
    public void setId( int id){  this.id = id;}

    //getters & settes
    public int getStudentId(){   return studentId; }
    public void setName(int name){  this.studentId = studentId;}

    //getters & settes
    public int getCourse_id(){   return courseId; }
    public void setCourse_id( int course_id){  this.courseId = courseId;}
}

