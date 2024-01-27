package io.pivotal.microservices.services.web;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Winery DTO - used to interact with the {@link WebWineriesService}.
 */
@JsonRootName("Winery")
public class WineryDTO {

	protected Long id;
	protected String name;
	protected String owner;
	protected BigDecimal balance;

	/**
	 * Default constructor for JPA only.
	 */
	protected WineryDTO() {
		balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only. Not intended for normal use.
	 * 
	 * @param id
	 *            The new id.
	 */
	protected void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	protected void setName(String wineryName) {
		this.name = wineryName;
	}

	public String getOwner() {
		return owner;
	}

	protected void setOwner(String owner) {
		this.owner = owner;
	}

	public BigDecimal getBalance() {
		return balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	protected void setBalance(BigDecimal value) {
		balance = value;
		balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public String toString() {
		return name + " [" + owner + "]: $" + balance;
	}

}
