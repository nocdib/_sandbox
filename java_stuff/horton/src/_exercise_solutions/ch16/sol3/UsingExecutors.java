// Chapter 16 Exercise 3

/*
 Extend the result of the previous exercise to incorporate two supervisors
 for two teams of clerks, where the supervisors each run in their own thread.
 The supervisor threads should receive transactions from transaction sources
 and pass them to the clerks they supervise. The supervisors’ work should result
 in a variable time delay in transferring transaction to the clerks of between
 100 and 500 milliseconds.
 */

/*
  This is not an easy exercise. The addition of the supervisors and the delays
  they introduce in processing transactions adds a lot more potential for things
  going wrong. The exxample now has the transaction sources, the supervisors and the
  clerks all running in separate threads.

  You need to be sure that the clerks continue working when their in tray
  is empty until the supervisor notifies them that there are no more transactions. If
  you manage to get this working satisfactorily you are to be congratulated. Multi-threaded
  programs are extremely difficult to write in general and even more difficult to debug.

  I increased the number of accounts to make the solution more interesting. I made the supervisors
  create their own team of clerks - three in my solution. This way the supervisors manage when
  the clerks start and stop work.
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
      int[] initialBalance = {500, 800, 900, 1100};                                                // The initial account balances
      int transactionCount = 30;                                                                   // Number of debits and of credits
      int supervisorCount = 2;                                                                     // Number of supervisors
      int teamSize = 3;                                                                            // Number of clerks in a supervisor's team
      ExecutorService threadPool = Executors.newCachedThreadPool();                                // The thread pool

      // Create the account, the bank, and the supervisors...
      Bank theBank = new Bank();                                                                   // Create a bank
      Vector<Supervisor> supervisors = new Vector<>();                                             // Stores the supervisors
      Vector<Account> accounts = new Vector<>();                                                   // Stores the accounts

      for(int i = 1 ; i <= supervisorCount ; ++i) {
        supervisors.add(new Supervisor(i, theBank, teamSize));                                     // Create the supervisors
      }

      for(int i = 0 ; i < initialBalance.length; ++i) {
        accounts.add(new Account(i+1, initialBalance[i]));                                         // Create accounts
      }

      // Start the supervisor threads
      for(Supervisor supervisor : supervisors) {
        threadPool.submit(supervisor);
      }

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
        transSources.add(threadPool.submit(new TransactionSource(transactionCount, accounts, supervisors))) ;
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
        threadPool.awaitTermination(60L, TimeUnit.SECONDS);
      } catch(InterruptedException e) {
        System.out.println(e);
      }

      if(threadPool.isTerminated()) {
        System.out.println("\nAll supervisors have completed their tasks.\n");
      } else {
        System.out.println("\nSupervisors still running - shutting down anyway.\n");
        threadPool.shutdownNow();
      }

      // Now output the results
      for(int i = 0 ; i < accounts.size() ; ++i) {
        System.out.println("Account Number: "+accounts.get(i).getAccountNumber()+"\n"+
           "Original balance       : $" + initialBalance[i] + "\n" +
           "Account balance change : $" + accountTotals[i] + "\n" +
           "Final balance          : $" + accounts.get(i).getBalance() + "\n" +
           "Should be              : $" + (initialBalance[i] + accountTotals[i]) + "\n");
    }
  }
}
