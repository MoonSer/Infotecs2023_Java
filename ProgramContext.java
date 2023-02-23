
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import FTP.FTPConnection;
import FTP.SimpleFTPClient;
import Json.JsonParser;
import sun.net.ftp.FtpProtocolException;
import DataClass.Student;

public class ProgramContext {
    public boolean inWork = true;
    public FTPConnection ftpConnection;
    public Map<Integer, Student> studentsInfo;
    public BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));


    ProgramContext(FTPConnection connection) {
        this.ftpConnection = connection;
    }
    

    public static Void findStudentsByName(ProgramContext context) {
        System.out.print("Enter Student's name: ");
        try {
            String studentName = context.inputStream.readLine();
            ProgramContext.reloadStudents(context);
            ProgramContext.printStudents(context.studentsInfo.entrySet()
                            .stream()
                            .filter(e -> e.getValue()
                                            .getName()
                                            .toLowerCase()
                                            .contains(studentName.toLowerCase()))
                            .collect(Collectors.toMap(e -> e.getKey(), e-> e.getValue()))
                            .values());
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return null;
    }

    public static Void findStudentsById(ProgramContext context) {
        System.out.print("Enter Student's id: ");
        try {
            int studentId = Integer.parseInt(context.inputStream.readLine());
            ProgramContext.reloadStudents(context);
            ProgramContext.printStudents(context.studentsInfo.entrySet()
                            .stream()
                            .filter(e -> e.getKey() == studentId)
                            .collect(Collectors.toMap(e -> e.getKey(), e-> e.getValue()))
                            .values());
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return null;
    }

    public static void printStudents(Collection<Student> students) {
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

            ProgramContext.reloadStudents(context);

            int studentId = Collections.max(context.studentsInfo.keySet())+1;
            context.studentsInfo.put(studentId, new Student(studentId, studentName));

            ProgramContext.updateStudents(context);
        }catch (Exception e) {
            System.out.println("Error:" + e);
        }
        return null;
    }

    public static Void deleteStudentById(ProgramContext context) {
        try {
            System.out.print("\nEnter student's id to delete:");
            Integer studentId = Integer.parseInt(context.inputStream.readLine());

            ProgramContext.reloadStudents(context);

            context.studentsInfo.remove(studentId);

            ProgramContext.updateStudents(context);
        }catch(Exception e){
            System.out.println("Error:" + e);
        }
        return null;
    }

    private static void updateStudents(ProgramContext context) throws FtpProtocolException, IOException {
        SimpleFTPClient ftpClient = context.ftpConnection.createFTP();
        ftpClient.loadFile("students.json", 
                new ByteArrayInputStream(Student
                        .toJson(context.studentsInfo.values())
                        .toString()
                        .getBytes()));
    }


    public static void reloadStudents(ProgramContext context) throws Exception {
        SimpleFTPClient ftpClient = context.ftpConnection.createFTP();
        if (ftpClient == null)
            throw new Exception("Error. Can't connect to FTP server");
        context.studentsInfo = Student.loadFromJson(JsonParser.parse(ftpClient.getFileReader("students.json")).get("students").getList());
    }


    public static Void exit(ProgramContext context) {
        context.inWork = false;
        return null;
    }
}
