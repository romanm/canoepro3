package com.canoepro2.domain;

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
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "oldgroup")
@Configurable
public class Oldgroup {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")

	@Id
	@SequenceGenerator(name="OLDGROUP_ID_GENERATOR", sequenceName="DBID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="OLDGROUP_ID_GENERATOR")
    private Integer id;

    @PersistenceContext
    transient EntityManager entityManager;

    @OneToMany(mappedBy = "idoldgroup")
    private Set<Race> races;

    @Column(name = "oldmin", unique = true)
    @NotNull
    private Integer oldmin;

    @Column(name = "oldmax", unique = true)
    @NotNull
    private Integer oldmax;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final EntityManager entityManager() {
        EntityManager em = new Oldgroup().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countOldgroups() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Oldgroup o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.Oldgroup> findAllOldgroups() {
        return entityManager().createQuery("SELECT o FROM Oldgroup o", Oldgroup.class).getResultList();
    }

    public static com.canoepro2.domain.Oldgroup findOldgroup(Integer id) {
        if (id == null) return null;
        return entityManager().find(Oldgroup.class, id);
    }

    public static List<com.canoepro2.domain.Oldgroup> findOldgroupEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Oldgroup o", Oldgroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Oldgroup attached = Oldgroup.findOldgroup(this.id);
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
    public com.canoepro2.domain.Oldgroup merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Oldgroup merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public Set<com.canoepro2.domain.Race> getRaces() {
        return races;
    }

    public void setRaces(Set<com.canoepro2.domain.Race> races) {
        this.races = races;
    }

    public Integer getOldmin() {
        return oldmin;
    }

    public void setOldmin(Integer oldmin) {
        this.oldmin = oldmin;
    }

    public Integer getOldmax() {
        return oldmax;
    }

    public void setOldmax(Integer oldmax) {
        this.oldmax = oldmax;
    }
}
