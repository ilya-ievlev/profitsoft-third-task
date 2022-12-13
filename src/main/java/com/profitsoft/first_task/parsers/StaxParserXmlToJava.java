package com.profitsoft.first_task.parsers;

import com.profitsoft.first_task.models.Person;
import com.profitsoft.first_task.models.ViolationType;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StaxParserXmlToJava {

    private static final String PERSON = "person";
    private static final String FINES = "fines";
    private static final String DATE_TIME = "date_time";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String TYPE = "type";
    private static final String FINE_AMOUNT = "fine_amount";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<Person> parseData(File inputFile) {
        Person person = null;
        String content = null;
        List<Person> personList = null;
        XMLInputFactory factory = XMLInputFactory.newInstance();
        String filePathFromProjectRoot = inputFile.getPath();
        //WINDOWS VERSION
        String relativeFilePath = filePathFromProjectRoot.replace("src\\main\\resources\\", ""); //it is needed to be able to pass correct path to file
        //LINUX VERSION (I don't know why it doesn't work the same on different systems)
//        String relativeFilePath = filePathFromProjectRoot.replace("src/main/resources/", "");
        try {
            XMLStreamReader reader =
                    factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(relativeFilePath));
            while (reader.hasNext()) {
                int event = reader.next();
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (PERSON.equals(reader.getLocalName())) {
                            person = new Person();
                        }
                        if (FINES.equals(reader.getLocalName())) {
                            if (personList == null) {
                                personList = new ArrayList<>();
                            }
                        }
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        content = reader.getText().trim();
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (reader.getLocalName()) {
                            case PERSON:
                                personList.add(person);
                                break;
                            case DATE_TIME:
                                person.setDateTime(LocalDateTime.parse(content, DATE_TIME_FORMATTER));
                                break;
                            case FIRST_NAME:
                                person.setFirstName(content);
                                break;
                            case LAST_NAME:
                                person.setLastName(content);
                                break;
                            case TYPE:
                                person.setViolationType(ViolationType.valueOf(content));
                                break;
                            case FINE_AMOUNT:
                                person.setFineAmount(Double.parseDouble(content));
                                break;
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {
            throw new IllegalArgumentException("XMLStreamReader can't work with file you provided", e);
        }

        return personList;
    }
}
