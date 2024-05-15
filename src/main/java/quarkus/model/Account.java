package quarkus.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

	public Long accountNumber;
	public Long customerNumber;
	public String customerName;
	public BigDecimal balance;
	public AccountStatus accountStatus = AccountStatus.OPEN;

	public Account() {
	}

	public Account(Long accountNumber, Long customerNumber, String customerName, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.customerNumber = customerNumber;
		this.customerName = customerName;
		this.balance = balance;
	}

	public void markOverdrawn() {
		accountStatus = AccountStatus.OVERDRAWN;
	}
	
	public void removeOverdrawnStatus() {
		accountStatus = AccountStatus.OPEN;
	}
	
	public void close() {
		accountStatus = AccountStatus.CLOSED;
		balance = BigDecimal.valueOf(0);
	}
	
	public void windrawFunds(BigDecimal amount) {
		balance =  balance.subtract(amount);
	}
	
	public void addFounds(BigDecimal amount) {
		balance = balance.add(amount);
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public Long getAccountNumber() {
		return accountNumber;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber, accountStatus, balance, customerName, customerNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(accountNumber, other.accountNumber) && accountStatus == other.accountStatus
				&& Objects.equals(balance, other.balance) && Objects.equals(customerName, other.customerName)
				&& Objects.equals(customerNumber, other.customerNumber);
	}
	
	
}
