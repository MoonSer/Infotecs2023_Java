package suite.Menu;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import main.Menu.MenuItem;

public class MenuItemTesting {

    private Integer simpleInt;

    @BeforeTest
    public void setUpSimpleInteger () {
        this.simpleInt = new Integer(-228);
    }
    
    @Test
    public void testExecute() {
        
        MenuItem<Integer> menuItem = new MenuItem<Integer>("Test", integer -> exampleCall(integer));
        try {
            menuItem.execute(this.simpleInt);
        }catch (Exception exception) {
            Assert.assertTrue(false);
        }
    }

    
    public Void exampleCall(Integer integer) {
        Assert.assertTrue(simpleInt == integer);
        return null;
    }
}
