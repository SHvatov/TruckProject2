package com.ishvatov.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Abstract DTO class representation. Stores such basic fields
 * of the DTO object, as unique identificator.
 *
 * <U> type of the unique identificator.
 *
 * @author Sergey Khvatov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractDto<U> {

    /**
     * Unique identificator of the object.
     */
    protected U id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractDto)) return false;
        AbstractDto<?> that = (AbstractDto<?>) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
