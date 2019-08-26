package com.ishvatov.service.inner;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.AbstractDto;
import com.ishvatov.model.entity.AbstractEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.BaseCrudRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    T extends AbstractDto<U>,
    V extends AbstractEntity<U>,
    R extends BaseCrudRepository<U, V>>
    implements BaseInnerService<U, T> {

    /**
     * Repository instance.
     */
    protected R repository;

    /**
     * Mapper instance.
     */
    protected Mapper<U, V, T> mapper;

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    public AbstractInnerService(R repository,
                                Mapper<U, V, T> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Finds entity by it's unique id.
     *
     * @param key unique key of the id.
     * @return Unique entity with this entity.
     * @throws DaoException if no entity with such unique key exists.
     */
    @Override
    public T find(U key) {
        V entity = repository.findById(key)
            .orElseThrow(() -> new DaoException(getClass(), "find"));
        return mapper.map(entity);
    }

    /**
     * Finds all the entities in the DB.
     *
     * @return list with all the entities, empty list if nothing found.
     */
    @Override
    public List<T> findAll() {
        return repository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    /**
     * Checks if the input key is unique or not.
     *
     * @param key key to check.
     * @return true, if this key is unique in the DB, false otherwise.
     */
    @Override
    public boolean exists(U key) {
        return repository.existsById(key);
    }
}
