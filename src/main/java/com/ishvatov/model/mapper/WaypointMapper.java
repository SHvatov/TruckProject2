package com.ishvatov.model.mapper;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.WaypointDto;
import com.ishvatov.model.entity.CargoEntity;
import com.ishvatov.model.entity.OrderEntity;
import com.ishvatov.model.entity.WaypointEntity;
import com.ishvatov.model.repository.CargoRepository;
import com.ishvatov.model.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of the mapper class, which is used to map waypoint entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class WaypointMapper extends AbstractMapper<WaypointEntity, WaypointDto>
    implements Mapper<WaypointEntity, WaypointDto> {

    /**
     * Repository object, used to access Orders DAO.
     */
    private OrderRepository orderRepository;

    /**
     * Repository object, used to access Cargo DAO.
     */
    private CargoRepository cargoRepository;

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param modelMapper mapper instance.
     */
    @Autowired
    public WaypointMapper(ModelMapper modelMapper, OrderRepository orderRepository, CargoRepository cargoRepository) {
        super(modelMapper);
        this.orderRepository = orderRepository;
        this.cargoRepository = cargoRepository;
    }

    /**
     * Sets up the mapper instance and adds new type map.
     * Prevents mapper from setting user's password.
     */
    @PostConstruct
    public void setupMapper() {
        this.mapper.createTypeMap(this.dtoClass, this.entityClass).addMappings(m -> {
            m.skip(WaypointEntity::setId);
            m.skip(WaypointEntity::setOrder);
            m.skip(WaypointEntity::setCargo);
        }).setPostConverter(convertToEntity());

        this.mapper.createTypeMap(this.entityClass, this.dtoClass).addMappings(m -> {
            m.skip(WaypointDto::setOrderId);
            m.skip(WaypointDto::setCargoId);
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
    protected void mapSpecificFields(WaypointEntity source, WaypointDto destination) {
        destination.setCargoId(Objects.isNull(source.getCargo())
            ? null
            : source.getCargo().getId());

        destination.setOrderId(Objects.isNull(source.getOrder())
            ? null
            : source.getOrder().getId());
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    @Override
    protected void mapSpecificFields(WaypointDto source, WaypointEntity destination) {
        if (Objects.nonNull(source.getOrderId())) {
            setOrder(source.getOrderId(), destination);
        } else {
            removeOrder(destination);
        }

        if (Objects.nonNull(source.getCargoId())) {
            setCargo(source.getCargoId(), destination);
        } else {
            removeCargo(destination);
        }
    }

    /**
     * Updates the cargo of the waypoint.
     *
     * @param cargoId     id of the cargo.
     * @param destination entity destination.
     */
    private void setCargo(Integer cargoId, WaypointEntity destination) {
        Optional.ofNullable(destination.getCargo())
            .ifPresent(cargoEntity -> cargoEntity.removeWaypoint(destination));
        CargoEntity cargoEntity = cargoRepository.findById(cargoId)
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + cargoId + "] exists"));
        cargoEntity.addWaypoint(destination);
    }

    /**
     * Removes cargo from the waypoint.
     *
     * @param destination entity destination.
     */
    private void removeCargo(WaypointEntity destination) {
        Optional.ofNullable(destination.getCargo())
            .ifPresent(cargoEntity -> cargoEntity.removeWaypoint(destination));
    }

    /**
     * Updates the order of the waypoint.
     *
     * @param orderId     id of the order.
     * @param destination entity destination.
     */
    private void setOrder(Integer orderId, WaypointEntity destination) {
        Optional.ofNullable(destination.getOrder())
            .ifPresent(orderEntity -> orderEntity.removeWaypoint(destination));
        OrderEntity orderEntity = orderRepository.findById(orderId)
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + orderId + "] exists"));
        orderEntity.addWaypoint(destination);
    }

    /**
     * Removes order from the waypoint.
     *
     * @param destination entity destination.
     */
    private void removeOrder(WaypointEntity destination) {
        Optional.ofNullable(destination.getOrder())
            .ifPresent(orderEntity -> orderEntity.removeWaypoint(destination));
    }
}
