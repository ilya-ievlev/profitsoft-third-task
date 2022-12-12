package com.profitsoft.first_task.random_list_filling.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.profitsoft.first_task.models.ViolationType;

import java.time.LocalDateTime;

@JacksonXmlRootElement(localName = "fines")
public class PersonDto {
    @JacksonXmlProperty(localName = "date_time")
    private final LocalDateTime dateTime;
    @JacksonXmlProperty(localName = "first_name")
    private final String name = "john"; // TODO: 07.12.22 what to do with constant fields
    @JacksonXmlProperty(localName = "last_name")
    private final String lastName = "smith";
    @JacksonXmlProperty(localName = "type")
    private final ViolationType violationType;
    @JacksonXmlProperty(localName = "fine_amount")
    private final double fineAmount;

    public PersonDto(LocalDateTime localDateTime, ViolationType violationType, double fineAmount) {
        this.dateTime = localDateTime;
        this.violationType = violationType;
        this.fineAmount = fineAmount;
    }

    public LocalDateTime getYear() {
        return dateTime;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public double getFineAmount() {
        return fineAmount;
    }
}
