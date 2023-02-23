import java.io.BufferedReader;
import java.io.InputStreamReader;

import Validators.*;

public class ArgumentsParser {

    public void parse(String[] args) {
        if (args.length > 1) username = args[1];
        if (args.length > 2) username = args[2];
        if (args.length > 3) username = args[3];
        
        username = ArgumentsParser.validateOrAsk(username, "Username", new AlwaysCorrectValidator());
        password = ArgumentsParser.validateOrAsk(password, "Password", new AlwaysCorrectValidator());
        ip = ArgumentsParser.validateOrAsk(ip, "IP", new IPValidator());        
    }

    public static String validateOrAsk(String valueToValidate, String textToAsk, Validator validator) {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        
        while (valueToValidate == null || !validator.validate(valueToValidate)) {
            try {
                System.out.println("Enter " + textToAsk + ": ");
                valueToValidate = buffer.readLine();
            } catch( Exception e) {
                System.out.print("Error! " + e);
            }
        }
        return valueToValidate;
    }


    public String username;
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }



    public String password;
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String ip;
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}