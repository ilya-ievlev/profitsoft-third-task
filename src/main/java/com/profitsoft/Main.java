package com.profitsoft;

import com.profitsoft.first_task.parsers.JacksonJavaToJsonParser;
import com.profitsoft.second_task.models.Model;
import com.profitsoft.second_task.property_loader.PropertiesLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final File inputFileFolderFirstTask = new File("src/main/resources/first_task/input_xml");
    private static final Path inputFileSecondTask = Paths.get("src/main/resources/second_task/class_params.properties");

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        startFirstTask();
        startSecondTask(inputFileSecondTask);
    }

    private static void startFirstTask() throws IOException, ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        JacksonJavaToJsonParser.parseData(inputFileFolderFirstTask);
        double finalTime = (System.currentTimeMillis()-startTime)/1000D;
        System.out.println(finalTime);
    }

    private static void startSecondTask(Path inputFile) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        System.out.println(PropertiesLoader.loadFromProperties(Model.class, inputFile));
    }
}
