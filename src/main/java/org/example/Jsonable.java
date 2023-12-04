package org.example;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Jsonable extends Stringify {

    @Override
    protected String numberToJsonString(Number value) {
        return value.toString();
    }

    @Override
    String stringToJsonString(String string) {
        return "\"" + string + "\"";
    }

    @Override
    String booleanToJsonString(Boolean bool) {
        return bool.toString();
    }

    @Override
    String arrayToJsonString(Object[] array) {
        switch (array.length) {
            case 0:
                return "{}";
            case 1:
                return "{" + valueToJsonString(array[0]) + "}";
        }
        List<Object> objectList = Arrays.stream(array).map(this::valueToJsonString).collect(Collectors.toList());
        return objectList.toString().replace('[', '{').replace(']', '}');
    }

    @Override
    String collectionToJsonString(Collection collection) {
        Object[] values = collection.toArray();
        return this.arrayToJsonString(values);
    }

    @Override
    String objectToJsonString(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        switch (fields.length) {
            case 0:
                return "{}";
            case 1:
                return "{" + getValueField(object, fields[0]) + "}";
        }
        List<String> valueFields = new ArrayList<>();
        String result;
        for (Field field : fields) {
            result = getValueField(object, field);
            if (result != null) {
                valueFields.add(result);
            }
        }
        return valueFields.toString().replace('[', '{').replace(']', '}');
    }

    @Override
    String mapToHashString(Map map) {
        switch (map.size()) {
            case 0:
                return "{}";
            case 1:
                List<Map.Entry<Object, Object>> entryList = new ArrayList<>(map.entrySet());
                Map.Entry<Object, Object> firstEntry = entryList.get(0);
                return "{" + this.valueToJsonString(firstEntry.getKey()) + ": " + this.valueToJsonString(firstEntry.getValue()) + "}";
        }
        List<String> result = new ArrayList<>();
        List<Map.Entry<Object, Object>> entryList = new ArrayList<>(map.entrySet());
        Map.Entry<Object, Object> entry;
        for (int i = 0; i < entryList.size(); i++) {
            entry = entryList.get(i);
            result.add("{" + this.valueToJsonString(entry.getKey()) + ": " + this.valueToJsonString(entry.getValue()) + "}");
        }
        return result.toString().replace('[', '{').replace(']', '}');
    }

    @Override
    String nullToJsonString() {
        return "null";
    }

    private String getValueField(Object object, Field field) {
        try {
            Object valueField = field.get(object);
            return "\"" + field.getName() + "\": " + this.valueToJsonString(valueField);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
