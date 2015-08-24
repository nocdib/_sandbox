// Chapter 16 Exercise 1

/*
 Modify the UsingExecutors example in the chapter
 so that each transaction is a debit or a credit at random.
 */

/*
  A TransactionSource object now generates a debit or a credit transaction at random.
  The constructor no longer needs a transaction type to be specified.
  The TransactionSource objects now accumulate the total of debits and credits
  originated for each account so these are not reported separately.
  If you wanted to retain information about credits and debits separately,
  you could return an array of type int[][] from the call() method.
 */

import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class UsingExecutors {

    public static void main(String[] args) {
      int[] initialBalance = {500, 800};                               // The initial account balances
      int[] totals1 = new int[initialBalance.length];                  // Two different cr totals
      int[] totals2 = new int[initialBalance.length];                  // Two different db totals
      int transactionCount = 20;                                       // Number of debits and of credits
      int clerkCount = 2;

      // Create the account, the bank, and the clerks...
      Bank theBank = new Bank();                                       // Create a bank
      Vector<Clerk> clerks = new Vector<>();                           // Stores the clerk
      Vector<Account> accounts = new Vector<>();                       // Stores the accounts

      for(int i = 0 ; i < clerkCount ; ++i) {
        clerks.add(new Clerk(i+1, theBank));                           // Create the clerks
      }

      for(int i = 0 ; i < initialBalance.length; ++i) {
        accounts.add(new Account(i+1, initialBalance[i]));             // Create accounts
        totals1[i] = totals2[i] = 0;
      }

      ExecutorService threadPool = Executors.newCachedThreadPool();

      // Create and start the transaction source threads
      Future<int[]> TransSource1 = threadPool.submit(new TransactionSource(transactionCount, accounts, clerks));
      Future<int[]> TransSource2 = threadPool.submit(new TransactionSource(transactionCount, accounts, clerks));

      // Create and start the clerk threads
      for(Clerk clerk : clerks) {
        threadPool.submit(clerk);
      }
      try {
        totals1 = TransSource1.get();
        totals2 = TransSource2.get();
      } catch(ExecutionException e) {
        System.out.println(e.getCause());
      } catch(InterruptedException e) {
        System.out.println(e);
      }
      int[] totals = new int[totals1.length];
      for(int i = 0 ; i < totals.length ; ++i) {
        totals[i] = totals1[i] + totals2[i];
      }

      // Orderly shutdown when all threads have ended
      threadPool.shutdown();
      try {
        threadPool.awaitTermination(10L, TimeUnit.SECONDS);
      } catch(InterruptedException e) {
        System.out.println(e);
      }

      if(threadPool.isTerminated()) {
        System.out.println("\nAll clerks have completed their tasks.\n");
      } else {
        System.out.println("\nClerks still running - shutting down anyway.\n");
        threadPool.shutdownNow();
      }

      // Now output the results
      for(int i = 0 ; i < accounts.size() ; ++i) {
        System.out.println("Account Number:"+accounts.get(i).getAccountNumber()+"\n"+
           "Original balance       : $" + initialBalance[i] + "\n" +
           "Account balance change : $" + totals[i] + "\n" +
           "Final balance          : $" + accounts.get(i).getBalance() + "\n" +
           "Should be              : $" + (initialBalance[i] + totals[i]) + "\n");
    }
  }
}
