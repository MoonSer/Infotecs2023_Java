package DataClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Student {
    private int id;
    private String name;
    
    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<Student> createFromMap(Map<String, Object> map) {
        List<Student> students = new ArrayList<Student>();

        map.forEach((id, name) -> {
            System.out.println("=================");
            System.out.println(id);
            System.out.println(name);
        });

        return students;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        
        if (obj == null || obj.getClass() != this.getClass())
            return false;
        Student student = (Student)obj;
        return this.id == student.id && this.name == student.getName();
    }

    // id Setter/Getter
    public int getId() 
        { return id; }
    
    public void setId(int id) 
        { this.id = id; }

    // name Setter/Getter
    public String getName() 
        { return name; }
    
    public void setName(String name) 
        { this.name = name; }
    
}
