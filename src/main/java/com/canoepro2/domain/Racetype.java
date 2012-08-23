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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "racetype")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "racetype", schema = "public")
@RooDbManaged(automaticallyDelete = true)
public class Racetype {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @PersistenceContext
    transient EntityManager entityManager;

    @OneToMany(mappedBy = "idracetype")
    private Set<Race> races;

    @Column(name = "racetype", length = 40)
    @NotNull
    private String racetype;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static final EntityManager entityManager() {
        EntityManager em = new Racetype().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countRacetypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Racetype o", Long.class).getSingleResult();
    }

    public static List<com.canoepro2.domain.Racetype> findAllRacetypes() {
        return entityManager().createQuery("SELECT o FROM Racetype o", Racetype.class).getResultList();
    }

    public static com.canoepro2.domain.Racetype findRacetype(Integer id) {
        if (id == null) return null;
        return entityManager().find(Racetype.class, id);
    }

    public static List<com.canoepro2.domain.Racetype> findRacetypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Racetype o", Racetype.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Racetype attached = Racetype.findRacetype(this.id);
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
    public com.canoepro2.domain.Racetype merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Racetype merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Set<com.canoepro2.domain.Race> getRaces() {
        return races;
    }

    public void setRaces(Set<com.canoepro2.domain.Race> races) {
        this.races = races;
    }

    public String getRacetype() {
        return racetype;
    }

    public void setRacetype(String racetype) {
        this.racetype = racetype;
    }
}
