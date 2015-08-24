// Chapter 16 Exercise 2

/*
 Modify the result of the previous exercise to allow
 an arbitrary number of transaction source objects to be in effect.
 */

/*
 There is now a Vector of between 2 and 6 TransactionSource objects.
 Each of these will return an array of net transactions for the accounts
 and these are stored in an array of arrays, totals.
 Once operations are complete, the total of transactions from all sources
 for each account are accumulated in the accountTotals array.

 It's all straightforward if you can keep all the arrays clear in your mind.
 */

import java.util.Vector;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class UsingExecutors {

    public static void main(String[] args) {
      int[] initialBalance = {500, 800};                                                        // The initial account balances
      int transactionCount = 20;                                                                // Number of debits and of credits
      int clerkCount = 2;                                                                       // Number of clerks

      // Create the account, the bank, and the clerks...
      Bank theBank = new Bank();                                                                 // Create a bank
      Vector<Clerk> clerks = new Vector<>();                                                     // Stores the clerk
      Vector<Account> accounts = new Vector<>();                                                 // Stores the accounts

      for(int i = 0 ; i < clerkCount ; ++i) {
        clerks.add(new Clerk(i+1, theBank));                                                     // Create the clerks
      }

      for(int i = 0 ; i < initialBalance.length; ++i) {
        accounts.add(new Account(i+1, initialBalance[i]));                                        // Create accounts
      }

      ExecutorService threadPool = Executors.newCachedThreadPool();

      // Create and start between 2 and 6 transaction source threads
      Random rand = new Random();
      int sourceCount = 1 + rand.nextInt(5);
      System.out.format("%nThere are %d transaction sources, each generating %d transactions.%n", sourceCount, transactionCount);
      Vector<Future<int[]>> transSources = new Vector<>();                                         // A vector of transaction sources

      // Create array of arrays to hold totals.
      // The first dimension identifies  the transaction source and the second the account
      int[][] totals = new int[sourceCount][initialBalance.length];

      // Initialize the totals array to zero
      for(int i = 0 ; i < sourceCount ; ++i) {
        Arrays.fill(totals[i], 0);
      }

      for(int i = 0 ; i < sourceCount  ; ++i) {
        transSources.add(threadPool.submit(new TransactionSource(transactionCount, accounts, clerks))) ;
      }

      // Create and start the clerk threads
      for(Clerk clerk : clerks) {
        threadPool.submit(clerk);
      }
      try {
        for(int i = 0 ; i < sourceCount ; ++i) {
          totals[i] = transSources.get(i).get();
        }
      } catch(ExecutionException e) {
        System.out.println(e.getCause());
      } catch(InterruptedException e) {
        System.out.println(e);
      }

      // Accumulate the transactions totals for the accounts
      int[] accountTotals = new int[initialBalance.length];
      for(int i = 0 ; i < initialBalance.length ; ++i) {
        accountTotals[i] = 0;
        for(int j = 0 ; j < sourceCount ; ++j) {
        accountTotals[i] += totals[j][i];
        }
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
           "Account balance change : $" + accountTotals[i] + "\n" +
           "Final balance          : $" + accounts.get(i).getBalance() + "\n" +
           "Should be              : $" + (initialBalance[i] + accountTotals[i]) + "\n");
    }
  }
}
