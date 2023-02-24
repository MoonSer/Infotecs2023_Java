package Program;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import FTP.FakeFTPConnection;

import ru.moonser.ProgramContext;
import ru.moonser.DataClass.Student;

public class ProgramContextTesting {
    private ProgramContext programContext;

    @BeforeTest
    private void setUpProgramContext() {
        this.programContext = new ProgramContext(new FakeFTPConnection());
    }
    
    @Test
    public void addStudentTest() {
        this.addStudent("Donnovan");

        Map<Integer, Student> students = this.programContext.getStudents();
        Assert.assertEquals(students.size(), 1);
        Assert.assertEquals(students.get(0), new Student(0, "Donnovan"));
    }

    @Test
    public void deleteStudentTest() {
        this.addStudent("Donnovan");


        String userIdToDelete = "0";
        this.programContext.setInputStream(new ByteArrayInputStream(userIdToDelete.getBytes()));

        ProgramContext.deleteStudentById(this.programContext);
        
        Assert.assertEquals(this.programContext.getStudents().size(), 0);
    }

    public void addStudent(String studentName) {
        this.programContext.setInputStream(new ByteArrayInputStream(studentName.getBytes()));
        
        ProgramContext.addStudents(this.programContext);
    }
}
