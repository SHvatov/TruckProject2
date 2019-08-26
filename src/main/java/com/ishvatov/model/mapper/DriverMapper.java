package com.ishvatov.model.mapper;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.DriverDto;
import com.ishvatov.model.entity.DriverEntity;
import com.ishvatov.model.entity.OrderEntity;
import com.ishvatov.model.entity.TruckEntity;
import com.ishvatov.model.repository.OrderRepository;
import com.ishvatov.model.repository.TruckRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of the mapper class, which is used to map driver entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class DriverMapper extends AbstractMapper<String, DriverEntity, DriverDto>
    implements Mapper<String, DriverEntity, DriverDto> {

    /**
     * Repository object, used to access Truck DAO.
     */
    private TruckRepository truckRepository;

    /**
     * Repository object, used to access Order DAO.
     */
    private OrderRepository orderRepository;

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param orderRepository order repo.
     * @param truckRepository truck repo.
     * @param mapper          mapper instance.
     */
    @Autowired
    public DriverMapper(ModelMapper mapper, TruckRepository truckRepository,
                        OrderRepository orderRepository) {
        super(mapper);
        this.truckRepository = truckRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Sets up the mapper instance and adds new type map.
     * Prevents mapper from setting user's password.
     */
    @PostConstruct
    public void setupMapper() {
        this.mapper.createTypeMap(this.entityClass, this.dtoClass).addMappings(m -> {
            m.skip(DriverDto::setTruckId);
            m.skip(DriverDto::setOrderId);
        }).setPostConverter(converToDto());

        this.mapper.createTypeMap(this.dtoClass, this.entityClass).addMappings(m -> {
            m.skip(DriverEntity::setOrder);
            m.skip(DriverEntity::setTruck);
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
    protected void mapSpecificFields(DriverEntity source, DriverDto destination) {
        destination.setTruckId(Objects.isNull(source.getTruck()) ? null : source.getTruck().getId());
        destination.setOrderId(Objects.isNull(source.getOrder()) ? null : source.getOrder().getId());
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    @Override
    protected void mapSpecificFields(DriverDto source, DriverEntity destination) {
        if (Objects.nonNull(source.getTruckId())) {
            setTruck(source.getTruckId(), destination);
        } else {
            removeTruck(destination);
        }

        if (Objects.nonNull(source.getOrderId())) {
            setOrder(source.getOrderId(), destination);
        } else {
            removeOrder(destination);
        }
    }

    /**
     * Updates the truck of the entity.
     *
     * @param truckId UID of the truck.
     * @param entity  Entity object.
     */
    private void setTruck(String truckId, DriverEntity entity) {
        Optional.ofNullable(entity.getTruck()).ifPresent(truckEntity -> truckEntity.removeDriver(entity));

        TruckEntity truckEntity = truckRepository.findById(truckId)
            .orElseThrow(() -> new DaoException(getClass(), "setTruck"));
        truckEntity.addDriver(entity);
    }

    /**
     * Deletes the truck of the entity.
     *
     * @param entity Entity object.
     */
    private void removeTruck(DriverEntity entity) {
        Optional.ofNullable(entity.getTruck())
            .ifPresent(e -> e.removeDriver(entity));
    }

    /**
     * Updates the order of the entity.
     *
     * @param orderId UID of the order.
     * @param entity  Entity object.
     */
    private void setOrder(Integer orderId, DriverEntity entity) {
        Optional.ofNullable(entity.getOrder()).ifPresent(orderEntity -> orderEntity.removeDriver(entity));
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new DaoException(getClass(), "setOrder"));
        orderEntity.addDriver(entity);
    }

    /**
     * Deletes the order of the entity.
     *
     * @param entity Entity object.
     */
    private void removeOrder(DriverEntity entity) {
        Optional.ofNullable(entity.getOrder())
            .ifPresent(e -> e.removeDriver(entity));
    }
}
