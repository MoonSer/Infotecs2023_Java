package Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import ru.moonser.Json.JsonObject;

public class JsonObjectTesting {
    
    @Test
    public void testJsonFromIntegerCreation() throws Exception {
        JsonObject integerObject = new JsonObject(99955);
        
        Assert.assertTrue(isInteger(integerObject));
        Assert.assertEquals(integerObject.getInteger(), new Integer(99955));
    }

    @Test
    public void testJsonFromNegativeIntegerCreation() throws Exception {
        JsonObject negativeIntegerObject = new JsonObject(-99955);
        
        Assert.assertTrue(isInteger(negativeIntegerObject));
        Assert.assertEquals(negativeIntegerObject.getInteger(), new Integer(-99955));
    }

    @Test
    public void testJsonFromStringCreation() throws Exception {
        JsonObject stringObject = new JsonObject("Привет");

        Assert.assertTrue(isString(stringObject));
        Assert.assertEquals(stringObject.getString(), "Привет");
    }

    @Test
    public void testJsonFromListCreation() throws Exception {
        // Создаём список с числами для теста
        ArrayList<Integer> intList = new ArrayList<>();
        for(int i = -10; i < 10; ++i)
            intList.add(i);
        
        // Конвертируем числа в Json-объекты
        ArrayList<JsonObject> jsonList = new ArrayList<>();
        for (int i : intList) {
            jsonList.add(new JsonObject(i));
        }

        // Конвертируем массив в Json-объект
        JsonObject listJson = new JsonObject(jsonList);

        // Проверям что список это список:
        Assert.assertTrue(isList(listJson));
        for (int i = 0; i < listJson.getList().size(); ++i) {
            JsonObject jsonObject = listJson.getList().get(i);
            
            Assert.assertTrue(isInteger(jsonObject));
            
            Assert.assertEquals(jsonObject.getInteger(), jsonList.get(i).getInteger());
            Assert.assertEquals(jsonObject.getInteger(), jsonList.get(i).getInteger());
            Assert.assertEquals(jsonObject.getInteger(), intList.get(i));
                            
        }
    }

    @Test
    public void testJsonFromMapCreation() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        map.put("Test1", -100);
        
        map.put("Test2", 100);

        Map<String, JsonObject> jsonMap = new HashMap<>();
        map.forEach((k, l) -> jsonMap.put(k, new JsonObject(l)));

        JsonObject mapJson = new JsonObject(jsonMap);

        Assert.assertTrue(isMap(mapJson));
        for (Map.Entry<String,JsonObject> it : mapJson.getMap().entrySet()) {
            Assert.assertTrue(it.getValue().isInt());
            Assert.assertEquals(it.getValue().getInteger(), map.get(it.getKey()));
        }
    }

    @Test
    public void testNULLJsonObjectEquals() throws Exception {
        Assert.assertTrue(new JsonObject().equals(new JsonObject()));
        Assert.assertFalse(new JsonObject(1).equals(new JsonObject()));
    }

    @Test
    public void testIntegerJsonObjectEquals() throws Exception {
        Assert.assertTrue(new JsonObject(-988).equals(new JsonObject(-988)));
        Assert.assertFalse(new JsonObject(2023).equals(new JsonObject(1914)));
    }

    @Test
    public void testStringJsonObjectEquals() throws Exception {
        Assert.assertTrue(new JsonObject("К-ОН").equals(new JsonObject("К-ОН")));
        Assert.assertFalse(new JsonObject("К-ОН").equals(new JsonObject("К-ОФФ")));
    }

    @Test
    public void testListJsonObjectEquals() throws Exception {
        ArrayList<JsonObject> jsonList1 = new ArrayList<>();
        jsonList1.add(new JsonObject("See"));
        jsonList1.add(new JsonObject("you"));
        jsonList1.add(new JsonObject("Space"));
        jsonList1.add(new JsonObject(1337));

        ArrayList<JsonObject> jsonList2 = new ArrayList<>();
        jsonList2.add(new JsonObject("See"));
        jsonList2.add(new JsonObject("you"));
        jsonList2.add(new JsonObject("Space"));
        jsonList2.add(new JsonObject(1337));

        Assert.assertTrue(new JsonObject(jsonList1).equals(new JsonObject(jsonList2)));

        jsonList2.add(new JsonObject("Cowboy"));
        Assert.assertFalse(new JsonObject(jsonList1).equals(new JsonObject(jsonList2)));
    }
    
    public static boolean isNull(JsonObject nullJson) {
        return  nullJson.isNull()    && 
                !nullJson.isInt()    && 
                !nullJson.isString() && 
                !nullJson.isList()   &&
                !nullJson.isMap();
    }

    public static boolean isInteger(JsonObject integerJson) {
        return  !integerJson.isNull()   && 
                integerJson.isInt()     && 
                !integerJson.isString() && 
                !integerJson.isList()   &&
                !integerJson.isMap();
    }

    public static boolean isString(JsonObject stringJson) {
        return  !stringJson.isNull()   && 
                !stringJson.isInt()    && 
                stringJson.isString()  && 
                !stringJson.isList()   &&
                !stringJson.isMap();
    }

    public static boolean isList(JsonObject listJson) {
        return  !listJson.isNull()   && 
                !listJson.isInt()    && 
                !listJson.isString() && 
                listJson.isList()    &&
                !listJson.isMap();
    }

    public static boolean isMap(JsonObject mapJson) {
        return  !mapJson.isNull()   && 
                !mapJson.isInt()    && 
                !mapJson.isString() && 
                !mapJson.isList()    &&
                mapJson.isMap();
    }
}
