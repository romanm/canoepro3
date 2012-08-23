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

@Configurable
@Entity
@Table(schema = "public", name = "athlete")
public class Athlete {

	@OneToMany(mappedBy = "idathlete")
	private Set<BandAthlete> bandAthletes;

	@Column(name = "forename", length = 50)
	@NotNull
	private String forename;

	@Column(name = "familyname", length = 50)
	@NotNull
	private String familyname;

	@Column(name = "sex", length = 1)
	@NotNull
	private String sex;

	@Column(name = "birthdate")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date birthdate;

	@PersistenceContext
	transient EntityManager entityManager;

	@Id
	@SequenceGenerator(name = "ATHLETE_ID_GENERATOR", sequenceName = "DBID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATHLETE_ID_GENERATOR")
	private Integer id;

	public Set<com.canoepro2.domain.BandAthlete> getBandAthletes() {
		return bandAthletes;
	}

	public void setBandAthletes(Set<com.canoepro2.domain.BandAthlete> bandAthletes) {
		this.bandAthletes = bandAthletes;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Athlete().entityManager;
		if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAthletes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Athlete o", Long.class).getSingleResult();
	}

	public static List<com.canoepro2.domain.Athlete> findAllAthletes() {
		return entityManager().createQuery("SELECT o FROM Athlete o", Athlete.class).getResultList();
	}

	public static com.canoepro2.domain.Athlete findAthlete(Integer id) {
		if (id == null) return null;
		return entityManager().find(Athlete.class, id);
	}

	public static List<com.canoepro2.domain.Athlete> findAthleteEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Athlete o", Athlete.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
			Athlete attached = Athlete.findAthlete(this.id);
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
	public com.canoepro2.domain.Athlete merge() {
		if (this.entityManager == null) this.entityManager = entityManager();
		Athlete merged = this.entityManager.merge(this);
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
}
