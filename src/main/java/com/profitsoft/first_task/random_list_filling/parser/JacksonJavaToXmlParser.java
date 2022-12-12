//package com.profitsoft.first_task.random_list_filling.parser;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.profitsoft.first_task.data_processors.DataProcessor;
//import com.profitsoft.first_task.dto.SortedOutputDataTransferObject;
//import com.profitsoft.first_task.random_list_filling.dto.ListOfPersonsDto;
//import com.profitsoft.first_task.random_list_filling.dto.PersonDto;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class JacksonJavaToXmlParser {
//
//    public static void parseData(File inputFileFolder) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<PersonDto> personDtoList = new ArrayList<>();
//
//        for()
//        personDtoList.add()
//
//
//        ListOfPersonsDto listOfPersonsDto = new ListOfPersonsDto();
//        SortedOutputDataTransferObject transferObject = DataProcessor.processData(inputFileFolder);
//        try {
//            mapper.writeValue(resultFile, transferObject);
//        } catch (IOException e) {
//            throw new IOException("error in jsonMapper", e);
//        }
//    }
//
//    private static void fillPersonDtoList(int amountOfPeopleInList) {
//        for(int i = 0; i<amountOfPeopleInList; i++){
//            LocalDateTime localDateTime = ;
//            PersonDto personDto = new PersonDto();
//        }
//    }
//}
