package com.profitsoft.first_task.parsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.profitsoft.first_task.data_processors.DataProcessor;
import com.profitsoft.first_task.dto.SortedOutputDataTransferObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class JacksonJavaToJsonParser {
    private static final File resultFile = new File("src/main/resources/first_task/output_json/output.json");
    private static final String ERROR_IN_JSON_MAPPER = "error in jsonMapper";
    private static final String CAN_T_GET_TRANSFER_OBJECT_IN_JACKSON_PARSER = "can't get transferObject In JacksonParser";

    public static void parseData(File inputFileFolder) throws IOException, ExecutionException {
        ObjectMapper jsonMapper = new ObjectMapper();
        SortedOutputDataTransferObject transferObject = null;
        try {
            transferObject = DataProcessor.processData(inputFileFolder);
            jsonMapper.writeValue(resultFile, transferObject);
        } catch (IOException e) {
            throw new IOException(ERROR_IN_JSON_MAPPER, e);
        } catch (ExecutionException e) {
            throw new ExecutionException(CAN_T_GET_TRANSFER_OBJECT_IN_JACKSON_PARSER, e);
        }
    }
}
