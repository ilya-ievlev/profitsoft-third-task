package com.profitsoft.second_task.property_loader;

import com.profitsoft.second_task.models.Model;
import com.profitsoft.second_task.models.ModelWithExtraFields;
import com.profitsoft.second_task.models.ModelWithMissingField;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesLoaderTest {

    @Test
    void throwExceptionIfIntegerNotParsed() {
        Path inputFileWithWrongInteger = Paths.get("src/test/resources/second_task/class_params_wrongInteger.properties");
        assertThrows(IllegalArgumentException.class,
                () -> PropertiesLoader.loadFromProperties(Model.class, inputFileWithWrongInteger));
    }

    @Test
    void throwExceptionIfDateNotParsed() {
        Path inputFileWithWrongDate = Paths.get("src/test/resources/second_task/class_params_wrongDate.properties");
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(Model.class, inputFileWithWrongDate));
    }

    @Test
    void throwExceptionIfPropertiesFileContainsNull() {
        Path inputFileEmpty = Paths.get("src/test/resources/class_params_empty.properties");
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(Model.class, inputFileEmpty));
    }

    @Test
    void throwExceptionIfIntNumberIsBiggerThanIntLimit() {
        Path inputFileBigInteger = Paths.get("src/test/resources/class_params_bigInteger.properties");
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(Model.class, inputFileBigInteger));
    }

    @Test
    void throwExceptionIfClassIsNull() {
        Path inputFileCorrect = Paths.get("src/test/resources/class_params.properties");
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(null, inputFileCorrect));
    }

    @Test
    void throwExceptionIfPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(Model.class, null));
    }

    @Test
    void throwExceptionIfClassDoesNotHaveFieldWithRequiredParams() {
        Path inputFileCorrect = Paths.get("src/test/resources/class_params.properties");
        assertThrows(IllegalArgumentException.class, ()-> PropertiesLoader.loadFromProperties(ModelWithMissingField.class, inputFileCorrect));
    }

    @Test
    void throwExceptionIfClassHasExtraFields() {
        Path inputFileCorrect = Paths.get("src/test/resources/class_params.properties");
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(ModelWithExtraFields.class, inputFileCorrect));
    }

    @Test
    void createRightModelFromProperties(){
        Path inputFileCorrect = Paths.get("src/test/resources/class_params.properties");
        Model model = new Model();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse("29.11.2022 18:30", dateTimeFormatter);
        Instant correctInstant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        model.setMyNumber(10);
        model.setStringProperty("value1");
        model.setTimeProperty(correctInstant);
        try {
            Model testModel = PropertiesLoader.loadFromProperties(Model.class, inputFileCorrect);
            assertEquals(model, testModel);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException();
        }
    }



}
