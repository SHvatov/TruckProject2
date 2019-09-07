package com.ishvatov.controller;

import com.ishvatov.model.dto.UserDto;
import com.ishvatov.service.inner.user.UserInnerService;
import com.ishvatov.validation.user.ExistingUserChecksOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * This controller is designed to process login requests.
 *
 * @author Sergey Khvatov
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/login")
public class LoginController {

    /**
     * User data repository.
     */
    private UserInnerService userInnerService;

    /**
     * Default class field injection constructor.
     *
     * @param userInnerService user service object.
     */
    @Autowired
    public LoginController(UserInnerService userInnerService) {
        this.userInnerService = userInnerService;
    }

    @PostMapping(path = "/authenticate", produces = "application/json")
    public ResponseEntity<UserDto> authenticateUser(
        @RequestBody @Validated(ExistingUserChecksOrder.class) UserDto user) {
        UserDto response = userInnerService.find(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
