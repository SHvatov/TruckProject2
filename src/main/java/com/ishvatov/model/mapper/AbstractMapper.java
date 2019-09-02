package com.ishvatov.model.mapper;

import com.ishvatov.model.dto.AbstractDto;
import com.ishvatov.model.entity.AbstractEntity;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Abstract mapper, which implements basic mapper interface. All its subclasses
 * must override protected methods in order to finish the mapping process.
 *
 * @param <E> type of the entity.
 * @param <T> type of the dto.
 * @author Sergey Khvatov
 */
public abstract class AbstractMapper<E extends AbstractEntity, T extends AbstractDto>
    implements Mapper<E, T> {

    /**
     * Mapper instance.
     */
    protected ModelMapper mapper;

    /**
     * Entity class.
     */
    protected Class<E> entityClass;

    /**
     * DTO class.
     */
    protected Class<T> dtoClass;

    /**
     * Default protected class constructor, which is used to
     * initialize mapper instance.
     *
     * @param modelMapper {@link ModelMapper} instance from the sub-class.
     */
    @SuppressWarnings("unchecked")
    protected AbstractMapper(ModelMapper modelMapper) {
        this.mapper = modelMapper;
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[0];
        this.dtoClass = (Class<T>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[1];
    }

    /**
     * Converts entity object into DTO.
     *
     * @param source entity object.
     * @return corresponding to this entity DTO object.
     */
    @Override
    public T map(E source) {
        return Objects.isNull(source) ? null : mapper.map(source, dtoClass);
    }

    /**
     * Updates internal entity object according
     * to the data, that is stored in the DTO object.
     *
     * @param source      DTO source object.
     * @param destination entity destination object.
     */
    @Override
    public void map(T source, E destination) {
        mapper.map(source, destination);
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      entity source.
     * @param destination dto destination.
     */
    protected abstract void mapSpecificFields(E source, T destination);

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    protected abstract void mapSpecificFields(T source, E destination);

    /**
     * Converts entity to dto.
     */
    protected Converter<E, T> converToDto() {
        return context -> {
            E source = context.getSource();
            T destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    /**
     * Converts entity to dto.
     */
    protected Converter<T, E> convertToEntity() {
        return context -> {
            T source = context.getSource();
            E destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }
}
