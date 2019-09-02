package com.ishvatov.service.inner.truck;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.TruckDto;
import com.ishvatov.model.entity.DriverEntity;
import com.ishvatov.model.entity.TruckEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.TruckRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Basic {@link TruckInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class TruckInnerServiceImpl
    extends AbstractInnerService<String, TruckDto, TruckEntity, TruckRepository>
    implements TruckInnerService {

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public TruckInnerServiceImpl(TruckRepository repository,
                                 Mapper<TruckEntity, TruckDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void saveImpl(TruckDto dto) {
        if (exists(dto.getId())) {
            throw new DataBaseException("Entity with such UID already exists");
        } else {
            TruckEntity entity = new TruckEntity();
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
    protected void updateImpl(TruckDto dto) {
        TruckEntity entity = repository.findById(dto.getId())
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + dto.getId() + "] exists"));
        mapper.map(dto, entity);
        repository.save(entity);
    }

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    @Override
    protected void deleteImpl(String key) {
        Optional<TruckEntity> truckEntity = repository.findById(key);
        truckEntity.ifPresent(entity -> {
            Optional.ofNullable(entity.getOrder()).ifPresent(e -> {
                e.setAssignedTruck(null);
                entity.setOrder(null);
            });

            Set<DriverEntity> driverEntitySet = entity.getAssignedDrivers()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            driverEntitySet.forEach(entity::removeDriver);

            repository.delete(entity);
        });
    }
}
