package org.example;

import java.util.Collection;
import java.util.Map;

public abstract class Stringify {
    abstract String numberToJsonString(Number value);
    abstract String stringToJsonString(String string);
    abstract String booleanToJsonString(Boolean bool);
    abstract String arrayToJsonString(Object[] array);
    abstract String collectionToJsonString(Collection collection);
    abstract String objectToJsonString(Object object);
    abstract String mapToHashString(Map map);
    abstract String nullToJsonString();
    public String valueToJsonString(Object object) {
        if (object instanceof Number) {
            return this.numberToJsonString((Number) object);
        }
        if (object instanceof String) {
            return this.stringToJsonString((String) object);
        }
        if (object instanceof Boolean) {
            return this.booleanToJsonString((Boolean) object);
        }
        if (object instanceof Object[]) {
            return this.arrayToJsonString((Object[]) object);
        }
        if (object instanceof Map){
            return this.mapToHashString((Map) object);
        }
        if (object instanceof Collection) {
            return this.collectionToJsonString((Collection) object);
        }
        if (object == null){
            return this.nullToJsonString();
        }
        return this.objectToJsonString(object);
    }
}
