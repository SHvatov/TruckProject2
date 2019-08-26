package com.ishvatov.service.inner.city;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.CityDto;
import com.ishvatov.model.entity.CityEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.CityRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Basic {@link CityInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class CityInnerServiceImpl
    extends AbstractInnerService<String, CityDto, CityEntity, CityRepository>
    implements CityInnerService {

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public CityInnerServiceImpl(CityRepository repository,
                                Mapper<String, CityEntity, CityDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException if entity with this UID already exists
     */
    @Override
    public void save(CityDto dto) {
        if (exists(dto.getId())) {
            throw new DaoException(getClass(), "save", "Entity with such UID already exists");
        } else {
            CityEntity entity = new CityEntity();
            mapper.map(dto, entity);
            repository.save(entity);
        }
    }

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
    @Override
    public void update(CityDto dto) {
        CityEntity cityEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, cityEntity);
        repository.save(cityEntity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     */
    @Override
    public void delete(String key) {
        repository.findById(key).ifPresent(repository::delete);
    }
}
