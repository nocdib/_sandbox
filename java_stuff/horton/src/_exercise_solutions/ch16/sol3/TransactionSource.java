// Generates transactions for supervisors
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Callable;

public class TransactionSource implements Callable<int[]> {

  public TransactionSource(int maxTrans, Vector<Account> accounts, Vector<Supervisor> supervisors) {
    this.maxTrans = maxTrans;
    this.accounts = accounts;
    this.supervisors = supervisors;
    totals = new int[accounts.size()];
  }

  // The source of transactions
  public int[] call() {
    // Create transactions randomly distributed between the accounts
    Random rand = new Random();
    Transaction transaction = null;                                                                // Stores a transaction
    int amount = 0;                                                                                // Stores an amount of money
    int select = 0;                                                                                // Selects an account
    boolean done = false;
    TransactionType type = null;
    for(int i = 1 ; i <= maxTrans ; ++i) {
      // Generate a random account index for  operation
      select = rand.nextInt(accounts.size());
      amount = 50 + rand.nextInt(26);                                                              // Generate amount of $50 to $75

      type = rand. nextBoolean() ? TransactionType.CREDIT : TransactionType.DEBIT;                 // Generate random transaction type

      transaction = new Transaction(accounts.get(select),                                          // Account
                                    type,                                                          // Transaction type
                                    amount);                                                       //  of amount
      totals[select] += type == TransactionType.CREDIT ? amount : -amount;                         // Track total of credits & debits
      done = false;
      while(true) {
        // Find a supervisor to do the transaction
        for(Supervisor supervisor : supervisors) {
          if(done = supervisor.doTransaction(transaction))
            break;
        }
        if(done) {
          break;
        }

        // No supervisor was free so wait a while
        try {
          Thread.sleep(10);
        } catch(InterruptedException e) {
          System.out.println(" TransactionSource\n" + e);
        }
      }
      if(Thread.interrupted()) {
        System.out.println("Interrupt flag for "+ type + " transaction source set. Terminating.");
        return totals;
      }
    }
    return totals;
  }

  private int maxTrans;
  private Vector<Account> accounts;
  private Vector<Supervisor> supervisors;
  private int[] totals;
}