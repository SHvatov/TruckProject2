package com.ishvatov.controller.manager;

import com.ishvatov.model.dto.CargoDto;
import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.enums.UserRoleType;
import com.ishvatov.service.inner.cargo.CargoInnerService;
import com.ishvatov.validation.cargo.ExistingCargoChecksOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;

/**
 * This controller is designed to process truck associated requests.
 *
 * @author Sergey Khvatov
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/manager/cargo")
public class CargoController {

    /**
     * {@link CargoInnerService} autowired instance.
     */
    private CargoInnerService cargoInnerService;

    /**
     * Default class constructor, used to autowire fields instances.
     *
     * @param cargoInnerService {@link CargoInnerService} implementation instance.
     */
    @Autowired
    public CargoController(CargoInnerService cargoInnerService) {
        this.cargoInnerService = cargoInnerService;
    }

    /**
     * Gets all the truck entities from the database.
     *
     * @return list of {@link CargoDto} object.
     */
    @GetMapping(path = "/list", produces = "application/json")
    public ResponseEntity<List<CargoDto>> listCargoEntities() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(cargoInnerService.findAll());
    }

    /**
     * Adds new entity to the database.
     *
     * @param entity entity to be added.
     * @return entity, that has been added to the system.
     */
    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity<CargoDto> addEntity(
        @RequestBody @Validated(Default.class) CargoDto entity) {
        cargoInnerService.save(entity);
        return ResponseEntity.status(HttpStatus.OK)
            .body(cargoInnerService.find(entity.getId()));
    }

    /**
     * Updates existing entity in the database.
     *
     * @param entity entity to be updated.
     * @return entity, that has been added to the system.
     */
    @PostMapping(path = "/update", produces = "application/json")
    public ResponseEntity<CargoDto> updateEntity(
        @RequestBody @Validated(ExistingCargoChecksOrder.class) CargoDto entity) {
        cargoInnerService.update(entity);
        return ResponseEntity.status(HttpStatus.OK)
            .body(cargoInnerService.find(entity.getId()));
    }

    /**
     * Adds new entity to the database.
     *
     * @param entity entity to be deleted.
     * @return id of the deleted entity.
     */
    @PostMapping(path = "/delete", produces = "application/json")
    public ResponseEntity<Integer> deleteEntity(@RequestBody @Valid CargoDto entity) {
        cargoInnerService.delete(entity.getId());
        return ResponseEntity.status(HttpStatus.OK).body(entity.getId());
    }
}
