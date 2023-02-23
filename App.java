import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import Json.JsonObject;
import Json.JsonParser;

public class App {  
    public static void main(String[] args) throws IOException, ParseException {
        ArgumentsParser parser = new ArgumentsParser();
        parser.setIp("93.189.41.9");
        parser.setUsername("u288847");
        parser.setPassword("sc3JV8Ou8Q6g");
        // parser.parse(args);
        

        SimpleFTPClient ftpClient = new SimpleFTPClient();
        if (!ftpClient.connect(parser.getIp(), parser.getUsername(), parser.getPassword())) {
            System.out.println("Can't connect to server");
            return;
        }

        JsonObject obj = JsonParser.parse("students.json");
        obj.getList().get(0).getMap().put("Hello", new JsonObject("Yeah"));

            /*
 * public FtpClient getFile(String name,
    OutputStream local) throws FtpProtocolException, IOException 
Retrieves a file from the ftp server and writes it to the specified OutputStream. If the restart offset was set, then a REST command will be sent before the RETR in order to restart the tranfer from the specified offset. The OutputStream is not closed by this method at the end of the transfer.
 public InputStream getFileStream(String name) throws FtpProtocolException, IOException 
 */

        // try{
        //     Files.lines(new File("students.json").toPath());
        // }catch(Exception e) {
        //     System.out.println(e);
        // }
    }
}