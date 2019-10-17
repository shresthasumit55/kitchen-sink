package org.jboss.as.quickstarts.kitchensink.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.as.quickstarts.kitchensink.data.ContestRepository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Team;

@Stateless
public class ContestService {

	@Inject
	ContestRepository contestRepository;
	
	public void save(Contest contest) {
		contestRepository.save(contest);
	}
	
	public void update(Contest contest) {
		if (contest.getRegistration_allowed())
			contestRepository.save(contest);
	}
	
	public Contest getContestById(Long id) {
		return contestRepository.findBy(id);
	}
	
	public List<Contest> getAllContests(){
		return contestRepository.findAll();
	}
	
	public boolean isContestFull(Contest contest) {
		Integer contestCapacity = contest.getCapacity();
		Integer contestSize = contest.getTeamSet().size();
		if (contestSize<contestCapacity) {
			return false;
		}
		return true;
	}
	
	public boolean isContestWritable(Contest contest) {
		if (contest.getRegistration_allowed())
			return true;
		return false;
	}
}
