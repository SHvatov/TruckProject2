package com.ishvatov.model.repository;

import com.ishvatov.model.entity.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base project repository interface, which extends Spring Data
 * {@link CrudRepository} interface, adding base CRUD abilities to
 * the extending repositories.
 *
 * @param <T> type of the entity, which extends {@link AbstractEntity}.
 * @param <U> type of the id of the entity.
 * @author Sergey Khvatov
 */
@NoRepositoryBean
public interface BaseCrudRepository<U, T extends AbstractEntity> extends CrudRepository<T, U> {
    // empty
}
