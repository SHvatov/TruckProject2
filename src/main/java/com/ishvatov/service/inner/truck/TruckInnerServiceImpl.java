package com.ishvatov.service.inner.truck;

import com.ishvatov.exception.DaoException;
import com.ishvatov.exception.ValidationException;
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
    public TruckInnerServiceImpl(TruckRepository repository, Mapper<String, TruckEntity, TruckDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException        if entity with this UID already exists
     * @throws ValidationException if DTO field, which is corresponding to
     *                             the not nullable field in the Entity object is null.
     */
    @Override
    public void save(TruckDto dto) {
        if (exists(dto.getId())) {
            throw new DaoException(getClass(), "save", "Entity with such UID already exists");
        } else {
            TruckEntity entity = new TruckEntity();
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
     * @throws ValidationException if DTO field, which is corresponding to
     *                             the not nullable field in the Entity object is null.
     */
    @Override
    public void update(TruckDto dto) {
        TruckEntity entity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, entity);
        repository.save(entity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     * @throws ValidationException if key is null.
     */
    @Override
    public void delete(String key) {
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
