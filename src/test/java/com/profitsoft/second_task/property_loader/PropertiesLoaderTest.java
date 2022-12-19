package com.profitsoft.second_task.property_loader;

import com.profitsoft.second_task.models.Model;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void throwExceptionIfClassIsNull(){
        Path inputFileCorrect = Paths.get("src/test/resources/class_params.properties");
        assertThrows(IllegalArgumentException.class, ()-> PropertiesLoader.loadFromProperties(null, inputFileCorrect));
    }

    @Test
    void throwExceptionIfPathIsNull(){
        assertThrows(IllegalArgumentException.class, () -> PropertiesLoader.loadFromProperties(Model.class, null));
    }
}
