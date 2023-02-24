package FTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.stream.Collectors;

import ru.moonser.FTP.SimpleFTPClient;

public class FakeFTPClient extends SimpleFTPClient {
    public String studentsJsonString = "{\"students\":[]}";
    
    @Override
    public BufferedReader getFileReader(String ftpFileName) {
        return new BufferedReader(new StringReader(this.studentsJsonString));
    }

    @Override
    public void loadFile(String ftpFileName, InputStream fileStream) {
        this.studentsJsonString = 
            new BufferedReader(
                new InputStreamReader(fileStream)
            ).lines()
            .collect(
                Collectors.joining("\n")
            );
    }
}
