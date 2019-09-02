package com.ishvatov.service.inner;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.AbstractDto;
import com.ishvatov.model.entity.AbstractEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.BaseCrudRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Implementation of the basic CRUD methods, that are common for all
 * DTO objects. Also contains common methods, that should be used
 * according to the design.
 *
 * @param <U> type of the id of the DTO.
 * @param <T> dto type.
 * @param <V> entity type.
 * @param <R> repository type.
 * @author Sergey Khvatov
 */
public abstract class AbstractInnerService<U,
    T extends AbstractDto,
    V extends AbstractEntity,
    R extends BaseCrudRepository<U, V>>
    implements BaseInnerService<U, T> {

    /**
     * Repository instance.
     */
    protected R repository;

    /**
     * Mapper instance.
     */
    protected Mapper<V, T> mapper;

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    public AbstractInnerService(R repository,
                                Mapper<V, T> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DataBaseException if entity with this UID already exists or if
     *                           input data violates the integrity of the data in the database or
     *                           if connection to the database is lost.
     */
    @Override
    public void save(T dto) {
        try {
            saveImpl(dto);
        } catch (DataIntegrityViolationException | DataSourceLookupFailureException exception) {
            throw new DataBaseException(exception);
        }
    }

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
    @Override
    public void update(T dto) {
        try {
            updateImpl(dto);
        } catch (DataIntegrityViolationException | DataSourceLookupFailureException exception) {
            throw new DataBaseException(exception);
        }
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     * @throws IllegalArgumentException if key is null.
     * @throws DataBaseException        if connection to the DB is lost.
     */
    @Override
    public void delete(U key) {
        try {
            deleteImpl(key);
        } catch (DataSourceLookupFailureException exception) {
            throw new DataBaseException(exception);
        }
    }

    /**
     * Finds entity by it's unique id.
     *
     * @param key unique key of the id.
     * @return Unique entity with this entity.
     * @throws DataBaseException        if no entity with such unique key exists.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public T find(U key) {
        V entity = repository.findById(key)
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + key + "] exists"));
        return mapper.map(entity);
    }

    /**
     * Finds all the entities in the DB.
     *
     * @return list with all the entities, empty list if nothing found.
     */
    @Override
    public List<T> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
            .filter(Objects::nonNull)
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    /**
     * Checks if the input key is unique or not.
     *
     * @param key key to check.
     * @return true, if this key is unique in the DB, false otherwise.
     * @throws IllegalArgumentException if id is null.
     */
    @Override
    public boolean exists(U key) {
        return repository.existsById(key);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    protected abstract void saveImpl(T dto);

    /**
     * Implementation of the update method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    protected abstract void updateImpl(T dto);

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    protected abstract void deleteImpl(U key);
}
