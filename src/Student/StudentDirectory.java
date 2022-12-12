/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Student;

import java.util.ArrayList;

/**
 *
 *  
 */
public class StudentDirectory {
    private ArrayList<Student> studentList;
    
    public StudentDirectory()
    {
        studentList=new ArrayList<Student>();
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(ArrayList<Student> studentList) {
        this.studentList = studentList;
    }
    
    public Student addStudent()
    {
        Student p=new Student();
        studentList.add(p);
        return p;
    }
    
}
