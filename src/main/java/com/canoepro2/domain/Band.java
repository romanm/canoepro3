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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@Table(schema = "public", name = "band")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "band", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Band {

    @PersistenceContext
    transient EntityManager entityManager;

    @OneToMany(mappedBy = "idband")
    private Set<BandAthlete> bandAthletes;

    @ManyToOne
    @JoinColumn(name = "idrace", referencedColumnName = "id", nullable = false)
    private Race idrace;

    @Column(name = "band")
    @NotNull
    private Integer band;

    @Column(name = "rank")
    private Short rank;

    @Column(name = "finish")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date finish;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static final EntityManager entityManager() {
        EntityManager em = new Band().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countBands() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Band o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.Band> findAllBands() {
        return entityManager().createQuery("SELECT o FROM Band o", Band.class).getResultList();
    }

    public static com.canoepro2.domain.Band findBand(Integer id) {
        if (id == null) return null;
        return entityManager().find(Band.class, id);
    }

    public static List<com.canoepro2.domain.Band> findBandEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Band o", Band.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Band attached = Band.findBand(this.id);
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
    public com.canoepro2.domain.Band merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Band merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public Set<com.canoepro2.domain.BandAthlete> getBandAthletes() {
        return bandAthletes;
    }

    public void setBandAthletes(Set<com.canoepro2.domain.BandAthlete> bandAthletes) {
        this.bandAthletes = bandAthletes;
    }

    public Race getIdrace() {
        return idrace;
    }

    public void setIdrace(Race idrace) {
        this.idrace = idrace;
    }

    public Integer getBand() {
        return band;
    }

    public void setBand(Integer band) {
        this.band = band;
    }

    public Short getRank() {
        return rank;
    }

    public void setRank(Short rank) {
        this.rank = rank;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
