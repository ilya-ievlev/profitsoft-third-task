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
    private static final String DEFAULT_NAME_IN_ANNOTATION = "";
    private static final String CANT_SET_NUMBER_PROPERTY = "can't set number property";
    private static final String CANT_SET_NAME_PROPERTY = "can't set name property";
    private static final String CANT_SET_DATE_TIME_PROPERTY = "can't set dateTime property";
    private static final String FILE_INPUT_STREAM_ERROR = "fileInputStream error";
    private static final String MUST_BE_WITH_PROPERTY_ANNOTATION_AND_USER_FORMAT = "must be with Property annotation and user format";
    private static final String DEFAULT_FORMAT_IN_ANNOTATION = "";
    private static final String WRONG_PROPERTIES_FILE = "wrong properties file";

    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T classObj = cls.getDeclaredConstructor().newInstance();
        Map<String, String> propertiesFromFile = loadPropertiesFile(propertiesPath);
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String objectFieldName = field.getName();
            String nameInPropertyAnnotation = DEFAULT_NAME_IN_ANNOTATION;
            if (field.isAnnotationPresent(Property.class)) {
                nameInPropertyAnnotation = field.getDeclaredAnnotation(Property.class).name();
            }
            boolean isFieldNameFromObjectContainsInPropertiesFile = propertiesFromFile.containsKey(objectFieldName);
            boolean isFieldNameInAnnotationContainsInPropertiesFile = propertiesFromFile.containsKey(nameInPropertyAnnotation);

            parseNumberProperty(field, classObj, propertiesFromFile);
            parseStringProperty(field, classObj, propertiesFromFile);
            parseDateProperty(field, classObj, propertiesFromFile);

        }
        return classObj;
    }

    private static void parseNumberProperty(Field field, Object classObj, Map<String, String> propertiesFromFile) throws IllegalAccessException {
        String objectFieldName = field.getName();
        String nameInPropertyAnnotation = DEFAULT_NAME_IN_ANNOTATION;
        if (field.isAnnotationPresent(Property.class)) {
            nameInPropertyAnnotation = field.getDeclaredAnnotation(Property.class).name();
        }
        boolean isFieldNameFromObjectContainsInPropertiesFile = propertiesFromFile.containsKey(objectFieldName);
        boolean isFieldNameInAnnotationContainsInPropertiesFile = propertiesFromFile.containsKey(nameInPropertyAnnotation);
        if (objectFieldName.equals(NUMBER_PROPERTY) || nameInPropertyAnnotation.equals(NUMBER_PROPERTY)) {
            //this is necessary in order to make the name in
            // the annotation, if it is not default, take precedence over the variable name
            if ((field.isAnnotationPresent(Property.class) && (!field.getAnnotation(Property.class).name().equals(DEFAULT_NAME_IN_ANNOTATION))
                    && isFieldNameInAnnotationContainsInPropertiesFile) || (!field.isAnnotationPresent(Property.class)
                    || field.getAnnotation(Property.class).name().equals(DEFAULT_NAME_IN_ANNOTATION))) {
                if (isFieldNameInAnnotationContainsInPropertiesFile || isFieldNameFromObjectContainsInPropertiesFile) {
                    field.setInt(classObj, Integer.parseInt(propertiesFromFile.get(NUMBER_PROPERTY)));
                } else {
                    throw new IllegalArgumentException(CANT_SET_NUMBER_PROPERTY);
                }
            }
        }
    }


    private static void parseStringProperty(Field field, Object classObj, Map<String, String> propertiesFromFile) throws IllegalAccessException {
        String objectFieldName = field.getName();
        String nameInPropertyAnnotation = DEFAULT_NAME_IN_ANNOTATION;
        if (field.isAnnotationPresent(Property.class)) {
            nameInPropertyAnnotation = field.getDeclaredAnnotation(Property.class).name();
        }
        boolean isFieldNameFromObjectContainsInPropertiesFile = propertiesFromFile.containsKey(objectFieldName);
        boolean isFieldNameInAnnotationContainsInPropertiesFile = propertiesFromFile.containsKey(nameInPropertyAnnotation);

        if (objectFieldName.equals(STRING_PROPERTY) || nameInPropertyAnnotation.equals(STRING_PROPERTY)) {
            if ((field.isAnnotationPresent(Property.class) && (!field.getAnnotation(Property.class).name().equals(DEFAULT_NAME_IN_ANNOTATION))
                    && isFieldNameInAnnotationContainsInPropertiesFile) || (!field.isAnnotationPresent(Property.class)
                    || field.getAnnotation(Property.class).name().equals(DEFAULT_NAME_IN_ANNOTATION))) {
                if (isFieldNameInAnnotationContainsInPropertiesFile || isFieldNameFromObjectContainsInPropertiesFile) {
                    field.set(classObj, propertiesFromFile.get(STRING_PROPERTY));
                } else {
                    throw new IllegalArgumentException(CANT_SET_NAME_PROPERTY);
                }
            }
        }
    }

    private static void parseDateProperty(Field field, Object classObj, Map<String, String> propertiesFromFile) throws IllegalAccessException {
        String objectFieldName = field.getName();
        String nameInPropertyAnnotation = DEFAULT_NAME_IN_ANNOTATION;
        if (field.isAnnotationPresent(Property.class)) {
            nameInPropertyAnnotation = field.getDeclaredAnnotation(Property.class).name();
        }
        boolean isFieldNameFromObjectContainsInPropertiesFile = propertiesFromFile.containsKey(objectFieldName);
        boolean isFieldNameInAnnotationContainsInPropertiesFile = propertiesFromFile.containsKey(nameInPropertyAnnotation);

        if (objectFieldName.equals(TIME_PROPERTY) || nameInPropertyAnnotation.equals(TIME_PROPERTY)) {
            if ((field.isAnnotationPresent(Property.class) && (!field.getAnnotation(Property.class).name().equals(DEFAULT_NAME_IN_ANNOTATION))
                    && isFieldNameInAnnotationContainsInPropertiesFile) || (!field.isAnnotationPresent(Property.class)
                    || field.getAnnotation(Property.class).name().equals(DEFAULT_NAME_IN_ANNOTATION))) {
                String userDateTimeFormat;
                if (field.isAnnotationPresent(Property.class) && (!field.getAnnotation(Property.class).format().equals(DEFAULT_FORMAT_IN_ANNOTATION))) {
                    userDateTimeFormat = field.getAnnotation(Property.class).format();
                } else throw new IllegalArgumentException(MUST_BE_WITH_PROPERTY_ANNOTATION_AND_USER_FORMAT);
                if (isFieldNameInAnnotationContainsInPropertiesFile || isFieldNameFromObjectContainsInPropertiesFile) {
                    field.set(classObj, parseInstant(propertiesFromFile.get(TIME_PROPERTY), userDateTimeFormat));
                } else {
                    throw new IllegalArgumentException(CANT_SET_DATE_TIME_PROPERTY);
                }
            }
        }
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
            throw new IllegalArgumentException(FILE_INPUT_STREAM_ERROR, e);
        }
        for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
            if (entry.getValue() == null) {
                throw new IllegalArgumentException(WRONG_PROPERTIES_FILE);
            }
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
