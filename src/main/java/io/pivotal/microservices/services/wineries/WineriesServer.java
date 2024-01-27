package io.pivotal.microservices.services.wineries;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import io.pivotal.microservices.wineries.WineryRepository;
import io.pivotal.microservices.wineries.WineriesConfiguration;
import io.pivotal.microservices.services.registration.RegistrationServer;

/**
 * Run as a micro-service, registering with the Discovery Server (Eureka).
 * <p>
 * Config for this application is imported from {@link WineriesConfiguration}. 
 * <p>
 * This class declares no beans and current package contains no components for ComponentScan to find.
 */
@SpringBootApplication
@EnableDiscoveryClient
@Import(WineriesConfiguration.class)
public class WineriesServer {

    @Autowired
    protected WineryRepository wineryRepository;

    protected Logger logger = Logger.getLogger(WineriesServer.class.getName());

    /**
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        System.setProperty("spring.config.name", "wineries-server");

        SpringApplication.run(WineriesServer.class, args);
    }
}
