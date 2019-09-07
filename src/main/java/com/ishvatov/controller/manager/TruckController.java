package com.ishvatov.controller.manager;

import com.ishvatov.model.dto.CargoDto;
import com.ishvatov.model.dto.TruckDto;
import com.ishvatov.model.enums.CargoStatusType;
import com.ishvatov.service.inner.cargo.CargoInnerService;
import com.ishvatov.service.inner.truck.TruckInnerService;
import com.ishvatov.validation.truck.ExistingTruckChecksOrder;
import com.ishvatov.validation.truck.NotExistingTruckChecksOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.List;

/**
 * This controller is designed to process truck associated requests.
 *
 * @author Sergey Khvatov
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/manager/trucks")
public class TruckController {

    /**
     * {@link TruckInnerService} autowired instance.
     */
    private TruckInnerService truckInnerService;

    /**
     * Default class constructor, used to autowire fields instances.
     *
     * @param truckInnerService {@link TruckInnerService} implementation instance.
     */
    @Autowired
    public TruckController(TruckInnerService truckInnerService) {
        this.truckInnerService = truckInnerService;
    }

    /**
     * Gets all the truck entities from the database.
     *
     * @return list of {@link TruckDto} object.
     */
    @GetMapping(path = "/list", produces = "application/json")
    public ResponseEntity<List<TruckDto>> listTruckEntities() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(truckInnerService.findAll());
    }

    /**
     * Adds new entity to the database.
     *
     * @param entity entity to be added.
     * @return entity, that has been added to the system.
     */
    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity<TruckDto> addEntity(
        @RequestBody @Validated(NotExistingTruckChecksOrder.class) TruckDto entity) {
        truckInnerService.save(entity);
        return ResponseEntity.status(HttpStatus.OK)
            .body(truckInnerService.find(entity.getId()));
    }

    /**
     * Updates existing entity in the database.
     *
     * @param entity entity to be updated.
     * @return entity, that has been added to the system.
     */
    @PostMapping(path = "/update", produces = "application/json")
    public ResponseEntity<TruckDto> updateEntity(
        @RequestBody @Validated(ExistingTruckChecksOrder.class) TruckDto entity) {
        truckInnerService.update(entity);
        return ResponseEntity.status(HttpStatus.OK)
            .body(truckInnerService.find(entity.getId()));
    }

    /**
     * Adds new entity to the database.
     *
     * @param entity entity to be deleted.
     * @return id of the deleted entity.
     */
    @PostMapping(path = "/delete", produces = "application/json")
    public ResponseEntity<String> deleteEntity(@RequestBody @Valid TruckDto entity) {
        truckInnerService.delete(entity.getId());
        return ResponseEntity.status(HttpStatus.OK).body(entity.getId());
    }
}
