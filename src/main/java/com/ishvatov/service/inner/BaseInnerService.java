package com.ishvatov.service.inner;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.AbstractDto;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

/**
 * Basic service interface.
 *
 * @param <T> DTO type.
 * @param <U> type of the unique key.
 */
public interface BaseInnerService<U, T extends AbstractDto> {

    /**
     * Finds entity by it's unique id.
     *
     * @param key unique key of the id.
     * @return Unique entity with this entity.
     * @throws DataBaseException        if no entity with such unique key exists.
     * @throws IllegalArgumentException if id is null.
     */
    T find(U key);

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DataBaseException if entity with this UID already exists or if
     *                           input data violates the integrity of the data in the database or
     *                           if connection to the database is lost.
     */
    void save(T dto);

    /**
     * Updates data in the database. If fields in the DTO
     * are not null, then update them. If are null, then
     * if corresponding filed in the Entity is nullable,
     * then set it to null and remove all connections,
     * otherwise throw {@link DataBaseException}
     * because of the {@link DataIntegrityViolationException}.
     *
     * @param dto values to update in the entity.
     * @throws DataBaseException if entity with this UID does not exists or if
     *                           input data violates the integrity of the data in the database or
     *                           if connection to the database is lost.
     */
    void update(T dto);

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     * @throws IllegalArgumentException if key is null.
     * @throws DataBaseException        if connection to the DB is lost.
     */
    void delete(U key);

    /**
     * Finds all the entities in the DB.
     *
     * @return list with all the entities, empty list if nothing found.
     */
    List<T> findAll();

    /**
     * Checks if the input key is unique or not.
     *
     * @param key key to check.
     * @return true, if this key is unique in the DB, false otherwise.
     * @throws IllegalArgumentException if id is null.
     */
    boolean exists(U key);
}
