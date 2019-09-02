package com.ishvatov.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.io.IOException;
import java.util.Properties;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ConfigBean {

    /**
     * Creates a {@link ModelMapper} bean, which is used to map entities.
     *
     * @return {@link ModelMapper} instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STRICT)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(PRIVATE);
        return mapper;
    }

    /**
     * Loads yml property file.
     *
     * @return instance of {@link Properties}.
     */
    @Bean
    public Properties yamlProperties() {
        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(new ClassPathResource("validation.yml"));
        return bean.getObject();
    }

    /**
     * Configures JSR303 Validation - setups the path to the properties file with
     * internationalized validation error messages.
     *
     * @return configured {@link ResourceBundleMessageSource} object.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setCommonMessages(yamlProperties());
        return messageSource;
    }

    /**
     * Overrides default validation message source.
     *
     * @return instance of {@link LocalValidatorFactoryBean}.
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
