package com.ishvatov.service.inner.user;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.enums.UserRoleType;
import com.ishvatov.service.inner.BaseInnerService;

/**
 * Defines a basic interface to interact with
 * user DAO layer.
 *
 * @author Sergey Khvatov
 */
public interface UserInnerService extends BaseInnerService<String, UserDto> {

    /**
     * Checks if user with such credentials exists in the system.
     *
     * @param id user's name.
     * @param password user's password.
     * @return true, if user with such id and password exists in the DB,
     * false otherwise.
     * @throws IllegalArgumentException if required fields are null.
     */
    boolean isValidUserCredentials(String id, String password);
}
