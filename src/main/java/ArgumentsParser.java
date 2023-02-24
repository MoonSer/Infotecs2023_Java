package ru.moonser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ru.moonser.Validators.*;

public class ArgumentsParser {

    public String ip;
    public String username;
    public String password;

    public ArgumentsParser() {
    }

    public void parse(String[] args) {
        if (args != null && args.length == 3) {
            if (args.length > 1) ip = args[0];
            if (args.length > 2) username = args[1];
            if (args.length > 3) password = args[2];
        }else if (args.length == 4) {
            if (args.length > 1) ip = args[1];
            if (args.length > 2) username = args[2];
            if (args.length > 3) password = args[3];
        }
        
        
        ip = this.validateOrAsk(ip, "IP", new DNSorIPValidator());
        username = this.validateOrAsk(username, "Username", new AlwaysCorrectValidator());
        password = this.validateOrAsk(password, "Password", new AlwaysCorrectValidator());
    }

    public String validateOrAsk(String valueToValidate, String textToAsk, Validator validator) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        
        while (valueToValidate == null || !validator.validate(valueToValidate)) {
            try {
                System.out.print("Enter " + textToAsk + ": ");
                valueToValidate = buffer.readLine();
            } catch( Exception e) {
                System.out.print("Error! " + e);
            }
        }
        return valueToValidate;
    }


    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
