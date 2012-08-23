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
@Table(schema = "public", name = "gender")
@Configurable
public class Gender {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
    @Id
	@SequenceGenerator(name="GENDER_ID_GENERATOR", sequenceName="DBID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GENDER_ID_GENERATOR")
    private Integer id;

    @PersistenceContext
    transient EntityManager entityManager;

    @OneToMany(mappedBy = "idgender")
    private Set<Race> races;

    @Column(name = "gender", length = 1)
    @NotNull
    private String gender;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final EntityManager entityManager() {
        EntityManager em = new Gender().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countGenders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Gender o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.Gender> findAllGenders() {
        return entityManager().createQuery("SELECT o FROM Gender o", Gender.class).getResultList();
    }

    public static com.canoepro2.domain.Gender findGender(Integer id) {
        if (id == null) return null;
        return entityManager().find(Gender.class, id);
    }

    public static List<com.canoepro2.domain.Gender> findGenderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Gender o", Gender.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Gender attached = Gender.findGender(this.id);
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
    public com.canoepro2.domain.Gender merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Gender merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public Set<com.canoepro2.domain.Race> getRaces() {
        return races;
    }

    public void setRaces(Set<com.canoepro2.domain.Race> races) {
        this.races = races;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
