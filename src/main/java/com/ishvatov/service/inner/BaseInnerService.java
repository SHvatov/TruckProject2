package com.ishvatov.service.inner;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.AbstractDto;

import java.util.List;

/**
 * Basic service interface.
 *
 * @param <T> DTO type.
 * @param <U> type of the unique key.
 */
public interface BaseInnerService<U, T extends AbstractDto<U>> {

    /**
     * Finds entity by it's unique id.
     *
     * @param key unique key of the id.
     * @return Unique entity with this entity.
     * @throws DaoException if no entity with such unique key exists.
     */
    T find(U key);

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException if entity with this UID already exists
     */
    void save(T dto);

    /**
     * Updates data in the database. If fields in teh DTO
     * are not null, then update them. If are null, then
     * if corresponding filed in the Entity is nullable,
     * then set it to null and remove all connections,
     * otherwise throw NPE.
     *
     * @param dto values to update in the entity.
     * @throws DaoException if entity with this UID already exists
     */
    void update(T dto);

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
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
     */
    boolean exists(U key);
}
