package com.profitsoft;

import com.profitsoft.first_task.parsers.JacksonJavaToJsonParser;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class App {
    private static final File inputFileFolder = new File("src/main/resources/first_task/input_xml");

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        JacksonJavaToJsonParser.parseData(inputFileFolder);
    }
}
