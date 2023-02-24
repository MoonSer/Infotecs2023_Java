package ArgumentParse;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.moonser.ArgumentsParser;

public class ArgumentParserTesting {

    @Test
    public void correctIpLoginPasswordParsing() {
        String ip = "192.168.0.1";
        String username = "moonser";
        String password = "jff3a29";

        String[] args = new String[]{"path", ip, username, password};

        ArgumentsParser parser = new ArgumentsParser();
        parser.parse(args);

        Assert.assertEquals(ip, parser.getIp());
        Assert.assertEquals(username, parser.getUsername());
        Assert.assertEquals(password, parser.getPassword());
    }
}
