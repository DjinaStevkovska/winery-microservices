package io.pivotal.microservices.services.web;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.exceptions.WineryNotFoundException;

/**
 * Hide the access to the microservice inside this local service.
 */
@Service
public class WebWineriesService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebWineriesService.class.getName());

    public WebWineriesService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
    }

    public WineryDTO findByName(String wineryName) {

        logger.info("findByName() invoked: for " + wineryName);
        try {
            return restTemplate.getForObject(serviceUrl + "/wineries/{name}", WineryDTO.class, wineryName);
        } catch (Exception e) {
            logger.severe(e.getClass() + ": " + e.getLocalizedMessage());
            return null;
        }

    }

    public List<WineryDTO> byOwnerContains(String name) {
        logger.info("byOwnerContains() invoked:  for " + name);
        WineryDTO[] wineries = null;

        try {
            wineries = restTemplate.getForObject(serviceUrl + "/wineries/owner/{name}", WineryDTO[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (wineries == null || wineries.length == 0)
            return null;
        else
            return Arrays.asList(wineries);
    }

    public WineryDTO getByName(String wineryName) {
        WineryDTO wineryDTO = restTemplate.getForObject(serviceUrl + "/wineries/{name}", WineryDTO.class, wineryName);

        if (wineryDTO == null)
            throw new WineryNotFoundException(wineryName);
        else
            return wineryDTO;
    }
}
