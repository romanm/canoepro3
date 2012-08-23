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
@Table(schema = "public", name = "boot")
@Configurable
public class Boot {

    @OneToMany(mappedBy = "idboot")
    private Set<Race> races;

    @Column(name = "boot", length = 2)
    @NotNull
    private String boot;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")

	@Id
	@SequenceGenerator(name="BOOT_ID_GENERATOR", sequenceName="DBID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOOT_ID_GENERATOR")
    private Integer id;

    @PersistenceContext
    transient EntityManager entityManager;

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Set<com.canoepro2.domain.Race> getRaces() {
        return races;
    }

    public void setRaces(Set<com.canoepro2.domain.Race> races) {
        this.races = races;
    }

    public String getBoot() {
        return boot;
    }

    public void setBoot(String boot) {
        this.boot = boot;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final EntityManager entityManager() {
        EntityManager em = new Boot().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countBoots() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Boot o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.Boot> findAllBoots() {
        return entityManager().createQuery("SELECT o FROM Boot o", Boot.class).getResultList();
    }

    public static com.canoepro2.domain.Boot findBoot(Integer id) {
        if (id == null) return null;
        return entityManager().find(Boot.class, id);
    }

    public static List<com.canoepro2.domain.Boot> findBootEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Boot o", Boot.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Boot attached = Boot.findBoot(this.id);
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
    public com.canoepro2.domain.Boot merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Boot merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
