package com.capgemini.controller.mapper;

import com.capgemini.controller.dto.PersonDto;
import com.capgemini.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "CDI")
public interface PersonMapper {
    Person map(PersonDto source);

    PersonDto map(Person source);

    List<PersonDto> map(List<Person> source);
}
