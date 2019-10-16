package org.jboss.as.quickstarts.kitchensink.data;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.as.quickstarts.kitchensink.model.Person;

@Repository
public interface PersonRepository extends EntityRepository<Person, Long>
{

    Person findById(Long id);


}
