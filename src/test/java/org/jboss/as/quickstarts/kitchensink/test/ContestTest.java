package org.jboss.as.quickstarts.kitchensink.test;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.service.ContestService;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ContestTest {
	
	
	@Inject
	ContestService contestService;
	

	
	public void populateData() {
        try {


            Date date = new Date();
            Contest contest = new Contest(11, date, "Contest1", true, date, date);
            Contest contest2 = new Contest(15, date, "Contest2", true, date, date);
            Contest contest12 = new Contest(11, date, "Contest12", true, date, date);
            contest12.setPreliminaryRound(contest);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -22);
            Date result = cal.getTime();

            Person contestMgr = new Person(result, "cm@gm.com", "Ryan", "UT");
            Person contestMgr2 = new Person(result, "cm2@gm.com", "Jan", "UT");
            em.persist(contestMgr);
            em.persist(contestMgr2);

            contest.setManagers(new HashSet<>(Arrays.asList(contestMgr)));
            contest2.setManagers(new HashSet<>(Arrays.asList(contestMgr2)));
            contest12.setManagers(new HashSet<>(Arrays.asList(contestMgr2)));

            em.persist(contest);
            em.persist(contest2);
            em.persist(contest12);


            Person coach = new Person(result, "coach@gmail.com", "Bobby", "baylor");
            Person coach2 = new Person(result, "coach2@gmail.com", "Robby", "baylor");
            Person coach3 = new Person(result, "coach3@gmail.com", "Toby", "baylor");
            em.persist(coach);
            em.persist(coach2);
            em.persist(coach3);


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
            em.persist(team1);

            Team team2 = new Team("team2", 2);
            team2.setParticipatingContest(contest12);
            team2.setMembers(persons2);
            team2.setCoach(coach2);
            em.persist(team2);

            Team team3 = new Team("team3", 3);
            team3.setParticipatingContest(contest12);
            team3.setMembers(persons3);
            team3.setCoach(coach3);
            em.persist(team3);

            Team team4 = new Team("team4", 4);
            team4.setClonedTeam(team3);
            em.persist(team4);

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -20);
            result = cal.getTime();
            Person person = new Person(result, "newtest@gmail.com", "Shaw", "baylor");
            em.persist(person);

            cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -15);
            result = cal.getTime();
            person = new Person(result, "newtest2@gmail.com", "Shane", "baylor");
            em.persist(person);

        }catch (HibernateException e){
            System.err.println("Database error.");
        }

    }
}
