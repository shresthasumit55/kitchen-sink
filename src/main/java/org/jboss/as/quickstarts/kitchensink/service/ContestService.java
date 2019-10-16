package org.jboss.as.quickstarts.kitchensink.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.as.quickstarts.kitchensink.data.ContestRepository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;

@Stateless
public class ContestService {

	@Inject
	ContestRepository contestRepository;
	
	public void save(Contest contest) {
		contestRepository.save(contest);
	}
	
	public void update(Contest contest) {
		contestRepository.save(contest);
	}
}
