import java.util.Vector;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Supervisor implements Runnable {

    public Supervisor(int ID, Bank bank, int teamSize) {
      this.ID = ID;                                                                                // This supervisors ID
      this.theBank = bank;                                                                         // The bank we all work for
      this.threadPool = Executors.newFixedThreadPool(teamSize);                                    // Thread pool for clerks

      // Create the team of clerks
      for(int i = 1 ; i <= teamSize ; ++i) {
        clerkTeam.add(new Clerk(i, ID, theBank));
      }
    }

  // Receive a transaction
  synchronized public boolean doTransaction(Transaction transaction) {
    if(!atWork) {                                                                                  // If supervisor has not started...
      return false;                                                                                // ... can't accept the transaction.
    }
    if(inTray.size() >= maxTransactions)
      return false;
    inTray.add(transaction);                                                                       // Add transaction to the list
    return true;
  }

  // The working supervisor...
   public void run() {
    // Create and start the clerk threads
    for(Clerk clerk : clerkTeam) {
      threadPool.submit(clerk);
    }
    atWork = true;                                                                                 // We are ready to receive transactions
    boolean done = false;
    while(true) {
      while(inTray.size() == 0) {                                                                  // No transaction waiting?
        try {
          Thread.sleep(200);                                                                       // then take a break
          if(inTray.size() != 0) {                                                                 // If a transaction has been posted
            break;                                                                                 // ...continue operations
          } else {                                                                                 // ...otherwise...
            shutdownClerks();                                                                      // ...tell the clerks to go home...
            return;                                                                                // ...we are done for the day!
          }
        } catch(InterruptedException e) {
          // Interrupted while idle, so shut down the clerks and return...
          shutdownClerks();
          return;
        }
      }
      Transaction transaction = inTray.remove(0);
      try {                                                                                        // Processing the transction...
        System.out.println(this + " working...");
        Thread.sleep(100*(1 + rand.nextInt(5)));                                                   // ...takes a while (100 to 500msec)
      } catch(InterruptedException e) {
        System.out.println(this + " interrupted while processing transaction. Continuing...");
      }

      // Allocate the transaction to a clerk...
      done = false;
      while(true) {
        // Find a clerk to do the transaction
        for(Clerk clerk : clerkTeam) {
          if(done = clerk.doTransaction(transaction))
            break;
        }
        if(done) {
          break;
        }

        // No clerk was free so wait a while
        try {
          Thread.sleep(100);
        } catch(InterruptedException e) {
          System.out.println(this + " interrupted waiting for a free clerk. Continuing...\n");
        }
      }
    }
  }

  private void shutdownClerks() {
    // Tell the clerks we are done for the day...
    for(Clerk clerk : clerkTeam) {
      clerk.end();
    }
    threadPool.shutdown();
    try {
      threadPool.awaitTermination(10L, TimeUnit.SECONDS);
    } catch(InterruptedException e) {
      System.out.println(e);
    }

    if(threadPool.isTerminated()) {
      System.out.println("\nAll clerks for " + this + " have completed their tasks.\n");
    } else {
      System.out.println("\nClerks still running - shutting down anyway.\n");
      threadPool.shutdownNow();
    }
  }

  @Override
  public String toString() {
    return "Supervisor " + ID;
  }

  private boolean atWork = false;                                                                  // Not available flag - until we start work
  private int ID;                                                                                  // This supervisor's ID
  private Bank theBank;                                                                            // The bank
  ExecutorService threadPool;                                                                      // The pool of threads
  private Vector<Clerk> clerkTeam = new Vector<>();                                                // The team of clerks for this supervisor
  private List<Transaction> inTray = Collections.synchronizedList(new LinkedList<Transaction>());  // The in-tray holding transactions
  private int maxTransactions = 10;                                                                // Maximum transactions in the in-tray
  private Random rand = new Random();                                                              // Random number generator
}