import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class Clerk implements Runnable {
  // Constructor
  public Clerk(int ID, int superID, Bank theBank) {
    this.ID = ID;
    this.superID = superID;                                            // Remember the supervisor's ID
    this.theBank = theBank;                                            // Who the clerk works for
  }

  // Receive a transaction
  synchronized public boolean doTransaction(Transaction transaction) {
    if(inTray.size() >= maxTransactions)
      return false;
    inTray.add(transaction);                                           // Add transaction to the list
    return true;
  }

  // The working clerk...
   public void run() {
    while(true) {
      while(inTray.size() == 0) {                                      // No transaction waiting?
        if(noMoreWork) {                                               // No more transactions coming...
          return;                                                      // so we are finished for the day!
        }
        try {
          Thread.sleep(1000);                                          // then take a break
          if(inTray.size() != 0) {
            break;
          }
        } catch(InterruptedException e) {
          System.out.println("Clerk "+ ID + "\n" + e);
          return;
        }
      }
      theBank.doTransaction(inTray.remove(0));
      if(Thread.interrupted()) {
        System.out.println("Interrupt flag for " + this + " set. Terminating.");
        return;
      }
    }
  }

  // This method is called by the supervisor to tell the clerk
  // that there are no more transactions
  public void end() {
    noMoreWork = true;
  }

  @Override
  public String toString() {
    return "Clerk " + superID + "_" + ID;                                                          // Unique identifier for the clerk
  }

  int ID;                                                                                          // This clerk's ID
  int superID;                                                                                     // The supervisor's ID
  private Bank theBank;                                                                            // The bank
  private List<Transaction> inTray = Collections.synchronizedList(new LinkedList<Transaction>());  // The in-tray holding transactions
  private int maxTransactions = 4;                                                                 // Maximum transactions in the in-tray
  private boolean noMoreWork = false;                                                              // Indicator for when no more work is due
}
