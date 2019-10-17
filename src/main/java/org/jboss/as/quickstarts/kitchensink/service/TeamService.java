package org.jboss.as.quickstarts.kitchensink.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.as.quickstarts.kitchensink.data.TeamRepository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.model.Team;

@Stateless
public class TeamService {
	
	@Inject
	Logger logger;
	
	@Inject
	TeamRepository teamRepository;
	
	@Inject
	ContestService contestService;
	
		
	private boolean checkUniqueMembers(Team team) {
		Set<Long> ids = new HashSet<>();
		Integer actualTeamSize = team.getMembers().size() + 1;
		team.getMembers().forEach(item -> ids.add(item.getId()));
		ids.add(team.getCoach().getId());
		return ids.size()==actualTeamSize;
		
	}
	
	
	
	private boolean checkMembersAge(Team team) {        
        
		for(Person member:team.getMembers()) {
			Date birth = member.getBirthDate();
			Instant instant = Instant.ofEpochMilli(birth.getTime());
	        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

	        LocalDate birthdate = localDateTime.toLocalDate();
	        LocalDate now = LocalDate.now();
	        int years = (int) ChronoUnit.YEARS.between(birthdate, now);
			if (years>=24)
				return false;
		}
		return true;
	}
	
	
	private boolean checkForMultipleParticipation(Team team) {
		Contest contest = team.getParticipatingContest();
		Set<Long> memberIds = new HashSet<>();
		contest.getTeamSet().forEach(singleTeam -> {
			Set<Person> members = singleTeam.getMembers();
			members.forEach(member -> memberIds.add(member.getId()));			
		});
		for (Person person: team.getMembers()) {
			if (memberIds.contains(person.getId()))
				return false;
		}
		return true;
		
	}
	
	
	
	/**
	 * This function checks to see if all the rules are satisfied by the team during registration
	 * @param team
	 * @return
	 */
	private boolean checkRules(Team team) {
		
		if(contestService.isContestWritable(team.getParticipatingContest()) &&
				checkUniqueMembers(team)&&
				!contestService.isContestFull(team.getParticipatingContest()) &&
				checkMembersAge(team)&&
				checkForMultipleParticipation(team)
				) {
			return true;
		}
		return false;
	}
	
	/**
	 * This function promotes the team if all the required conditions are passed
	 * @param team
	 */
	
	private void promoteTeam(Team team) {
		Contest contest = team.getParticipatingContest();
		Contest superContest = contest.getPreliminaryRound();
		if (team.getRank()>0 && team.getRank()<6 && 
				superContest.getTeamSet().size()<superContest.getCapacity()) {
			Team clonedTeam = new Team(team);
			clonedTeam.setClonedTeamFrom(team);
			save(clonedTeam);
			team.setClonedTeamTo(clonedTeam);
			update(team);
			}
		else {
			logger.info("Team cannot be promoted");
		}
			
		}
		
	
	public void save(Team team) {
		if (checkRules(team)) {
			teamRepository.save(team);
		}else {
			logger.info("The team cannot be registered. It doesn't satisfy all the rules");
		}
	}
	
	public void update(Team team) {
		if (contestService.isContestWritable(team.getParticipatingContest()))
			teamRepository.save(team);
	}

}
