package com.ishvatov.service.inner.cargo;

import com.ishvatov.exception.DataBaseException;
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
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public CargoInnerServiceImpl(CargoRepository repository,
                                 Mapper<CargoEntity, CargoDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void saveImpl(CargoDto dto) {
        CargoEntity entity = new CargoEntity();
        mapper.map(dto, entity);
        repository.save(entity);
    }

    /**
     * Implementation of the update method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void updateImpl(CargoDto dto) {
        CargoEntity cargoEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + dto.getId() + "] exists"));
        mapper.map(dto, cargoEntity);
        repository.save(cargoEntity);
    }

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    @Override
    protected void deleteImpl(Integer key) {
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
