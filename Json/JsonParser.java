package Json;


import java.io.IOException;
import java.text.ParseException;
import java.io.Reader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/* К глубочайшему сожалению, 
    Java SE 8-й версии "из коробки" не поддерживает Json */
/* Так что парсим ручками  */

public class JsonParser {

    public static JsonObject parse(String filePath) throws IOException, ParseException {
        return JsonParser.parse(new FileReader(filePath));
    }


    public static JsonObject parse(Reader reader) throws IOException, ParseException {
        BufferedReader bufferedReader = null;
        
        if (reader instanceof BufferedReader)
            bufferedReader = (BufferedReader)reader;
        else
            bufferedReader = new BufferedReader(reader);
        
        return JsonParser.parseValue(bufferedReader);
    }

    
    private static JsonObject parseValue(BufferedReader bufferedReader) throws IOException, ParseException {
        int readedSymbol = -1;
        
        bufferedReader.mark(1);
        while((readedSymbol=bufferedReader.read()) != -1) {
            char readedChar = (char)readedSymbol;
            
            if (Character.isWhitespace(readedChar)) {
                bufferedReader.mark(1);
                continue;
            }

            switch (readedChar) {
                case '"':
                    return JsonParser.parseString(bufferedReader);
                
                case '[':
                    return JsonParser.parseList(bufferedReader);
                
                case '{':
                    return JsonParser.parseMap(bufferedReader);
                
                default:
                    if (readedChar == '-' || Character.isDigit(readedChar)) {
                        bufferedReader.reset();
                        return JsonParser.parseInteger(bufferedReader);
                    }
                    throw new ParseException("Unexpected symbol: \""+readedChar+"\"", -1);
            }
        }
        throw new ParseException("Unexpected EOF", -1);
    }
    

    private static JsonObject parseMap(BufferedReader bufferedReader) throws IOException, ParseException {
        Map<String, JsonObject> map = new HashMap<String, JsonObject>();
        
        String currentKey = null;
        boolean isComma = false;

        int currentSymbol = -1;
        while ((currentSymbol = bufferedReader.read()) != -1) {
            char currentChar = (char)currentSymbol;
            
            if (Character.isWhitespace(currentChar)) continue;

            switch(currentChar) {
                case '"':
                    if (currentKey != null)
                        throw new ParseException("Unexpected string", -1);
                    currentKey = JsonParser.parseString(bufferedReader).getString();
                    isComma = false;
                    break;
                
                case ',':
                    isComma = true;
                    break;
                
                case '}':
                    if (isComma)
                        throw new ParseException("Unexpected \"}\". Expected value after comma", -1);
                    return new JsonObject(map);
                
                case ':':
                    if (isComma)
                        throw new ParseException("Unexpected \":\". Expected value after comma", -1);
                    map.put(currentKey, JsonParser.parseValue(bufferedReader));
                    currentKey = null;
                    isComma = false;
                    break;
                
                default:
                    throw new ParseException("Unexpected symbol: \"" + currentChar + "\"", -1);
            }
        }
        throw new ParseException("Unexpected EOF.", -1);
        
    }


    private static JsonObject parseList(BufferedReader bufferedReader) throws IOException, ParseException {
        List<JsonObject> list = new ArrayList<JsonObject>();

        int currentSymbol = -1;
        boolean isComma = false;
        bufferedReader.mark(1);
        while((currentSymbol = bufferedReader.read()) != -1) {
            
            char currentChar = (char)currentSymbol;
            if (Character.isWhitespace(currentChar)) {
                bufferedReader.mark(1);
                continue;
            }

            switch(currentChar) {
                case ']':
                    if (isComma)
                        throw new ParseException("Unexpectede end of list", -1);
                    return new JsonObject(list);
                
                case ',':
                    if (isComma)
                        throw new ParseException("Doubled comma", -1);
                    isComma = true;
                    break;
                
                default:
                    bufferedReader.reset();
                    list.add(JsonParser.parseValue(bufferedReader));
                    isComma = false;
                    break;
            }
            bufferedReader.mark(1);
        }
        
        return null;    
    }
    
    private static JsonObject parseString(BufferedReader bufferedReader) throws IOException, ParseException {
        StringBuilder stringBuilder = new StringBuilder();
    
        int currentSymbol = -1;
        char prevChar = ' ';
        while ((currentSymbol = bufferedReader.read()) != -1) {            
            char currentChar = (char)currentSymbol;

            if ((currentChar == '"' || currentChar == '\'') && prevChar != '\\')
                return new JsonObject(stringBuilder.toString());
            
            stringBuilder.append(currentChar);
            prevChar = currentChar;
        }        
        throw new ParseException("Unexpected EOF", -1);
    }
    
    private static JsonObject parseInteger(BufferedReader bufferedReader) throws IOException, ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        
        int currentSymbol = -1;    
        bufferedReader.mark(1);
        while((currentSymbol = bufferedReader.read()) != -1) {
            char currentChar = (char)currentSymbol;
            
            
            if (Character.isDigit(currentChar) || currentChar == '-') {
                stringBuilder.append(currentChar);
            }else {
                bufferedReader.reset();
                break;
            }
            
            
            bufferedReader.mark(1);
        }
        
        String numberAsString = stringBuilder.toString();
        try{
            return new JsonObject(Integer.parseInt(numberAsString));
        }catch(Exception e) {
            throw new ParseException("Unresolved number: \"" + numberAsString + "\"", -1);
        }
    }
    
}
