package org.jboss.as.quickstarts.kitchensink.data;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.as.quickstarts.kitchensink.model.Team;

@Repository
public interface TeamRepository extends EntityRepository<Team, Long> {
	
	
	
	

}
