import java.net.InetSocketAddress;

import sun.net.ftp.FtpClient;

public class FTPClient {
    private FtpClient ftpClient = FtpClient.create();
    
    
    public boolean connect(String ip, String username, String password) {
        try{
            this.ftpClient.connect(new InetSocketAddress(ip, 21)).login(username, password.toCharArray());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void enablePassive() {
        this.ftpClient.enablePassiveMode(true);
    }

    public void enableActive() {
        this.ftpClient.enablePassiveMode(false);
    }
}
