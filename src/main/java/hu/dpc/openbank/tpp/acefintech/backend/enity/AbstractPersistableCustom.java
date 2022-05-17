package hu.dpc.openbank.tpp.acefintech.backend.enity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import org.springframework.data.domain.Persistable;

/**
 * Abstract base class for entities.
 *
 * Inspired by {@link org.springframework.data.jpa.domain.AbstractPersistable}, but Id is always Long (and this class
 * thus does not require generic parameterization), and auto-generation is of strategy
 * {@link javax.persistence.GenerationType#IDENTITY}.
 *
 * The {@link #equals(Object)} and {@link #hashCode()} methods are NOT implemented here, which is untypical for JPA
 * (it's usually implemented based on the Id), because "we end up with issues on OpenJPA" (TODO clarify this).
 */
@MappedSuperclass
public abstract class AbstractPersistableCustom implements Persistable<Long>, Serializable {

    private static final long serialVersionUID = 9181640245194392646L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private boolean isNew = true;

    @Override
    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }
}
