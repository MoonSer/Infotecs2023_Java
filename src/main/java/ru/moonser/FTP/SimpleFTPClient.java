package ru.moonser.FTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;


public class SimpleFTPClient {
    private FtpClient ftpClient = FtpClient.create();
    
    
    public boolean connect(String ip, String username, String password) {
        try{
            this.ftpClient.connect(new InetSocketAddress(ip, 21)).login(username, password.toCharArray());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public BufferedReader getFileReader(String ftpFileName) throws FtpProtocolException, IOException {
        return new BufferedReader(new InputStreamReader(this.ftpClient.getFileStream(ftpFileName)));
    }

    public void loadFile(String ftpFileName, InputStream fileStream) throws FtpProtocolException, IOException {
        this.ftpClient.putFile(ftpFileName, fileStream);
    }

    public void enablePassive() 
        { this.ftpClient.enablePassiveMode(true); }
        
    public void enableActive() 
        { this.ftpClient.enablePassiveMode(false); }
}
