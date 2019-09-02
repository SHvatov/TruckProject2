package com.ishvatov.service.inner.driver;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.DriverDto;
import com.ishvatov.model.entity.DriverEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.DriverRepository;
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
                                  Mapper<DriverEntity, DriverDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void saveImpl(DriverDto dto) {
        if (exists(dto.getId())) {
            throw new DataBaseException("Entity with such UID already exists");
        } else {
            DriverEntity entity = new DriverEntity();
            mapper.map(dto, entity);
            repository.save(entity);
        }
    }

    /**
     * Implementation of the update method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void updateImpl(DriverDto dto) {
        DriverEntity driverEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + dto.getId() + "] exists"));
        mapper.map(dto, driverEntity);
        repository.save(driverEntity);
    }

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    @Override
    protected void deleteImpl(String key) {
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
