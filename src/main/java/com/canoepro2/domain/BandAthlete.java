package com.canoepro2.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "band_athlete")
@Configurable
public class BandAthlete {

    @ManyToOne
    @JoinColumn(name = "idathlete", referencedColumnName = "id", nullable = false)
    private Athlete idathlete;

    @ManyToOne
    @JoinColumn(name = "idband", referencedColumnName = "id", nullable = false)
    private Band idband;

    @Column(name = "sit")
    private Short sit;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")

	@Id
	@SequenceGenerator(name="BAND_ATHLETE_ID_GENERATOR", sequenceName="DBID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BAND_ATHLETE_ID_GENERATOR")
    private Integer id;

    @PersistenceContext
    transient EntityManager entityManager;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Athlete getIdathlete() {
        return idathlete;
    }

    public void setIdathlete(Athlete idathlete) {
        this.idathlete = idathlete;
    }

    public Band getIdband() {
        return idband;
    }

    public void setIdband(Band idband) {
        this.idband = idband;
    }

    public Short getSit() {
        return sit;
    }

    public void setSit(Short sit) {
        this.sit = sit;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final EntityManager entityManager() {
        EntityManager em = new BandAthlete().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countBandAthletes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM BandAthlete o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.BandAthlete> findAllBandAthletes() {
        return entityManager().createQuery("SELECT o FROM BandAthlete o", BandAthlete.class).getResultList();
    }

    public static com.canoepro2.domain.BandAthlete findBandAthlete(Integer id) {
        if (id == null) return null;
        return entityManager().find(BandAthlete.class, id);
    }

    public static List<com.canoepro2.domain.BandAthlete> findBandAthleteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM BandAthlete o", BandAthlete.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            BandAthlete attached = BandAthlete.findBandAthlete(this.id);
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
    public com.canoepro2.domain.BandAthlete merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        BandAthlete merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
