package com.profitsoft.first_task.random_list_filling.dto;

import java.util.List;

public class ListOfPersonsDto {
    private final List<PersonDto> personDtoList;

    public ListOfPersonsDto(List<PersonDto> personDtoList) {
        this.personDtoList = personDtoList;
    }

    public List<PersonDto> getPersonDtoList() {
        return personDtoList;
    }
}
