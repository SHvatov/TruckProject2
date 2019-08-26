package com.ishvatov.model.mapper;

import com.ishvatov.model.dto.CityDto;
import com.ishvatov.model.entity.CityEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Default implementation of the mapper class, which is used to map driver entity classes
 * to dto classes and back.
 *
 * @author Sergey Khvatov
 */
@Component
public class CityMapper extends AbstractMapper<String, CityEntity, CityDto>
    implements Mapper<String, CityEntity, CityDto> {

    /**
     * Default class constructor, which inits the base abstract class and also autowires
     * mapper field.
     *
     * @param modelMapper mapper instance.
     */
    @Autowired
    public CityMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      entity source.
     * @param destination dto destination.
     */
    @Override
    protected void mapSpecificFields(CityEntity source, CityDto destination) {
        /* empty */
    }

    /**
     * Protected method, that must be implemented in the sub-class.
     * Maps all the specific field from entity object to DTO.
     *
     * @param source      DTO source.
     * @param destination entity destination.
     */
    @Override
    protected void mapSpecificFields(CityDto source, CityEntity destination) {
        /* empty */
    }
}
