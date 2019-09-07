package com.ishvatov.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Abstract entity class, designed for entities with string ids.
 *
 * @author Sergey Khvatov
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractEntityWithStringId implements AbstractEntity {

    /**
     * Unique id of the entity in the DB.
     */
    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntityWithStringId)) return false;
        AbstractEntityWithStringId that = (AbstractEntityWithStringId) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
