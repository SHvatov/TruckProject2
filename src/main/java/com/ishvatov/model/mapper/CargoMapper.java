package com.ishvatov.model.mapper;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.CargoDto;
import com.ishvatov.model.entity.AbstractEntity;
import com.ishvatov.model.entity.CargoEntity;
import com.ishvatov.model.entity.WaypointEntity;
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
 * Default implementation of the mapper class, which is used to map cargo entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class CargoMapper extends AbstractMapper<CargoEntity, CargoDto>
    implements Mapper<CargoEntity, CargoDto> {

    /**
     * Repository object, used to access Waypoint DAO.
     */
    private WaypointRepository waypointRepository;

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param waypointRepository waypoint repo.
     * @param modelMapper        mapper instance.
     */
    @Autowired
    public CargoMapper(ModelMapper modelMapper, WaypointRepository waypointRepository) {
        super(modelMapper);
        this.waypointRepository = waypointRepository;
    }

    /**
     * Sets up the mapper instance and adds new type map.
     * Prevents mapper from setting user's password.
     */
    @PostConstruct
    public void setupMapper() {
        this.mapper.createTypeMap(this.dtoClass, this.entityClass).addMappings(m -> {
            m.skip(CargoEntity::setId);
            m.skip(CargoEntity::setAssignedWaypoints);
        }).setPostConverter(convertToEntity());

        this.mapper.createTypeMap(this.entityClass, this.dtoClass).addMappings(m ->
            m.skip(CargoDto::setAssignedWaypoints)
        ).setPostConverter(converToDto());
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      entity source.
     * @param destination dto destination.
     */
    @Override
    protected void mapSpecificFields(CargoEntity source, CargoDto destination) {
        destination.setAssignedWaypoints(source.getAssignedWaypoints()
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
    protected void mapSpecificFields(CargoDto source, CargoEntity destination) {
        if (source.getAssignedWaypoints().isEmpty()) {
            clearAssignedWaypoints(destination);
        } else {
            setAssignedWaypoints(destination, source.getAssignedWaypoints());
        }
    }

    /**
     * Clears the set of the waypoints, that are assigned to this cargo.
     *
     * @param entity entity object.
     */
    private void clearAssignedWaypoints(CargoEntity entity) {
        Set<WaypointEntity> set = entity.getAssignedWaypoints()
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        set.forEach(entity::removeWaypoint);
    }

    /**
     * Updates the set of the waypoints, that are assigned to this cargo.
     *
     * @param entity    entity object.
     * @param waypoints set of the waypoints.
     */
    private void setAssignedWaypoints(CargoEntity entity, Set<Integer> waypoints) {
        clearAssignedWaypoints(entity);
        waypoints.forEach(id -> {
            WaypointEntity waypointEntity = waypointRepository.findById(id)
                .orElseThrow(() -> new DataBaseException("No entity with id: [" + id + "] exists"));
            Optional.ofNullable(waypointEntity.getCargo())
                .ifPresent(cargoEntity -> cargoEntity.removeWaypoint(waypointEntity));
            entity.addWaypoint(waypointEntity);
        });
    }
}
