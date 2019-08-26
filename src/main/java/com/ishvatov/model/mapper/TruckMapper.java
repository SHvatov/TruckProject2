package com.ishvatov.model.mapper;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.TruckDto;
import com.ishvatov.model.entity.AbstractEntity;
import com.ishvatov.model.entity.DriverEntity;
import com.ishvatov.model.entity.OrderEntity;
import com.ishvatov.model.entity.TruckEntity;
import com.ishvatov.model.repository.DriverRepository;
import com.ishvatov.model.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Default implementation of the mapper class, which is used to map truck entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class TruckMapper extends AbstractMapper<String, TruckEntity, TruckDto>
    implements Mapper<String, TruckEntity, TruckDto> {

    /**
     * Repository object, used to access Drivers DAO.
     */
    private DriverRepository driverRepository;

    /**
     * Repository object, used to access Orders DAO.
     */
    private OrderRepository orderRepository;

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param driverRepository driver repo.
     * @param orderRepository  order repo.
     * @param modelMapper      mapper instance.
     */
    @Autowired
    public TruckMapper(ModelMapper modelMapper, DriverRepository driverRepository,
                       OrderRepository orderRepository) {
        super(modelMapper);
        this.driverRepository = driverRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Sets up the mapper instance and adds new type map.
     * Prevents mapper from setting user's password.
     */
    @PostConstruct
    public void setupMapper() {
        this.mapper.createTypeMap(this.dtoClass, this.entityClass).addMappings(m -> {
            m.skip(TruckEntity::setOrder);
            m.skip(TruckEntity::setAssignedDrivers);
        }).setPostConverter(convertToEntity());

        this.mapper.createTypeMap(this.entityClass, this.dtoClass).addMappings(m -> {
            m.skip(TruckDto::setAssignedDrivers);
            m.skip(TruckDto::setOrderId);
        }).setPostConverter(converToDto());
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      entity source.
     * @param destination dto destination.
     */
    @Override
    protected void mapSpecificFields(TruckEntity source, TruckDto destination) {
        destination.setOrderId(Objects.isNull(source.getOrder())
            ? null
            : source.getOrder().getId());

        destination.setAssignedDrivers(source.getAssignedDrivers()
            .stream()
            .filter(Objects::nonNull)
            .map(AbstractEntity::getId)
            .collect(Collectors.toSet()));
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    @Override
    protected void mapSpecificFields(TruckDto source, TruckEntity destination) {
        if (Objects.nonNull(source.getOrderId())) {
            setOrder(source.getOrderId(), destination);
        } else {
            removeOrder(destination);
        }

        if (!source.getAssignedDrivers().isEmpty()) {
            setAssignedDrivers(source.getAssignedDrivers()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()), destination);
        } else {
            clearDriversSet(destination);
        }
    }

    /**
     * Updates the order of the entity.
     *
     * @param orderId UID of the order.
     * @param entity  Entity object.
     */
    private void setOrder(Integer orderId, TruckEntity entity) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new DaoException(getClass(), "setOrder"));

        Optional.ofNullable(entity.getOrder()).ifPresent(e -> {
            e.setAssignedTruck(null);
            entity.setOrder(null);
        });

        orderEntity.setAssignedTruck(entity);
        entity.setOrder(orderEntity);
    }

    /**
     * Deletes the order of the entity.
     *
     * @param entity Entity object.
     */
    private void removeOrder(TruckEntity entity) {
        Optional.ofNullable(entity.getOrder()).ifPresent(e -> {
            e.setAssignedTruck(null);
            entity.setOrder(null);
        });
    }

    /**
     * Updates the set of drivers of the entity.
     *
     * @param assignedDrivers UID of the order.
     * @param entity          Entity object.
     */
    private void setAssignedDrivers(Set<String> assignedDrivers, TruckEntity entity) {
        clearDriversSet(entity);
        assignedDrivers.forEach(id -> {
            DriverEntity driverEntity = driverRepository.findById(id)
                .orElseThrow(() -> new DaoException(getClass(), "setAssignedDrivers"));
            Optional.ofNullable(driverEntity.getTruck())
                .ifPresent(e -> e.removeDriver(driverEntity));
            entity.addDriver(driverEntity);
        });
    }

    /**
     * Deletes the order of the entity.
     *
     * @param entity Entity object.
     */
    private void clearDriversSet(TruckEntity entity) {
        Set<DriverEntity> driverEntitySet = entity.getAssignedDrivers()
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        driverEntitySet.forEach(entity::removeDriver);
    }
}
