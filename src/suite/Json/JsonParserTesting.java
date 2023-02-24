package suite.Json;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.Test;

import main.Json.JsonObject;
import main.Json.JsonParser;

public class JsonParserTesting {
    
    @Test
    public void  jsonFromMapParsing() throws Exception {
        StringBuilder jsonDataBuilder = new StringBuilder();
        jsonDataBuilder.append("{\n");
        jsonDataBuilder.append("\t\"кто мы?\":\"котики\",\n");
        jsonDataBuilder.append("\t\"чего мы хотим?\":\"еды и ласки\"\n");
        jsonDataBuilder.append("\t\"сколько нас?\":10000000\n");

        try{
            JsonParser.parse(new StringReader(jsonDataBuilder.toString()));
            Assert.assertTrue(false);
        }catch(Exception e) {
            Assert.assertTrue(true);
        }
        
        jsonDataBuilder.append("}");
        JsonObject jsonMap = JsonParser.parse(new StringReader(jsonDataBuilder.toString()));

        Assert.assertTrue(JsonObjectTesting.isMap(jsonMap));
        
        Assert.assertTrue(JsonObjectTesting.isString(jsonMap.get("кто мы?")));
        Assert.assertEquals(jsonMap.get("кто мы?").getString(), "котики");

        Assert.assertTrue(JsonObjectTesting.isString(jsonMap.get("чего мы хотим?")));
        Assert.assertEquals(jsonMap.get("чего мы хотим?").getString(), "еды и ласки");

        Assert.assertTrue(JsonObjectTesting.isInteger(jsonMap.get("сколько нас?")));
        Assert.assertEquals(jsonMap.get("сколько нас?").getInteger(), new Integer (10000000));        

        try{
            jsonMap.get("unexpected");
            Assert.assertTrue(false);
        }catch(Exception e) {
            Assert.assertTrue(true);
        }
    }

    
    @Test
    public void  jsonFromListParsing() throws IOException, ParseException {
        StringBuilder jsonDataBuilder = new StringBuilder();
        jsonDataBuilder.append("[\n");
        jsonDataBuilder.append("\t\"не от худа\",\n");
        jsonDataBuilder.append("\t\"без\",\n");
        jsonDataBuilder.append("\t\"добра,\n");
        jsonDataBuilder.append("\t\"-125\n");
        
        try{
            JsonParser.parse(new StringReader(jsonDataBuilder.toString()));
            Assert.assertTrue(false);
        }catch(Exception e) {
            Assert.assertTrue(true);
        }
        
        jsonDataBuilder.append("]");
        JsonObject jsonList = JsonParser.parse(new StringReader(jsonDataBuilder.toString()));

        Assert.assertTrue(JsonObjectTesting.isList(jsonList));
        
        Assert.assertTrue(JsonObjectTesting.isString(jsonList.get(0)));
        Assert.assertEquals(jsonList.get(0).getString(), "не от худа");

        Assert.assertTrue(JsonObjectTesting.isString(jsonList.get(1)));
        Assert.assertEquals(jsonList.get(1).getString(), "без");

        Assert.assertTrue(JsonObjectTesting.isString(jsonList.get(2)));
        Assert.assertEquals(jsonList.get(2).getString(), "добра");

        Assert.assertTrue(JsonObjectTesting.isInteger(jsonList.get(3)));
        Assert.assertEquals(jsonList.get(3).getInteger(), new Integer(-125));

        try{
            jsonList.get(4);
            Assert.assertTrue(false);
        }catch(Exception e) {
            Assert.assertTrue(true);
        }
    }
}
