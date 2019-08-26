package com.ishvatov.service.inner.driver;

import com.ishvatov.exception.DaoException;
import com.ishvatov.exception.ValidationException;
import com.ishvatov.model.dto.DriverDto;
import com.ishvatov.model.entity.DriverEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.DriverRepository;
import com.ishvatov.model.repository.OrderRepository;
import com.ishvatov.model.repository.TruckRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Basic {@link DriverInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class DriverInnerServiceImpl
    extends AbstractInnerService<String, DriverDto, DriverEntity, DriverRepository>
    implements DriverInnerService {

    /**
     * Default class constructor.
     *
     * @param repository      repository instance.
     * @param mapper    mapper instance.
     */
    @Autowired
    public DriverInnerServiceImpl(DriverRepository repository,
                                  Mapper<String, DriverEntity, DriverDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException        if entity with this UID already exists
     */
    @Override
    public void save(DriverDto dto) {
        if (exists(dto.getId())) {
            throw new DaoException(getClass(), "save", "Entity with such UID already exists");
        } else {
            DriverEntity entity = new DriverEntity();
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
     * @throws DaoException        if entity with this UID already exists
     */
    @Override
    public void update(DriverDto dto) {
        DriverEntity driverEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, driverEntity);
        repository.save(driverEntity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     */
    @Override
    public void delete(String key) {
        Optional<DriverEntity> driverEntity = repository.findById(key);
        driverEntity.ifPresent(entity -> {
            Optional.ofNullable(entity.getTruck())
                .ifPresent(truckEntity -> truckEntity.removeDriver(entity));
            Optional.ofNullable(entity.getOrder())
                .ifPresent(orderEntity -> orderEntity.removeDriver(entity));
            repository.delete(entity);
        });
    }
}
