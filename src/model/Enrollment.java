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
    public void setStudentId(int studentId){  this.studentId = studentId;}

    //getters & settes
    public int getCourseId(){   return courseId; }
    public void setCourseId( int course_id){  this.courseId = courseId;}
}

