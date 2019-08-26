package com.ishvatov.model.mapper;

import com.ishvatov.model.dto.UserDto;
import com.ishvatov.model.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * Default implementation of the mapper class, which is used to map user entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class UserMapper extends AbstractMapper<String, UserEntity, UserDto>
    implements Mapper<String, UserEntity, UserDto> {

    /**
     * Coder instance.
     */
    private BCryptPasswordEncoder encoder;

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param modelMapper mapper instance.
     */
    @Autowired
    public UserMapper(ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        super(modelMapper);
        this.encoder = encoder;
    }

    /**
     * Sets up the mapper instance and adds new type map.
     * Prevents mapper from setting user's password.
     */
    @PostConstruct
    public void setupMapper() {
        this.mapper.createTypeMap(this.entityClass, this.dtoClass)
            .addMappings(m -> m.skip(UserDto::setPassword))
            .setPostConverter(converToDto());
        this.mapper.createTypeMap(this.dtoClass, this.entityClass)
            .addMappings(m -> m.skip(UserEntity::setPassword))
            .setPostConverter(convertToEntity());
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      entity source.
     * @param destination dto destination.
     */
    @Override
    protected void mapSpecificFields(UserEntity source, UserDto destination) {
        destination.setPassword(null);
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    @Override
    protected void mapSpecificFields(UserDto source, UserEntity destination) {
        destination.setPassword(encoder.encode(source.getPassword()));
    }
}
