package io.pivotal.microservices.wineries;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Persistent winery entity with JPA markup.
 * Wineries are stored in an H2 relational database.
 */
@Entity
@Table(name = "T_WINERY")
public class Winery implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Long nextId = 0L;

	@Id
	protected Long id;

	protected String name;

//	@Column(name = "owner")
	protected String owner;

	protected BigDecimal balance;

	/**
	 * Non-scalable solution to generate unique ids.
	 * Not recommended for production.
	 * Consider using <tt>@GeneratedValue(strategy=SEQUENCE)</tt> to generate ids.

	 * @return The next available id.
	 */
	protected static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}

	/**
	 * Default constructor for JPA only.
	 */
	protected Winery() {
		balance = BigDecimal.ZERO;
	}

	public Winery(String name, String owner) {
		id = getNextId();
		this.name = name;
		this.owner = owner;
		balance = BigDecimal.ZERO;
	}

	public long getId() {
		return id;
	}

	/**
	 * Set JPA id - for testing and JPA only.
	 * Not intended for production use.
	 *
	 * @param id
	 *            New winery id.
	 */
	protected void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	protected void setName(String accountName) {
		this.name = accountName;
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

	public void withdraw(BigDecimal amount) {
		balance.subtract(amount);
	}

	public void deposit(BigDecimal amount) {
		balance.add(amount);
	}

	@Override
	public String toString() {
		return name + " [" + owner + "]: $" + balance;
	}

}
