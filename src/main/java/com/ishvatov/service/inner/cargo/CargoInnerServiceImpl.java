package com.ishvatov.service.inner.cargo;

import com.ishvatov.exception.DaoException;
import com.ishvatov.exception.ValidationException;
import com.ishvatov.model.dto.CargoDto;
import com.ishvatov.model.entity.CargoEntity;
import com.ishvatov.model.entity.WaypointEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.CargoRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Basic {@link CargoInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class CargoInnerServiceImpl
    extends AbstractInnerService<Integer, CargoDto, CargoEntity, CargoRepository>
    implements CargoInnerService {

    /**
     * Default class constructor.
     *
     * @param repository  repository instance.
     * @param mapper mapper instance.
     */
    @Autowired
    public CargoInnerServiceImpl(CargoRepository repository,
                                 Mapper<Integer, CargoEntity, CargoDto> mapper) {
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
    public void save(CargoDto dto) {
        CargoEntity entity = new CargoEntity();
        mapper.map(dto, entity);
        repository.save(entity);
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
    public void update(CargoDto dto) {
        CargoEntity cargoEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, cargoEntity);
        repository.save(cargoEntity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     * @throws ValidationException if key is null.
     */
    @Override
    public void delete(Integer key) {
        Optional<CargoEntity> cargoEntity = repository.findById(key);
        cargoEntity.ifPresent(entity -> {
            Set<WaypointEntity> set = entity.getAssignedWaypoints()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            set.forEach(entity::removeWaypoint);
            repository.delete(entity);
        });
    }
}
