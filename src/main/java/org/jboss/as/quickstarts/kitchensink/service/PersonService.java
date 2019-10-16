package org.jboss.as.quickstarts.kitchensink.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.as.quickstarts.kitchensink.data.PersonRepository;
import org.jboss.as.quickstarts.kitchensink.model.Person;

@Stateless
public class PersonService {
	
	@Inject
	PersonRepository personRepository;
	
	public void save(Person person) {
		personRepository.save(person);
	}
	
	public void update(Person person) {
		personRepository.save(person);
	}
	
	public List<Person> getAllPersons(){
		return personRepository.findAll();
	}
	
	public Person getPersonById(Long id){
		return personRepository.findById(id);
	}

}
