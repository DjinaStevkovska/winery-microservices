package io.pivotal.microservices.wineries;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.microservices.exceptions.WineryNotFoundException;

/**
 * A RESTFul controller for accessing winery information.
 */
@RestController
public class WineriesController {

	protected Logger logger = Logger.getLogger(WineriesController.class
			.getName());
	protected WineryRepository wineryRepository;

	/**
	 * Create an instance plugging in the repository of Wineries.
	 * 
	 * @param wineryRepository
	 *            Winery repository implementation.
	 */
	@Autowired
	public WineriesController(WineryRepository wineryRepository) {
		this.wineryRepository = wineryRepository;

		logger.info("WineryRepository says system has "
				+ wineryRepository.countWineries() + " wineries");
	}

	/**
	 * @param wineryName
	 *            3 letters winery name.
	 * @return The winery if found.
	 * @throws WineryNotFoundException
	 *             If the name is not recognised.
	 */
	@RequestMapping("/wineries/{wineryName}")
	public Winery byName(@PathVariable("wineryName") String wineryName) {

		logger.info("wineries-service byName() invoked: " + wineryName);
		Winery winery = wineryRepository.findByName(wineryName);
		logger.info("wineries-service byName() found: " + winery);

		if (winery == null)
			throw new WineryNotFoundException(wineryName);
		else {
			return winery;
		}
	}

	/**
	 * Fetch wineries with the specified owner.
	 * A partial case-insensitive match is supported.
	 * So <code>http://.../wineries/owner/a</code> will find any wineries with upper or lower case 'a' in their owner.
	 * 
	 * @param owner
	 * @return A non-null, non-empty set of wineries.
	 * @throws WineryNotFoundException
	 *             If there are no matches at all.
	 */
	@RequestMapping("/wineries/owner/{owner}")
	public List<Winery> byOwner(@PathVariable("owner") String owner) {
		logger.info("wineries-service byOwner() invoked: "
				+ wineryRepository.getClass().getName() + " for "
				+ owner);

		List<Winery> wineries = wineryRepository.findByOwnerContainingIgnoreCase(owner);
		logger.info("wineries-service byOwner() found: " + wineries);

		if (wineries == null || wineries.isEmpty())
			throw new WineryNotFoundException(owner);
		else {
			return wineries;
		}
	}
}
