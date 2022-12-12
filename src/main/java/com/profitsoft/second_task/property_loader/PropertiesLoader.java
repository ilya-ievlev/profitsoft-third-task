package com.profitsoft.second_task.property_loader;

import com.profitsoft.second_task.annotations.Property;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {

    private static final String STRING_PROPERTY = "stringProperty";
    private static final String NUMBER_PROPERTY = "numberProperty";
    private static final String TIME_PROPERTY = "timeProperty";

    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T classObj = cls.getDeclaredConstructor().newInstance();
        Map<String, String> propertiesFromFile = loadPropertiesFile(propertiesPath);
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true); // TODO: 12.12.22 temp decision
            String objectFieldName = field.getName();
            String nameInPropertyAnnotation = "";
            if (field.isAnnotationPresent(Property.class)) {
                nameInPropertyAnnotation = field.getDeclaredAnnotation(Property.class).name();
            }
            boolean isFieldNameFromObjectContainsInPropertiesFile = propertiesFromFile.containsKey(objectFieldName);
            boolean isFieldNameInAnnotationContainsInPropertiesFile = propertiesFromFile.containsKey(nameInPropertyAnnotation);


            if (objectFieldName.equals(NUMBER_PROPERTY) || nameInPropertyAnnotation.equals(NUMBER_PROPERTY)) {
                if (isFieldNameInAnnotationContainsInPropertiesFile ||
                        (nameInPropertyAnnotation.equals("") && isFieldNameFromObjectContainsInPropertiesFile)) {
                    if (objectFieldName.equals(NUMBER_PROPERTY) && isFieldNameFromObjectContainsInPropertiesFile) {
                        field.setInt(classObj, Integer.parseInt(propertiesFromFile.get(NUMBER_PROPERTY))); // TODO: 12.12.22 throw exception is not parsed
                    } else if (nameInPropertyAnnotation.equals(NUMBER_PROPERTY) && isFieldNameInAnnotationContainsInPropertiesFile) {
                        field.setInt(classObj, Integer.parseInt(propertiesFromFile.get(NUMBER_PROPERTY)));
                    } else {
                        throw new IllegalArgumentException("can't set number property");
                    }
                }
            }

            if (objectFieldName.equals(STRING_PROPERTY) || nameInPropertyAnnotation.equals(STRING_PROPERTY)) {
                if (isFieldNameInAnnotationContainsInPropertiesFile ||
                        (nameInPropertyAnnotation.equals("") && isFieldNameFromObjectContainsInPropertiesFile)) {

                    if (objectFieldName.equals(STRING_PROPERTY) && isFieldNameFromObjectContainsInPropertiesFile) {
                        field.set(classObj, propertiesFromFile.get(STRING_PROPERTY)); // TODO: 12.12.22 throw exception is not parsed
                    } else if (nameInPropertyAnnotation.equals(STRING_PROPERTY) && isFieldNameInAnnotationContainsInPropertiesFile) {
                        field.set(classObj, propertiesFromFile.get(STRING_PROPERTY));
                    } else {
                        throw new IllegalArgumentException("can't set name property");
                    }
                }
            }

            if (objectFieldName.equals(TIME_PROPERTY) || nameInPropertyAnnotation.equals(TIME_PROPERTY)) {
                if (isFieldNameInAnnotationContainsInPropertiesFile ||
                        (nameInPropertyAnnotation.equals("") && isFieldNameFromObjectContainsInPropertiesFile)) {
                    String userDateTimeFormat;
                    if (field.isAnnotationPresent(Property.class) && (!field.getAnnotation(Property.class).format().equals(""))) {
                        userDateTimeFormat = field.getAnnotation(Property.class).format();
                    } else throw new IllegalArgumentException("must be with Property annotation and user format");
                    if (objectFieldName.equals(TIME_PROPERTY) && isFieldNameFromObjectContainsInPropertiesFile) {
                        field.set(classObj, parseInstant(propertiesFromFile.get(TIME_PROPERTY), userDateTimeFormat));
                    } else if (nameInPropertyAnnotation.equals(TIME_PROPERTY) && isFieldNameInAnnotationContainsInPropertiesFile) {
                        field.set(classObj, parseInstant(propertiesFromFile.get(TIME_PROPERTY), userDateTimeFormat));
                    } else {
                        throw new IllegalArgumentException("can't set dateTime property");
                    }
                }
            }
        }
        return classObj;
    }

    private static Map<String, String> loadPropertiesFile(Path inputFile) {
        Map<String, String> propertiesMap = new HashMap<>();
        try (FileInputStream fileInputStream = new FileInputStream(String.valueOf(inputFile))) {
            Properties property = new Properties();
            property.load(fileInputStream);
            propertiesMap.put(STRING_PROPERTY, property.getProperty(STRING_PROPERTY));
            propertiesMap.put(NUMBER_PROPERTY, property.getProperty(NUMBER_PROPERTY));
            propertiesMap.put(TIME_PROPERTY, property.getProperty(TIME_PROPERTY));
        } catch (IOException e) {
            throw new IllegalArgumentException("fileInputStream error", e);
        }
        return propertiesMap;
    }

    private static Instant parseInstant(String inputString, String userFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(userFormat);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.parse(inputString, dateTimeFormatter);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime.toInstant();
    }
}