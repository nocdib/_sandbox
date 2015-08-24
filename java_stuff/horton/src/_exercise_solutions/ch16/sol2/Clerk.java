import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

public class Clerk implements Runnable {
  // Constructor
  public Clerk(int ID, Bank theBank) {
    this.ID = ID;
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
        try {
          Thread.sleep(200);                                           // then take a break
          if(inTray.size() != 0) {
            break;
          } else {
            return;
          }
        } catch(InterruptedException e) {
          System.out.println("Clerk "+ ID + "\n" + e);
          return;
        }
      }
      theBank.doTransaction(inTray.remove(0));
      if(Thread.interrupted()) {
        System.out.println("Interrupt flag for Clerk " + ID + " set. Terminating.");
        return;
      }
    }
  }

  int ID;
  private Bank theBank;
  private List<Transaction> inTray =                                   // The in-tray holding transactions
                   Collections.synchronizedList(new LinkedList<Transaction>());
  private int maxTransactions = 8;                                     // Maximum transactions in the in-tray
}
