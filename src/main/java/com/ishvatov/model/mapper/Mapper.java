package com.ishvatov.model.mapper;

import com.ishvatov.model.dto.AbstractDto;
import com.ishvatov.model.entity.AbstractEntity;

/**
 * Basic interface which defines methods, that are used to map objects from
 * dto to entity and back.
 *
 * @param <V> type of the id of the object.
 * @param <E> type of the entity object.
 * @param <T> type of the dto object.
 */
public interface Mapper<V, E extends AbstractEntity<V>, T extends AbstractDto<V>> {

    /**
     * Converts entity object into DTO.
     *
     * @param source entity object.
     * @return corresponding to this entity DTO object.
     */
    T map(E source);

    /**
     * Updates internal entity object according
     * to the data, that is stored in the DTO object.
     *
     * @param source      DTO source object.
     * @param destination entity destination object.
     */
    void map(T source, E destination);
}
