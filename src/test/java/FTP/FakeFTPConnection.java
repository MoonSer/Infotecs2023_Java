package FTP;

import ru.moonser.FTP.FTPConnection;

public class FakeFTPConnection extends FTPConnection {

    public FakeFTPConnection() {
        super("0.0.0.0", "Test", "Test");
    }
    
    @Override
    public FakeFTPClient createFTP() {
        return new FakeFTPClient();
    }
}
