import FTP.FTPConnection;
import FTP.SimpleFTPClient;

public class App {  
    public static void main(String[] args) {
        ArgumentsParser parser = new ArgumentsParser();

        // Example values
        parser.setIp("93.189.41.9");
        parser.setUsername("u288847");
        parser.setPassword("sc3JV8Ou8Q6g");
        
        // Check FTP Connection
        if (new SimpleFTPClient().connect(parser.ip, parser.username, parser.password)){
            System.out.println("Successfully connected to FTP!");
        }else{
            System.out.println("Error! Can't connect to the server");
            return;
        }        

        Program p = new Program(new ProgramContext(new FTPConnection(parser.ip, parser.username, parser.password)));
        p.handleUserInput();
    }
}