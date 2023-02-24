package suite.Validators;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.Validators.IPValidator;

public class IPValidatorTesting {
    
    @Test
    public void IPVAlidatorSuccess() {
        for (int i = 0; i < 256; ++i)
            Assert.assertTrue(new IPValidator().validate(String.format("%d.%d.%d.%d", i, i, i, i)));
        Assert.assertTrue(new IPValidator().validate("1.2.3.4"));
    }

    @Test
    public void IPValidatorFailure() {
        Assert.assertFalse(new IPValidator().validate("192.168.0.256"));
    }
}
