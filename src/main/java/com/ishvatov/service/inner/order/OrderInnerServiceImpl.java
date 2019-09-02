package com.ishvatov.service.inner.order;

import com.ishvatov.exception.DataBaseException;
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
                                 Mapper<OrderEntity, OrderDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void saveImpl(OrderDto dto) {
        if (exists(dto.getId())) {
            throw new DataBaseException("Entity with such UID already exists");
        } else {
            OrderEntity entity = new OrderEntity();
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
    protected void updateImpl(OrderDto dto) {
        OrderEntity orderEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + dto.getId() + "] exists"));
        mapper.map(dto, orderEntity);
        repository.save(orderEntity);
    }

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    @Override
    protected void deleteImpl(Integer key) {
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
