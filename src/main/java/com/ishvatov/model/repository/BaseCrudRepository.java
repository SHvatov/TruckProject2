package com.ishvatov.model.repository;

import com.ishvatov.model.entity.AbstractEntity;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Base project repository interface, which extends Spring Data
 * {@link Repository} interface, adding base CRUD abilities to
 * the extending repositories. This interface should be used
 * as a base interface for all repositories, that are working with instances, that
 * have only id as their unique identificator.
 *
 * @param <T> type of the entity, which extends {@link AbstractEntity}.
 * @param <U> type of the id of the entity.
 * @author Sergey Khvatov
 */
@NoRepositoryBean
public interface BaseCrudRepository<U, T extends AbstractEntity<U>> extends Repository<T, U> {

    /**
     * Saves entity to the DB.
     *
     * @param entity entity object.
     */
    void save(T entity);

    /**
     * Finds entity by its id in the DB.
     *
     * @param id id of the entity in the
     * @return object from the DB.
     */
    Optional<T> findById(U id);

    /**
     * Checks whether entity with such id exists
     * in the DB or not.
     *
     * @param id probable id of the entity.
     * @return true, if exists, false otherwise.
     */
    boolean existsById(U id);

    /**
     * Finds all the entities in the DB.
     *
     * @return list with all entities from DB.
     */
    List<T> findAll();

    /**
     * Counts the number of entities in the DB.
     *
     * @return number of the entities in the DB.
     */
    long count();

    /**
     * Deletes entity from the DB.
     *
     * @param entity entity to delete.
     */
    void delete(T entity);
}
