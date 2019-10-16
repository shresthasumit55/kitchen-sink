package org.jboss.as.quickstarts.kitchensink.data;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;

@Repository
public interface ContestRepository extends EntityRepository<Contest, Long>{

}
