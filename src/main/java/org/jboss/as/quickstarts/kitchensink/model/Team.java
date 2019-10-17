package org.jboss.as.quickstarts.kitchensink.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Team implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private Integer rank;

    @Column
    private String state;

    public enum State
    {
        Accepted, Pending, Canceled;
    }

    @ManyToOne
    @JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID")
    private Contest participatingContest;


    @Size(max=3)
    @ManyToMany
    @JoinTable(name = "TEAM_MEMBER",
            joinColumns = { @JoinColumn(name = "TEAM_ID", referencedColumnName = "ID") }, //do not forget referencedColumnName if name is different
            inverseJoinColumns = { @JoinColumn(name = "MEMBER_ID", referencedColumnName = "ID") })
    private Set<Person> members;

    @ManyToOne
    private Person coach;

    @OneToOne
    private Team clonedTeamFrom;
    
    @OneToOne
    private Team clonedTeamTo;
    
    public Team() {
		super();
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contest getParticipatingContest() {
        return participatingContest;
    }

    public void setParticipatingContest(Contest participatingContest) {
        this.participatingContest = participatingContest;
        this.participatingContest.getTeamSet().add(this);
    }

    public Set<Person> getMembers() {
        return members;
    }

    public void setMembers(Set<Person> members) {
        this.members = members;
    }

    public Person getCoach() {
        return coach;
    }

    public void setCoach(Person coach) {
        this.coach = coach;
    }

    public Team getClonedTeamFrom() {
		return clonedTeamFrom;
	}

	public void setClonedTeamFrom(Team clonedTeamFrom) {
		this.clonedTeamFrom = clonedTeamFrom;
	}

	public Team getClonedTeamTo() {
		return clonedTeamTo;
	}

	public void setClonedTeamTo(Team clonedTeamTo) {
		this.clonedTeamTo = clonedTeamTo;
	}

	public Team(String name, Integer rank) {
        this.name = name;
        this.rank = rank;
        this.state = State.Pending.toString();
    }
    
    public Team(Team otherTeam) {
    	
    	super();
		this.id = otherTeam.getId();
		this.name = otherTeam.getName();
		this.rank = otherTeam.getRank();
		this.state = State.Pending.toString();
		this.participatingContest = otherTeam.getParticipatingContest();
		this.members = otherTeam.getMembers();
		this.coach = otherTeam.getCoach();
    	
    }

    
    
}

