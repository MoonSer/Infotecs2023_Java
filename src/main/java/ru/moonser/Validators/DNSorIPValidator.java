package ru.moonser.Validators;

import java.net.InetAddress;

public class DNSorIPValidator implements Validator {
    @Override
    public boolean validate(final String host) {
        try{ 
            InetAddress.getByName(host);
            return true;
        }catch(Exception e) {
            String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";   
            return (host != null && host.matches(PATTERN));    
        }
    }
}
