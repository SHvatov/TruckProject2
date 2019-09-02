package com.ishvatov.service.inner.user;

import com.ishvatov.exception.DataBaseException;
import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.entity.UserEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.UserRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Basic {@link UserInnerService} interface implementation.
 *
 * @author Sergey Khvatov
 */
@Service
@Transactional
public class UserInnerServiceImpl
    extends AbstractInnerService<String, UserDto, UserEntity, UserRepository>
    implements UserInnerService {

    /**
     * Autowired encrypting service.
     */
    private BCryptPasswordEncoder encoder;

    /**
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public UserInnerServiceImpl(UserRepository repository,
                                Mapper<UserEntity, UserDto> mapper,
                                BCryptPasswordEncoder encoder) {
        super(repository, mapper);
        this.encoder = encoder;
    }

    /**
     * Checks if user with such credentials exists in the system.
     *
     * @param id       user's name.
     * @param password user's password.
     * @return true, if user with such id and password exists in the DB,
     * false otherwise.
     * @throws IllegalArgumentException if required fields are null.
     */
    @Override
    public boolean isValidUserCredentials(String id, String password) {
        Optional<UserEntity> userEntity = repository.findById(id);
        return userEntity.map(entity -> encoder.matches(password, entity.getPassword()))
            .orElse(false);
    }

    /**
     * Implementation of the save method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void saveImpl(UserDto dto) {
        if (exists(dto.getId())) {
            throw new DataBaseException("Entity with such UID already exists");
        } else {
            UserEntity entity = new UserEntity();
            mapper.map(dto, entity);
            repository.save(entity);
        }
    }

    /**
     * Implementation of the update method, that must be
     * implemented in the child class.
     *
     * @param dto DTO object.
     */
    @Override
    protected void updateImpl(UserDto dto) {
        UserEntity userEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DataBaseException("No entity with id: [" + dto.getId() + "] exists"));
        mapper.map(dto, userEntity);
        repository.save(userEntity);
    }

    /**
     * Implementation of the delete method, that must be
     * implemented in the child class.
     *
     * @param key unique id of the object.
     */
    @Override
    protected void deleteImpl(String key) {
        repository.findById(key).ifPresent(repository::delete);
    }
}
