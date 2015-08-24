// Defines a customer account
public class Account {
  // Constructor
  public Account(int accountNumber, int balance) {
    this.accountNumber = accountNumber;                                // Set the account number
    this.balance = balance;                                            // Set the initial balance
  }

  // Return the current balance
  public int getBalance() {
    return balance;
  }

  // Set the current balance
  public void setBalance(int balance) {
    this.balance = balance;
  }

  public int getAccountNumber() {
    return accountNumber;
  }

  @Override
  public String toString() {
    return "A/C No. " + accountNumber + " : $" + balance;
  }

  private int balance;                                                 // The current account balance
  private int accountNumber;                                           // Identifies this account
}
