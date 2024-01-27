package io.pivotal.microservices.services.web;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Client controller, fetches Winery info from the microservice via
 * {@link WebWineriesService}.
 */
@Controller
public class WebWineriesController {

    @Autowired
    protected WebWineriesService wineriesService;

    protected Logger logger = Logger.getLogger(WebWineriesController.class.getName());

    public WebWineriesController(WebWineriesService wineriesService) {
        this.wineriesService = wineriesService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("wineryName", "wineryOwner");
    }

    @RequestMapping("/wineries")
    public String goHome() {
        return "index";
    }

    @RequestMapping("/wineries/{wineryName}")
    public String byName(Model model, @PathVariable("wineryName") String wineryName) {

        logger.info("web-service byName() invoked: " + wineryName);

        Winery winery = wineriesService.findByName(wineryName);

        if (winery == null) { // no such winery
            model.addAttribute("name", wineryName);
            return "winery";
        }

        logger.info("web-service byName() found: " + winery);
        model.addAttribute("winery", winery);
        return "winery";
    }

    @RequestMapping("/wineries/owner/{wineryOwner}")
    public String ownerSearch(Model model, @PathVariable("wineryOwner") String name) {
        logger.info("web-service byOwner() invoked: " + name);

        List<Winery> wineries = wineriesService.byOwnerContains(name);
        logger.info("web-service byOwner() found: " + wineries);
        model.addAttribute("search", name);
        if (wineries != null)
            model.addAttribute("wineries", wineries);
        return "wineries";
    }

    @RequestMapping(value = "/wineries/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "winerySearch";
    }

    @RequestMapping(value = "/wineries/doSearch")
    public String doSearch(Model model, SearchCriteria criteria, BindingResult result) {
        logger.info("web-service search() invoked: " + criteria);

        criteria.validate(result);

        if (result.hasErrors())
            return "winerySearch";

        String wineryName = criteria.getWineryName();
        if (StringUtils.hasText(wineryName)) {
            return byName(model, wineryName);
        } else {
            String wineryOwner = criteria.getWineryOwner();
            return ownerSearch(model, wineryOwner);
        }
    }
}
