package io.pivotal.microservices.wineries;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;

public class WineriesControllerTests extends AbstractWineryControllerTests {

	protected static final Winery THE_WINERY = new Winery(ACCOUNT_1,
			ACCOUNT_1_NAME);

	protected static class TestWineryRepository implements WineryRepository {

		@Override
		public Winery findByName(String accountName) {
			if (accountName.equals(ACCOUNT_1))
				return THE_WINERY;
			else
				return null;
		}

		@Override
		public List<Winery> findByOwnerContainingIgnoreCase(String partialName) {
			List<Winery> wineries = new ArrayList<Winery>();

			if (ACCOUNT_1_NAME.toLowerCase().indexOf(partialName.toLowerCase()) != -1)
				wineries.add(THE_WINERY);

			return wineries;
		}

		@Override
		public int countWineries() {
			return 1;
		}
	}

	protected TestWineryRepository testRepo = new TestWineryRepository();

	@BeforeEach
	public void setup() {
		accountController = new WineriesController(testRepo);
	}
}
