package Json;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.security.KeyStore.Entry;
import java.util.ArrayList;

public class JsonObject {
    public static enum Type {
        NULL,
        INTEGER,
        STRING,
        MAP,
        LIST
    }
    private Object value;
    private Type valueType = Type.NULL;


    // Constructors from multiple objects
    public JsonObject(int value) {
        this.value = value;
        this.valueType = Type.INTEGER;
    }

    public JsonObject(String value) {
        if (value != null) {
            this.value = value;
            this.valueType = Type.STRING;
        }
    }

    public JsonObject(Map<String, JsonObject> value) {
        if (value != null) {
            this.value = new HashMap<String, JsonObject>(value);
            this.valueType = Type.MAP;
        }
    }

    public JsonObject(Collection<JsonObject> value) {
        if (value != null) {
            this.value = new ArrayList<JsonObject>(value);
            this.valueType = Type.LIST;
        }
    }


    // Check types
    public boolean isNull() {
        return this.valueType == Type.NULL;
    }

    public boolean isInt() {
        return this.valueType == Type.INTEGER;
    }

    public boolean isString() {
        return this.valueType == Type.STRING;
    }

    public boolean isList() {
        return this.valueType == Type.LIST;
    }
    
    public boolean isMap() {
        return this.valueType == Type.MAP;
    }

    
    // Getters for objects
    public Object getRaw() {
        return this.value;
    }

    public int getInteger() throws ClassCastException {
        return (int)this.value;
    }

    public String getString() throws ClassCastException {
        return (String)this.value;
    }
    
    @SuppressWarnings("unchecked")
    public List<JsonObject> getList() throws ClassCastException {
        return (List<JsonObject>)this.value;
    }

    @SuppressWarnings("unchecked")
    public Map<String, JsonObject> getMap() throws ClassCastException {
        return (Map<String, JsonObject>)this.value;
    }

    public JsonObject get(String key) throws ClassCastException {
        if (!this.isMap())
            throw new TypeNotPresentException("JsonObjet is not a Map", null);
        return this.getMap().get(key);
    }


    public JsonObject get(int index) throws ClassCastException {
        if (this.isList())
            return this.getList().get(index);
        throw new TypeNotPresentException("JsonObjet is not a List", null);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.isInt()){
            sb.append(this.getInteger());
        }
        else if (this.isString()){
            sb.append("\"" + this.getString() + "\"");
        }
        else if (this.isList()) {
            sb.append("[");

            boolean isFirst = true;
            for (JsonObject object : this.getList()) {
                if (!isFirst)
                    sb.append(",");
                sb.append(object.toString());
                isFirst = false;
            }
            sb.append("]");
        }
        else if (this.isMap()) {
            sb.append("{");
            
            boolean isFirst = true;
            for (Map.Entry pair : this.getMap().entrySet()) {
                if (!isFirst) 
                    sb.append(",");
                sb.append("\"" + pair.getKey() + "\"" + ":" + pair.getValue().toString());
                isFirst = false;
            }
            sb.append("}");
        }
        return sb.toString();
    }
}
