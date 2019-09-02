package com.ishvatov.service.inner.waypoint;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.WaypointDto;
import com.ishvatov.model.entity.WaypointEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.WaypointRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Basic {@link WaypointInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class WaypointInnerServiceImpl
    extends AbstractInnerService<Integer, WaypointDto, WaypointEntity, WaypointRepository>
    implements WaypointInnerService {

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public WaypointInnerServiceImpl(WaypointRepository repository,
                                    Mapper<WaypointEntity, WaypointDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void saveImpl(WaypointDto dto) {
        if (exists(dto.getId())) {
            throw new DataBaseException("Entity with such UID already exists");
        } else {
            WaypointEntity entity = new WaypointEntity();
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
    protected void updateImpl(WaypointDto dto) {
        WaypointEntity waypointEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + dto.getId() + "] exists"));
        mapper.map(dto, waypointEntity);
        repository.save(waypointEntity);
    }

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    @Override
    protected void deleteImpl(Integer key) {
        Optional<WaypointEntity> waypointEntity = repository.findById(key);
        waypointEntity.ifPresent(entity -> {
            Optional.ofNullable(entity.getOrder())
                .ifPresent(orderEntity -> orderEntity.removeWaypoint(entity));
            Optional.ofNullable(entity.getCargo())
                .ifPresent(orderEntity -> orderEntity.removeWaypoint(entity));
            repository.delete(entity);
        });
    }
}
