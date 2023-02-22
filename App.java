import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Files;

public class App {  
    public static void main(String[] args) {
        ArgumentsParser parser = new ArgumentsParser();
        parser.setIp("93.189.41.9");
        parser.setUsername("u288847");
        parser.setPassword("sc3JV8Ou8Q6g");
        // parser.parse(args);
        

        FTPClient ftpClient = new FTPClient();
        if (!ftpClient.connect(parser.getIp(), parser.getUsername(), parser.getPassword())) {
            System.out.println("Can't connect");
            return;
        }


    }

}