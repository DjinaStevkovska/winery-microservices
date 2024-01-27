package io.pivotal.microservices.wineries;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 * Repository for Winery data implemented using Spring Data JPA.
 */
public interface WineryRepository extends Repository<Winery, Long> {
	/**
	 * Find a Winery with the specified winery name.
	 *
	 * @return winery ?? null
	 */
	public Winery findByName(String wineryName);

	/**
	 * Find wineries whose owner name contains the specified string
	 * A partial case-insensitive match is supported.
	 * Will return any wineries with upper or lower case 'a' in their owner.
	 *
	 * @param owner
	 *            Any alphabetic string.
	 * @return The list of matching wineries - always non-null, but may be empty.
	 */
	public List<Winery> findByOwnerContainingIgnoreCase(String owner);

	/**
	 * Fetch the name of wineries known to the system.
	 * 
	 * @return The name of wineries.
	 */
	@Query("SELECT count(*) from Winery")
	public int countWineries();
}
