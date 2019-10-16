package org.jboss.as.quickstarts.kitchensink.model;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Contest implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer capacity;

    @Column
    private Date date;

    @Column
    private String name;

    @Column
    private Boolean registration_allowed;

    @Column
    private Date registration_from;

    @Column
    private Date registration_to;

    @ManyToOne
    @JoinTable(name = "CONTEST_PRELIMINARY_CONTEST")
    private Contest preliminaryRound;

    @OneToMany(mappedBy = "preliminaryRound", cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Contest> subCompetition = new ArrayList<>();
    
    
    public Contest() {
		super();
	}

	@ManyToMany
    @JoinTable(name = "CONTEST_MANAGER_CONTEST",
            joinColumns = { @JoinColumn(name = "CONTEST_ID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID") })
    private Set<Person> managers;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "participatingContest")
    private List<Team> teamSet = new ArrayList<>();

    public Contest(Integer capacity, Date date, String name, Boolean registration_allowed, Date registration_from, Date registration_to) {
        this.capacity = capacity;
        this.date = date;
        this.name = name;
        this.registration_allowed = registration_allowed;
        this.registration_from = registration_from;
        this.registration_to = registration_to;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRegistration_allowed() {
        return registration_allowed;
    }

    public void setRegistration_allowed(Boolean registration_allowed) {
        this.registration_allowed = registration_allowed;
    }

    public Date getRegistration_from() {
        return registration_from;
    }

    public void setRegistration_from(Date registration_from) {
        this.registration_from = registration_from;
    }

    public Date getRegistration_to() {
        return registration_to;
    }

    public void setRegistration_to(Date registration_to) {
        this.registration_to = registration_to;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Person> getManagers() {
        return managers;
    }

    public void setManagers(Set<Person> managers) {
        this.managers = managers;
    }

    public List<Team> getTeamSet() {
        return teamSet;
    }

    public void setTeamSet(List<Team> teamSet) {
        this.teamSet = teamSet;
    }

    public Contest getPreliminaryRound() {
        return preliminaryRound;
    }

    public void setPreliminaryRound(Contest preliminaryRound) {
        this.preliminaryRound = preliminaryRound;
    }

    public List<Contest> getSubCompetition() {
        return subCompetition;
    }

    public void setSubCompetition(List<Contest> subCompetition) {
        this.subCompetition = subCompetition;
    }
}



