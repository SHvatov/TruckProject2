package com.ishvatov.service.inner.user;

import com.ishvatov.exception.DaoException;
import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.entity.UserEntity;
import com.ishvatov.model.mapper.Mapper;
import com.ishvatov.model.repository.UserRepository;
import com.ishvatov.service.inner.AbstractInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
     * Default class constructor.
     *
     * @param repository repository instance.
     * @param mapper     mapper instance.
     */
    @Autowired
    public UserInnerServiceImpl(UserRepository repository, Mapper<String, UserEntity, UserDto> mapper) {
        super(repository, mapper);
    }

    /**
     * Adds entity to the DB. Check if entity already exists.
     *
     * @param dto new entity to add.
     * @throws DaoException if entity with this UID already exists
     */
    @Override
    public void save(UserDto dto) {
        if (exists(dto.getId())) {
            throw new DaoException(getClass(), "save", "Entity with such UID already exists");
        } else {
            UserEntity entity = new UserEntity();
            mapper.map(dto, entity);
            repository.save(entity);
        }
    }

    /**
     * Updates data in the database. If fields in teh DTO
     * are not null, then update them. If are null, then
     * if corresponding filed in the Entity is nullable,
     * then set it to null and remove all connections,
     * otherwise throw NPE.
     *
     * @param dto values to update in the entity.
     * @throws DaoException if entity with this UID already exists
     */
    @Override
    public void update(UserDto dto) {
        UserEntity userEntity = repository.findById(dto.getId())
            .orElseThrow(() -> new DaoException(getClass(), "update"));
        mapper.map(dto, userEntity);
        repository.save(userEntity);
    }

    /**
     * Deletes entity from the DB if it exists.
     *
     * @param key UID of the entity.
     */
    @Override
    public void delete(String key) {
        repository.findById(key).ifPresent(repository::delete);
    }
}
