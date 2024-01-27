package io.pivotal.microservices.services.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.services.registration.RegistrationServer;

/**
 * Wineries web-server. Works as a microservice client, fetching data from the Winery-Service.
 * Uses the Discovery Server (Eureka) to find the microservice.
 */
@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, //
        DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
public class WebServer {

    public static final String WINERIES_SERVICE_URL = "http://WINERIES-SERVICE";

    /**
     * @param args Program arguments - ignored.
     */
    public static void main(String[] args) {
        if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
            System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

        System.setProperty("spring.config.name", "web-server");
        SpringApplication.run(WebServer.class, args);
    }

    /**
     * A customized RestTemplate that has the ribbon load balancer build in.
     * Note that prior to the "Brixton"
     * 
     * @return
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The WineryService makes the interaction with the micro-service.
     * 
     * @return A new service instance.
     */
    @Bean
    public WebWineriesService wineriesService() {
        return new WebWineriesService(WINERIES_SERVICE_URL);
    }

    /**
     * Create the controller, passing it the {@link WebWineriesService} to use.
     * 
     * @return
     */
    @Bean
    public WebWineriesController wineriesController() {
        return new WebWineriesController(wineriesService());
    }

    @Bean
    public HomeController homeController() {
        return new HomeController();
    }
}
