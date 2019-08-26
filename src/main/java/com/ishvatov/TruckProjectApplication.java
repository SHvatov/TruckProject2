package com.ishvatov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main class of the project, which is used to configure and deploy the
 * project to the server.
 *
 * @author Sergey Khvatov
 */
@SpringBootApplication
public class TruckProjectApplication extends SpringBootServletInitializer {

    /**
     * Configuration method.
     *
     * @param application application context.
     * @return configured application context.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TruckProjectApplication.class);
    }

    /**
     * Entry point of the program.
     *
     * @param args array of arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(TruckProjectApplication.class, args);
    }

}
