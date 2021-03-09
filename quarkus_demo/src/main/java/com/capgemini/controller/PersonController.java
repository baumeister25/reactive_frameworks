package com.capgemini.controller;

import com.capgemini.controller.dto.PersonDto;
import com.capgemini.controller.mapper.PersonMapper;
import com.capgemini.model.Person;
import com.capgemini.service.PersonService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/person/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
public class PersonController {


    @Inject
    PersonMapper mapper;
    @Inject
    PersonService personService;

    @GET
    public List<PersonDto> list() {
        return mapper.map(personService.listPersons());
    }

    @GET
    @Path("/{id}")
    public PersonDto getById(@PathParam("id") Long id) {
        return mapper.map(personService.findPerson(id).orElseThrow(() -> new IllegalArgumentException("Person with id " + id + "not found")));
    }

    @GET
    @Path("/firstname/{firstname}")
    public List<PersonDto> getByFirstname(@PathParam("firstname") String firstname) {
        return mapper.map(personService.findByName(firstname));
    }

    @POST
    public Long add(PersonDto personDto) {
        Person person = mapper.map(personDto);
        return personService.persistPerson(person);
    }


}
