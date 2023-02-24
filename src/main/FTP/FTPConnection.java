package main.FTP;

public class FTPConnection {
    private String ip;
    private String username;
    private String password;
    private boolean passiveMode;
    
    public FTPConnection(String ip, String username, String password) {
        this.ip = ip;
        this.username = username;
        this.password = password;
    }

    public void enablePassiveMode(boolean enable) {
        this.passiveMode = enable;
    }

    public SimpleFTPClient createFTP() {
        SimpleFTPClient client = new SimpleFTPClient();
        if (this.passiveMode)
            client.enablePassive();
        if (client.connect(ip, username, password))
            return client;
        return null;
    }
}
