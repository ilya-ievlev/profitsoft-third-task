package com.profitsoft.first_task.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitsoft.first_task.data_processors.DataProcessor;
import com.profitsoft.first_task.dto.SortedOutputDataTransferObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class JacksonJavaToJsonParser {
    private static final File resultFile = new File("src/main/resources/first_task/output_json/output.json");

    public static void parseData(File inputFileFolder) throws IOException, ExecutionException, InterruptedException {
        ObjectMapper jsonMapper = new ObjectMapper();
        SortedOutputDataTransferObject transferObject = DataProcessor.processData(inputFileFolder);
        try {
            jsonMapper.writeValue(resultFile, transferObject);
        } catch (IOException e) {
            throw new IOException("error in jsonMapper", e);
        }
    }
}
