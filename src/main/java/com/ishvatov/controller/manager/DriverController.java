package com.ishvatov.controller.manager;

import com.ishvatov.model.dto.DriverDto;
import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.enums.UserRoleType;
import com.ishvatov.service.inner.driver.DriverInnerService;
import com.ishvatov.service.inner.user.UserInnerService;
import com.ishvatov.validation.driver.ExistingDriverChecksOrder;
import com.ishvatov.validation.driver.NotExistingDriverChecksOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This controller is designed to process truck associated requests.
 *
 * @author Sergey Khvatov
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/manager/drivers")
public class DriverController {

    /**
     * {@link DriverInnerService} autowired instance.
     */
    private DriverInnerService driverInnerService;

    /**
     * {@link DriverInnerService} autowired instance.
     */
    private UserInnerService userInnerService;

    /**
     * Default class constructor, used to autowire fields instances.
     *
     * @param driverInnerService {@link DriverInnerService} implementation instance.
     * @param userInnerService   {@link UserInnerService} implementation instance.
     */
    @Autowired
    public DriverController(DriverInnerService driverInnerService, UserInnerService userInnerService) {
        this.driverInnerService = driverInnerService;
        this.userInnerService = userInnerService;
    }

    /**
     * Gets all the truck entities from the database.
     *
     * @return list of {@link DriverDto} object.
     */
    @GetMapping(path = "/list", produces = "application/json")
    public ResponseEntity<List<DriverDto>> listDriverEntities() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(driverInnerService.findAll());
    }

    /**
     * Adds new entity to the database.
     *
     * @param entity entity to be added.
     * @return entity, that has been added to the system.
     */
    @PostMapping(path = "/add", produces = "application/json")
    public ResponseEntity<DriverDto> addEntity(
        @RequestBody @Validated(NotExistingDriverChecksOrder.class) DriverDto entity) {
        driverInnerService.save(entity);
        userInnerService.save(new UserDto(entity.getId(), entity.getPassword(), UserRoleType.ROLE_DRIVER));
        return ResponseEntity.status(HttpStatus.OK)
            .body(driverInnerService.find(entity.getId()));
    }

    /**
     * Updates existing entity in the database.
     *
     * @param entity entity to be updated.
     * @return entity, that has been added to the system.
     */
    @PostMapping(path = "/update", produces = "application/json")
    public ResponseEntity<DriverDto> updateEntity(
        @RequestBody @Validated(ExistingDriverChecksOrder.class) DriverDto entity) {
        driverInnerService.update(entity);
        userInnerService.update(new UserDto(entity.getId(), entity.getPassword(), UserRoleType.ROLE_DRIVER));
        return ResponseEntity.status(HttpStatus.OK)
            .body(driverInnerService.find(entity.getId()));
    }

    /**
     * Adds new entity to the database.
     *
     * @param entity entity to be deleted.
     * @return id of the deleted entity.
     */
    @PostMapping(path = "/delete", produces = "application/json")
    public ResponseEntity<String> deleteEntity(@RequestBody @Valid DriverDto entity) {
        driverInnerService.delete(entity.getId());
        userInnerService.delete(entity.getId());
        return ResponseEntity.status(HttpStatus.OK).body(entity.getId());
    }
}
