package com.abac.crew.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

/**
 * Starter of Crew Service
 *
 * @author sergiu-daniel.cionca
 */
@SpringBootApplication(scanBasePackages = "com.abac.crew")
@EnableReactiveCassandraRepositories("com.abac.crew.bm.repository")
public class CrewApplicationStarter {

    /**
     * Starts the application.
     *
     * @param args the arguments given at command line
     */
    public static void main(final String[] args) {
        SpringApplication.run(CrewApplicationStarter.class, args);
    }

}
