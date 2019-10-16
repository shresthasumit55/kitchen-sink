package org.jboss.as.quickstarts.kitchensink.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@SuppressWarnings("serial")
@Entity
@XmlRootElement
public class Person implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String university;


    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> participatingTeams;


    @ManyToMany(mappedBy = "managers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Contest> managedContest;


    @OneToMany(mappedBy = "coach",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Team> coachedTeams;


    public Person() {
		super();
	}

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Team> getParticipatingTeams() {
        return participatingTeams;
    }

    public void setParticipatingTeams(List<Team> participatingTeams) {
        this.participatingTeams = participatingTeams;
    }

    public Set<Contest> getManagedContest() {
        return managedContest;
    }

    public void setManagedContest(Set<Contest> managedContest) {
        this.managedContest = managedContest;
    }

    public Set<Team> getCoachedTeams() {
        return coachedTeams;
    }

    public void setCoachedTeams(Set<Team> coachedTeams) {
        this.coachedTeams = coachedTeams;
    }

    public Person(Date birthDate, String email, String name, String university) {
        this.birthDate = birthDate;
        this.email = email;
        this.name = name;
        this.university = university;
    }
}
