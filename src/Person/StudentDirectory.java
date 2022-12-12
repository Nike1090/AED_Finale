/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Person;

import java.util.ArrayList;

/**
 *
 * @author Sayali
 */
public class StudentDirectory {
    private ArrayList<Student> personList;
    
    public StudentDirectory()
    {
        personList=new ArrayList<Student>();
    }

    public ArrayList<Student> getPersonList() {
        return personList;
    }

    public void setPersonList(ArrayList<Student> personList) {
        this.personList = personList;
    }
    
    public Student addPerson()
    {
        Student p=new Student();
        personList.add(p);
        return p;
    }
    
}
