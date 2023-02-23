package DataClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Json.JsonObject;

public class Student {
    private int id;
    private String name;
    
    public Student(int id, String name) {
        this.id = id;
        this.name = name;
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

    public int getId() 
        { return id; }
    public void setId(int id) 
        { this.id = id; }

    public String getName() 
        { return name; }
    public void setName(String name) 
        { this.name = name; }

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student( id: ");
        sb.append(this.getId());
        sb.append(", name: ");
        sb.append(this.getName());
        sb.append(")");
        return sb.toString();
    }

    public static Map<Integer, Student> loadFromJson(List<JsonObject> list) {
        Map<Integer, Student> studentsMap = new HashMap<>();
        for (JsonObject studentJson : list) {
            Student student = Student.createFromMap(studentJson.getMap());
            studentsMap.put(student.getId(), student);
        }
        return studentsMap;
    }

    public static Student createFromMap(Map<String, JsonObject> map) {
        return new Student(map.get("id").getInteger(), map.get("name").getString());
    }

    
    public static JsonObject toJson(Collection<Student> students) {
        List<JsonObject> list = new ArrayList<>();
        for (Student student : students) {
            list.add(student.toJson());
        }

        Map<String, JsonObject> globalMap = new HashMap<>();
        globalMap.put("students", new JsonObject(list));
        return new JsonObject(globalMap);
    }
    
    public JsonObject toJson() {
        Map<String, JsonObject> studentsMap = new HashMap<>();
        studentsMap.put("id", new JsonObject(this.id));
        studentsMap.put("name", new JsonObject(this.name));

        return new JsonObject(studentsMap);
    }
    
}
