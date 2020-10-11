package com.mamalimomen.base.domains;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseEntity<PK extends Number> implements Serializable {

    @Transient
    private static final long serialVersionUID = -4281565028591599756L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private PK id;

    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted = false;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
