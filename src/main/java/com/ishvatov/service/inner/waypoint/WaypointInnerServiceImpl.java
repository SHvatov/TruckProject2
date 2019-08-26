package com.ishvatov.service.inner.waypoint;

import com.ishvatov.exception.DaoException;
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
                                    Mapper<Integer, WaypointEntity, WaypointDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException if entity with this UID already exists
     */
    @Override
    public void save(WaypointDto dto) {
        if (exists(dto.getId())) {
            throw new DaoException(getClass(), "save", "Entity with such UID already exists");
        } else {
            WaypointEntity entity = new WaypointEntity();
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
    public void update(WaypointDto dto) {
        WaypointEntity waypointEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, waypointEntity);
        repository.save(waypointEntity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     */
    @Override
    public void delete(Integer key) {
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
