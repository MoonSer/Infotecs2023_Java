package ru.moonser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import ru.moonser.DataClass.Student;
import ru.moonser.FTP.FTPConnection;
import ru.moonser.FTP.SimpleFTPClient;
import ru.moonser.Json.JsonParser;

public class ProgramContext {
    public boolean inWork = true;
    public FTPConnection ftpConnection;
    public Map<Integer, Student> studentsInfo;
    public BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));


    public ProgramContext(FTPConnection connection) {
        this.ftpConnection = connection;
    }


    public void setInputStream(InputStream inputStream) {
        this.inputStream = new BufferedReader(new InputStreamReader(inputStream));
    }
    

    public Map<Integer, Student> getStudents() {
        return this.studentsInfo;
    }

    public static Void findStudentsByName(ProgramContext context) {
        System.out.print("Enter Student's name: ");
        try {
            String studentName = context.inputStream.readLine();
            if (context.reloadStudents()) {
                context.printStudents(context.studentsInfo.entrySet()
                            .stream()
                            .filter(e -> e.getValue()
                                            .getName()
                                            .toLowerCase()
                                            .contains(studentName.toLowerCase()))
                            .collect(Collectors.toMap(e -> e.getKey(), e-> e.getValue()))
                            .values());
            }else{
                System.out.println("FTP Error. Can't reload student's list!");
            }
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return null;
    }

    public static Void findStudentsById(ProgramContext context) {
        System.out.print("Enter Student's id: ");
        try {
            int studentId = Integer.parseInt(context.inputStream.readLine());
            if (context.reloadStudents()) {
                context.printStudents(context.studentsInfo.entrySet()
                            .stream()
                            .filter(e -> e.getKey() == studentId)
                            .collect(Collectors.toMap(e -> e.getKey(), e-> e.getValue()))
                            .values());
            }else{
                System.out.println("FTP Error. Can't reload student's list!");
            }
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return null;
    }

    public void printStudents(Collection<Student> students) {
        if (students.size() == 0) {
            System.out.println("\nStudent's list empty!\n");
            return;
        }
        
        int i = 1;
        System.out.println("\nStudents list:");
        for (Student student : students) {
            System.out.printf("%d) Id:%d. Name: %s.\n", i, student.getId(), student.getName());
            i += 1;
        }
        System.out.println();
    }

    public static Void addStudents(ProgramContext context) {
        try {            
            System.out.print("\nEnter new student name:");
            String studentName = context.inputStream.readLine();

            context.reloadStudents();

            int studentId = 0;
            if (context.studentsInfo.size() > 0)
                studentId = Collections.max(context.studentsInfo.keySet())+1;
            
            context.studentsInfo.put(studentId, new Student(studentId, studentName));

            if (context.updateStudents()) {
                System.out.println("Success added!");
            }else{
                System.out.println("FTP Error. Can't upload users list!");
                context.studentsInfo.remove(studentId);
            }
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return null;
    }

    public static Void deleteStudentById(ProgramContext context) {
        try {
            System.out.print("\nEnter student's id to delete:");
            Integer studentId = Integer.parseInt(context.inputStream.readLine());
            
            if (!context.studentsInfo.containsKey(studentId)) {
                System.out.println("Invalid student id!");
                return null;
            }
            
            
            if (!context.reloadStudents()) 
                System.out.println("FTP Error! Can' reload students list.");
            
            Student tmp = context.studentsInfo.get(studentId);
            
            context.studentsInfo.remove(studentId);
                    
            if (!context.updateStudents()) {
                context.studentsInfo.put(studentId, tmp);
                System.out.println("FTP Error. Can't upload students list.");
            }else {
                System.out.println("Success deleted!");
            }
        }catch(Exception e){
            System.out.println("Error:" + e);
        }
        return null;
    }

    private boolean updateStudents() {
        SimpleFTPClient ftpClient = this.ftpConnection.createFTP();

        if (ftpClient == null)
            return false;

        try{
            ftpClient.loadFile("students.json", 
                new ByteArrayInputStream(Student
                        .toJson(this.studentsInfo.values())
                        .toString()
                        .getBytes()));
        }catch(Exception e) {
            return false;
        }
        return true;
    }


    public boolean reloadStudents() {
        SimpleFTPClient ftpClient = this.ftpConnection.createFTP();
        if (ftpClient == null)
            return false;
        try{
            this.studentsInfo = Student.createFromJson(
                                    JsonParser.parse(
                                            ftpClient.getFileReader("students.json")
                                    ).get("students").getList());
        }catch(Exception e) {
            return false;
        }
        return true;
    }


    public static Void exit(ProgramContext context) {
        context.inWork = false;
        return null;
    }
}
