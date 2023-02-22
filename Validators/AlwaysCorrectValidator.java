package Validators;

public class AlwaysCorrectValidator implements Validator {

    @Override
    public boolean validate(String str) {
        return true;
    }
    
}
