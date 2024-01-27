package io.pivotal.microservices.wineries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

/**
 * The wineries Spring configuration.
 */
@Configuration
@ComponentScan
@EntityScan("io.pivotal.microservices.wineries")
@EnableJpaRepositories("io.pivotal.microservices.wineries")
@PropertySource("classpath:db-config.properties")
public class WineriesConfiguration {

	protected Logger logger;

	public WineriesConfiguration() {
		logger = Logger.getLogger(getClass().getName());
	}

	/**
	 * Creates an in-memory "rewards" database populated with test data for fast testing
	 */
	@Bean
	public DataSource dataSource() {
		logger.info("dataSource() invoked");

		// Create an in-memory H2 relational database containing test data.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:database/createTableWinery.sql")
				.addScript("classpath:database/dataTableWinery.sql").build();

		logger.info("dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> wineries = jdbcTemplate.queryForList("SELECT name FROM T_WINERY");
		logger.info("System has " + wineries.size() + " wineries");

		// Populate with random balances
		Random rand = new Random();

		for (Map<String, Object> item : wineries) {
			String name = (String) item.get("name");
			BigDecimal balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, RoundingMode.HALF_UP);
			jdbcTemplate.update("UPDATE T_WINERY SET balance = ? WHERE name = ?", balance, name);
		}

		return dataSource;
	}
}
