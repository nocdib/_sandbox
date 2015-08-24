public class Transaction {
  // Constructor
  public Transaction(Account account, TransactionType type, int amount) {
    this.account = account;
    this.type = type;
    this.amount = amount;
  }

  public Account getAccount() {
    return account;
  }

  public TransactionType getTransactionType() {
    return type;
  }

  public int getAmount() {
    return amount;
  }
  @Override
  public String toString() {
    return type + " A//C: " + ": $" + amount;
  }

  private Account account;
  private int amount;
  private TransactionType type;
}
