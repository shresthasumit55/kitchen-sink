package org.jboss.as.quickstarts.kitchensink.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.as.quickstarts.kitchensink.data.TeamRepository;
import org.jboss.as.quickstarts.kitchensink.model.Team;

@Stateless
public class TeamService {
	
	@Inject
	TeamRepository teamRepository;
	
	public boolean isChangeAllowedForContest(Team team) {
		return team.getParticipatingContest().getRegistration_allowed();
	}
	
	public void save(Team team) {
		teamRepository.save(team);
	}
	
	public void update(Team team) {
		teamRepository.save(team);
	}

}
