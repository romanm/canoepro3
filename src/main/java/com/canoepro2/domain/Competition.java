package com.canoepro2.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "competition")
@Configurable
public class Competition {

    @PersistenceContext
    transient EntityManager entityManager;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")

	@Id
	@SequenceGenerator(name="COMPETITION_ID_GENERATOR", sequenceName="DBID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMPETITION_ID_GENERATOR")
    private Integer id;

    @OneToMany(mappedBy = "idcompetition")
    private Set<Race> races;

    @Column(name = "competition", length = 200, unique = true)
    @NotNull
    private String competition;

    @Column(name = "begindate", unique = true)
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date begindate;

    @Column(name = "enddate", unique = true)
    @NotNull
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date enddate;

    public static final EntityManager entityManager() {
        EntityManager em = new Competition().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCompetitions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Competition o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.Competition> findAllCompetitions() {
        return entityManager().createQuery("SELECT o FROM Competition o", Competition.class).getResultList();
    }

    public static com.canoepro2.domain.Competition findCompetition(Integer id) {
        if (id == null) return null;
        return entityManager().find(Competition.class, id);
    }

    public static List<com.canoepro2.domain.Competition> findCompetitionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Competition o", Competition.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Competition attached = Competition.findCompetition(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public com.canoepro2.domain.Competition merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Competition merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<com.canoepro2.domain.Race> getRaces() {
        return races;
    }

    public void setRaces(Set<com.canoepro2.domain.Race> races) {
        this.races = races;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
}
