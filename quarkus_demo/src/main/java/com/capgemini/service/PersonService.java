package com.capgemini.service;

import com.capgemini.model.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class PersonService {
    public Long persistPerson(Person person) {
        person.persist();
        return person.id;
    }

    public Optional<Person> findPerson(Long id) {
        return Person.findByIdOptional(id);
    }

    public List<Person> findByName(String firstname) {
        return Person.find("firstname=?1", firstname).list();
    }
    public List<Person> listPersons() {
        return Person.listAll();
    }
}
