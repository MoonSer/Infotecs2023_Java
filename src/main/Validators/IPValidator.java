package main.Validators;

public class IPValidator implements Validator {
    @Override
    public boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";   
        return (ip != null && ip.matches(PATTERN));
    }
}