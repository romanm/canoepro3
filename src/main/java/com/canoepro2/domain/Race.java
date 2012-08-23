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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public", name = "race")
public class Race {

	@OneToMany(mappedBy = "idrace")
	private Set<Band> bands;

	@ManyToOne
	@JoinColumn(name = "idboot", referencedColumnName = "id", nullable = false)
	private Boot idboot;

	@ManyToOne
	@JoinColumn(name = "idcompetition", referencedColumnName = "id", nullable = false)
	private Competition idcompetition;

	@ManyToOne
	@JoinColumn(name = "iddistance", referencedColumnName = "id", nullable = false)
	private Distance iddistance;

	@ManyToOne
	@JoinColumn(name = "idgender", referencedColumnName = "id", nullable = false)
	private Gender idgender;

	@ManyToOne
	@JoinColumn(name = "idoldgroup", referencedColumnName = "id", nullable = false)
	private Oldgroup idoldgroup;

	@ManyToOne
	@JoinColumn(name = "idracetype", referencedColumnName = "id", nullable = false)
	private Racetype idracetype;

	@Column(name = "start")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	private Date start;

	@PersistenceContext
	transient EntityManager entityManager;

	@Id
	@SequenceGenerator(name = "RACE_ID_GENERATOR", sequenceName = "DBID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RACE_ID_GENERATOR")
	private Integer id;

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public Set<com.canoepro2.domain.Band> getBands() {
		return bands;
	}

	public void setBands(Set<com.canoepro2.domain.Band> bands) {
		this.bands = bands;
	}

	public Boot getIdboot() {
		return idboot;
	}

	public void setIdboot(Boot idboot) {
		this.idboot = idboot;
	}

	public Competition getIdcompetition() {
		return idcompetition;
	}

	public void setIdcompetition(Competition idcompetition) {
		this.idcompetition = idcompetition;
	}

	public Distance getIddistance() {
		return iddistance;
	}

	public void setIddistance(Distance iddistance) {
		this.iddistance = iddistance;
	}

	public Gender getIdgender() {
		return idgender;
	}

	public void setIdgender(Gender idgender) {
		this.idgender = idgender;
	}

	public Oldgroup getIdoldgroup() {
		return idoldgroup;
	}

	public void setIdoldgroup(Oldgroup idoldgroup) {
		this.idoldgroup = idoldgroup;
	}

	public Racetype getIdracetype() {
		return idracetype;
	}

	public void setIdracetype(Racetype idracetype) {
		this.idracetype = idracetype;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Race().entityManager;
		if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countRaces() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Race o", Long.class).getSingleResult();
	}

	public static List<com.canoepro2.domain.Race> findAllRaces() {
		return entityManager().createQuery("SELECT o FROM Race o", Race.class).getResultList();
	}

	public static com.canoepro2.domain.Race findRace(Integer id) {
		System.out.println(".....................1 -- "+id);
		if (id == null) return null;
		System.out.println(".....................2 -- "+id);
		EntityManager entityManager2 = entityManager();
		System.out.println(".....................3 -- "+entityManager2);
		Race find = entityManager2.find(Race.class, id);
		System.out.println(".....................4 -- "+find);
		return find;
	}

	public static List<com.canoepro2.domain.Race> findRaceEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Race o", Race.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
			Race attached = Race.findRace(this.id);
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
	public com.canoepro2.domain.Race merge() {
		if (this.entityManager == null) this.entityManager = entityManager();
		Race merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
