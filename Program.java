import Menu.MenuController;

public class Program {

    private ProgramContext context;
    private MenuController<ProgramContext> menu;
    

    Program(ProgramContext context) {
        this.context = context;
        this.menu = new MenuController<ProgramContext>(this.context);
        this.menu.addAction("Get Students list by name", c -> ProgramContext.findStudentsByName(c));
        this.menu.addAction("Get student info by id", c -> ProgramContext.findStudentsById(c));
        this.menu.addAction("Add students", c -> ProgramContext.addStudents(c));
        this.menu.addAction("Delete student by id", c -> ProgramContext.deleteStudentById(c));
        this.menu.addAction("Exit", c -> ProgramContext.exit(c));
    }

    public void enablePassiveFtpMode(boolean enable) {
        this.context.ftpConnection.enablePassiveMode(enable);
    }
 
    public void handleUserInput() {
        try {
            while (this.context.inWork) { 
                this.menu.print();
                
                int index = this.menu.getUserInput(this.context.inputStream);
                if (index != -1)
                    this.menu.execute(index);
            }
        }catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
