package com.ishvatov.service.inner.order;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.OrderDto;
import com.ishvatov.model.entity.DriverEntity;
import com.ishvatov.model.entity.OrderEntity;
import com.ishvatov.model.entity.WaypointEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.OrderRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Basic {@link OrderInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class OrderInnerServiceImpl
    extends AbstractInnerService<Integer, OrderDto, OrderEntity, OrderRepository>
    implements OrderInnerService {

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public OrderInnerServiceImpl(OrderRepository repository,
                                 Mapper<Integer, OrderEntity, OrderDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException if entity with this UID already exists
     */
    @Override
    public void save(OrderDto dto) {
        if (exists(dto.getId())) {
            throw new DaoException(getClass(), "save", "Entity with such UID already exists");
        } else {
            OrderEntity entity = new OrderEntity();
            mapper.map(dto, entity);
            repository.save(entity);
        }
    }

    /**
     * Updates data in the database. If fields in teh DTO
     * are not null, then update them. If are null, then
     * if corresponding filed in the Entity is nullable,
     * then set it to null and remove all connections,
     * otherwise throw VE.
     *
     * @param dto values to update in the entity.
     * @throws DaoException if entity with this UID already exists
     */
    @Override
    public void update(OrderDto dto) {
        OrderEntity orderEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, orderEntity);
        repository.save(orderEntity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     */
    @Override
    public void delete(Integer key) {
        Optional<OrderEntity> orderEntity = repository.findById(key);
        orderEntity.ifPresent(entity -> {
            Set<DriverEntity> driverEntitySet = entity.getAssignedDrivers()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            driverEntitySet.forEach(entity::removeDriver);

            Optional.ofNullable(entity.getAssignedTruck()).ifPresent(e -> {
                e.setOrder(null);
                entity.setAssignedTruck(null);
            });

            Set<WaypointEntity> waypointEntitySet = entity.getAssignedWaypoints()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            waypointEntitySet.forEach(entity::removeWaypoint);

            repository.delete(entity);
        });
    }
}
