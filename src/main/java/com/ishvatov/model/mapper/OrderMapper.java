package com.ishvatov.model.mapper;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.OrderDto;
import com.ishvatov.model.entity.*;
import com.ishvatov.model.repository.DriverRepository;
import com.ishvatov.model.repository.TruckRepository;
import com.ishvatov.model.repository.WaypointRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation of the mapper class, which is used to map order entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class OrderMapper extends AbstractMapper<OrderEntity, OrderDto>
    implements Mapper<OrderEntity, OrderDto> {

    /**
     * Repository object, used to access Driver DAO.
     */
    private DriverRepository driverRepository;

    /**
     * Repository object, used to access Waypoints DAO.
     */
    private WaypointRepository waypointRepository;

    /**
     * Repository object, used to access Truck DAO.
     */
    private TruckRepository truckRepository;

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param truckRepository    truck repo.
     * @param waypointRepository waypoint repo.
     * @param driverRepository   driver repo.
     * @param mapper             mapper instance.
     */
    @Autowired
    public OrderMapper(ModelMapper mapper, DriverRepository driverRepository,
                       TruckRepository truckRepository, WaypointRepository waypointRepository) {
        super(mapper);
        this.truckRepository = truckRepository;
        this.driverRepository = driverRepository;
        this.waypointRepository = waypointRepository;
    }

    /**
     * Sets up the mapper instance and adds new type map.
     * Prevents mapper from setting user's password.
     */
    @PostConstruct
    public void setupMapper() {
        this.mapper.createTypeMap(this.entityClass, this.dtoClass).addMappings(m -> {
            m.skip(OrderDto::setTruckId);
            m.skip(OrderDto::setAssignedDrivers);
            m.skip(OrderDto::setAssignedWaypoints);
        }).setPostConverter(converToDto());

        this.mapper.createTypeMap(this.dtoClass, this.entityClass).addMappings(m -> {
            m.skip(OrderEntity::setAssignedTruck);
            m.skip(OrderEntity::setAssignedDrivers);
            m.skip(OrderEntity::setAssignedWaypoints);
        }).setPostConverter(convertToEntity());
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      entity source.
     * @param destination dto destination.
     */
    @Override
    protected void mapSpecificFields(OrderEntity source, OrderDto destination) {
        destination.setTruckId(Objects.isNull(source.getAssignedTruck())
            ? null
            : source.getAssignedTruck().getId());

        destination.setAssignedDrivers(source.getAssignedDrivers()
            .stream()
            .filter(Objects::nonNull)
            .map(AbstractEntity::getId)
            .collect(Collectors.toSet()));

        destination.setAssignedWaypoints(source.getAssignedWaypoints()
            .stream()
            .filter(Objects::nonNull)
            .map(AbstractEntity::getId)
            .collect(Collectors.toList()));
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    @Override
    protected void mapSpecificFields(OrderDto source, OrderEntity destination) {
        if (Objects.nonNull(source.getTruckId())) {
            setTruck(source.getTruckId(), destination);
        } else {
            removeTruck(destination);
        }

        if (!source.getAssignedDrivers().isEmpty()) {
            setAssignedDrivers(source.getAssignedDrivers()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()), destination);
        } else {
            clearAssignedDrivers(destination);
        }

        if (!source.getAssignedWaypoints().isEmpty()) {
            setAssignedWaypoints(source.getAssignedWaypoints()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()), destination);
        } else {
            clearAssignedWaypoints(destination);
        }
    }

    /**
     * Updates the drivers set of the entity.
     *
     * @param assignedDrivers set of UIDs of the drivers.
     * @param entity          Entity object.
     */
    private void setAssignedDrivers(Set<String> assignedDrivers, OrderEntity entity) {
        clearAssignedDrivers(entity);
        assignedDrivers.forEach(id -> {
            DriverEntity driverEntity = driverRepository.findById(id)
                .orElseThrow(() -> new DataBaseException("No entity with id: [" + id + "] exists"));
            Optional.ofNullable(driverEntity.getOrder()).ifPresent(e -> e.removeDriver(driverEntity));
            entity.addDriver(driverEntity);
        });
    }

    /**
     * Clears the drivers set of the entity.
     *
     * @param entity Entity object.
     */
    private void clearAssignedDrivers(OrderEntity entity) {
        Set<DriverEntity> driverEntitySet = entity.getAssignedDrivers()
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        driverEntitySet.forEach(entity::removeDriver);
    }

    /**
     * Updates the trucks set of the entity.
     *
     * @param truckId UID of the truck.
     * @param entity   Entity object.
     */
    private void setTruck(String truckId, OrderEntity entity) {
        Optional.ofNullable(entity.getAssignedTruck()).ifPresent(e -> {
            e.setOrder(null);
            entity.setAssignedTruck(null);
        });

        TruckEntity truckEntity = truckRepository.findById(truckId)
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + truckId + "] exists"));
        entity.setAssignedTruck(truckEntity);
        truckEntity.setOrder(entity);
    }

    /**
     * Clears the trucks set of the entity.
     *
     * @param entity Entity object.
     */
    private void removeTruck(OrderEntity entity) {
        Optional.ofNullable(entity.getAssignedTruck()).ifPresent(e -> {
            e.setOrder(null);
            entity.setAssignedTruck(null);
        });
    }

    /**
     * Updates the waypoints set of the entity.
     *
     * @param assignedWaypoints set of UIDs of the drivers.
     * @param entity            Entity object.
     */
    private void setAssignedWaypoints(Set<Integer> assignedWaypoints, OrderEntity entity) {
        clearAssignedWaypoints(entity);
        assignedWaypoints.forEach(id -> {
            WaypointEntity wayPointEntity = waypointRepository.findById(id)
                .orElseThrow(() -> new DataBaseException("No entity with id: [" + id + "] exists"));
            Optional.ofNullable(wayPointEntity.getOrder())
                .ifPresent(e -> e.removeWaypoint(wayPointEntity));
            entity.addWaypoint(wayPointEntity);
        });
    }

    /**
     * Clears the waypoints set of the entity.
     *
     * @param entity Entity object.
     */
    private void clearAssignedWaypoints(OrderEntity entity) {
        Set<WaypointEntity> waypointEntitySet = entity.getAssignedWaypoints()
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        waypointEntitySet.forEach(entity::removeWaypoint);
    }
}
