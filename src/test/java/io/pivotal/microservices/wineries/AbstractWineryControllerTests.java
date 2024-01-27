package io.pivotal.microservices.wineries;

import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import io.pivotal.microservices.exceptions.WineryNotFoundException;

// The following are equivalent, we only need to use one.

// 1. Read test properties from a file - neater when there are multiple properties
@TestPropertySource(locations = "classpath:winery-controller-tests.properties")

// 2. Define test properties directly, acceptable here since we only have one.
// @TestPropertySource(properties={"eureka.client.enabled=false"})
public abstract class AbstractWineryControllerTests {

	protected static final String ACCOUNT_1 = "Kuvin";
	protected static final String ACCOUNT_1_NAME = "Djina S";

	@Autowired
	WineriesController accountController;

	@Test
	public void validAccountName() {
		Logger.getGlobal().info("Start validAccountName test");
		Winery winery = accountController.byName(ACCOUNT_1);

		Assertions.assertNotNull(winery);
		Assertions.assertEquals(ACCOUNT_1, winery.getName());
		Assertions.assertEquals(ACCOUNT_1_NAME, winery.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void validAccountOwner() {
		Logger.getGlobal().info("Start validAccount test");
		List<Winery> wineries = accountController.byOwner(ACCOUNT_1_NAME);
		Logger.getGlobal().info("In validAccount test");

		Assertions.assertNotNull(wineries);
		Assertions.assertEquals(1, wineries.size());

		Winery winery = wineries.get(0);
		Assertions.assertEquals(ACCOUNT_1, winery.getName());
		Assertions.assertEquals(ACCOUNT_1_NAME, winery.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void validAccountOwnerMatches1() {
		Logger.getGlobal().info("Start validAccount test");
		List<Winery> wineries = accountController.byOwner("Djina");
		Logger.getGlobal().info("In validAccount test");

		Assertions.assertNotNull(wineries);
		Assertions.assertEquals(1, wineries.size());

		Winery winery = wineries.get(0);
		Assertions.assertEquals(ACCOUNT_1, winery.getName());
		Assertions.assertEquals(ACCOUNT_1_NAME, winery.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void validAccountOwnerMatches2() {
		Logger.getGlobal().info("Start validAccount test");
		List<Winery> wineries = accountController.byOwner("djina");
		Logger.getGlobal().info("In validAccount test");

		Assertions.assertNotNull(wineries);
		Assertions.assertEquals(1, wineries.size());

		Winery winery = wineries.get(0);
		Assertions.assertEquals(ACCOUNT_1, winery.getName());
		Assertions.assertEquals(ACCOUNT_1_NAME, winery.getOwner());
		Logger.getGlobal().info("End validAccount test");
	}

	@Test
	public void invalidAccountName() {
		try {
			accountController.byName("111");
			Assertions.fail("Expected an AccountNotFoundException");
		} catch (WineryNotFoundException e) {
			// Worked!
		}
	}

	@Test
	public void invalidAccountOwner() {
		try {
			accountController.byOwner("Dj Ina");
			Assertions.fail("Expected an AccountNotFoundException");
		} catch (WineryNotFoundException e) {
			// Worked!
		}
	}
}
