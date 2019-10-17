package org.jboss.as.quickstarts.kitchensink.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.xml.ws.http.HTTPException;

import org.apache.deltaspike.data.api.EntityCountRepository;
import org.apache.deltaspike.data.api.EntityPersistenceRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.kitchensink.data.ContestRepository;
import org.jboss.as.quickstarts.kitchensink.data.PersonRepository;
import org.jboss.as.quickstarts.kitchensink.data.TeamRepository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.model.Team;
import org.jboss.as.quickstarts.kitchensink.service.ContestService;
import org.jboss.as.quickstarts.kitchensink.service.MemberRegistration;
import org.jboss.as.quickstarts.kitchensink.service.PersonService;
import org.jboss.as.quickstarts.kitchensink.service.TeamService;
import org.jboss.as.quickstarts.kitchensink.util.EntityManagerProducer;
import org.jboss.as.quickstarts.kitchensink.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.filter.ExcludeRegExpPaths;
import org.junit.Test;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ContestTest {
	
	
	@Inject
	ContestService contestService;
	
	@Inject
	TeamService teamService;
	
	@Inject
	PersonService personService;
	
	
	@Deployment
    public static Archive<?> createTestArchive() {
		
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml").resolve(
                "org.apache.deltaspike.core:deltaspike-core-api", 
                "org.apache.deltaspike.core:deltaspike-core-impl",
                "org.apache.deltaspike.modules:deltaspike-partial-bean-module-impl"
        ).withTransitivity().asFile();
		
        return ShrinkWrap.create(WebArchive.class, "test.war")
            //.addPackages(true,"org.jboss.as.quickstarts.kitchensink")
            .addClasses(ContestRepository.class,TeamRepository.class,PersonRepository.class, ContestService.class,TeamService.class,PersonService.class,
            		EntityRepository.class,Person.class,Team.class,Contest.class,EntityPersistenceRepository.class,EntityManagerProducer.class,Resources.class,
            		EntityCountRepository.class)
        		.addAsLibraries(libs)
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            // Deploy our test datasource
            .addAsWebInfResource("test-ds.xml");
    }
	
	
	@Test
    public void testContestFlag() throws Exception {		
		System.out.println("fsdlkfjsdlkfj");
		populateData();
		Contest contest = contestService.getContestById(Long.valueOf(1));
		contest.setCapacity(33);
		contestService.update(contest);
		Contest contest2 = contestService.getContestById(Long.valueOf(1));
		assertTrue(contest2.getCapacity()==33);
		
		
		
    }
	
	private void persistPerson(Set<Person> persons) {
        for (Person person : persons) {
        	personService.save(person);
        }
    }
	

	
	public void populateData() {
        try {


            Date date = new Date();
            Contest contest = new Contest(11, date, "Contest1", true, date, date);
            Contest contest2 = new Contest(15, date, "Contest2", false, date, date);
            Contest contest12 = new Contest(11, date, "Contest12", true, date, date);
            contest12.setPreliminaryRound(contest);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -22);
            Date result = cal.getTime();

            Person contestMgr = new Person(result, "cm@gm.com", "Ryan", "UT");
            Person contestMgr2 = new Person(result, "cm2@gm.com", "Jan", "UT");

            
            personService.save(contestMgr);
            personService.save(contestMgr2);

            contest.setManagers(new HashSet<>(Arrays.asList(contestMgr)));
            contest2.setManagers(new HashSet<>(Arrays.asList(contestMgr2)));
            contest12.setManagers(new HashSet<>(Arrays.asList(contestMgr2)));

            contestService.save(contest);
            contestService.save(contest2);
            contestService.save(contest12);


            Person coach = new Person(result, "coach@gmail.com", "Bobby", "baylor");
            Person coach2 = new Person(result, "coach2@gmail.com", "Robby", "baylor");
            Person coach3 = new Person(result, "coach3@gmail.com", "Toby", "baylor");
            personService.save(coach);
            personService.save(coach2);
            personService.save(coach3);


            Person person1 = new Person(result, "test@gmail.com", "Bob", "baylor");
            Person person2 = new Person(result, "test1@gmail.com", "Rob", "UT");
            Person person3 = new Person(result, "test2@gmail.com", "Mary", "UTA");
            Set<Person> persons = new HashSet<>();
            persons.add(person1);
            persons.add(person2);
            persons.add(person3);
            persistPerson(persons);

            Person person4 = new Person(result, "test3@gmail.com", "Sam", "baylor");
            Person person5 = new Person(result, "test4@gmail.com", "Jon", "UT");
            Person person6 = new Person(result, "test5@gmail.com", "Paul", "UTA");
            Set<Person> persons2 = new HashSet<>();
            persons2.add(person4);
            persons2.add(person5);
            persons2.add(person6);
            persistPerson(persons2);

            Person person7 = new Person(result, "test6@gmail.com", "Amy", "baylor");
            Person person8 = new Person(result, "test7@gmail.com", "Natalie", "UT");
            Person person9 = new Person(result, "test8@gmail.com", "John", "UTA");
            Set<Person> persons3 = new HashSet<>();
            persons3.add(person7);
            persons3.add(person8);
            persons3.add(person9);
            persistPerson(persons3);

            Team team1 = new Team("team1", 1);
            team1.setParticipatingContest(contest);
            team1.setMembers(persons);
            team1.setCoach(coach);
            teamService.save(team1);

            Team team2 = new Team("team2", 2);
            team2.setParticipatingContest(contest12);
            team2.setMembers(persons2);
            team2.setCoach(coach2);
            teamService.save(team2);

            Team team3 = new Team("team3", 3);
            team3.setParticipatingContest(contest12);
            team3.setMembers(persons3);
            team3.setCoach(coach3);
            teamService.save(team3);

            Team team4 = new Team("team4", 4);
            team4.setClonedTeamTo(team3);
            teamService.save(team4);

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -20);
            result = cal.getTime();
            Person person = new Person(result, "newtest@gmail.com", "Shaw", "baylor");
            personService.save(person);

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -15);
            result = cal.getTime();
            person = new Person(result, "newtest2@gmail.com", "Shane", "baylor");
            personService.save(person);

        }catch (Exception e){
            System.err.println("Database error.");
        }

    }
    
}
