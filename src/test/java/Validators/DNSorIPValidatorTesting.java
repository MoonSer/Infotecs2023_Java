package Validators;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.moonser.Validators.DNSorIPValidator;

public class DNSorIPValidatorTesting {
    
    @Test
    public void IPVAlidatorSuccess() {
        for (int i = 0; i < 256; ++i)
            Assert.assertTrue(new DNSorIPValidator().validate(String.format("%d.%d.%d.%d", i, i, i, i)));
        Assert.assertTrue(new DNSorIPValidator().validate("1.2.3.4"));
    }

    @Test
    public void IPValidatorFailure() {
        Assert.assertFalse(new DNSorIPValidator().validate("192.168.0.256"));
        Assert.assertFalse(new DNSorIPValidator().validate("ftp@.bytehost17.com"));
    }
}
