// Chapter 15 Exercise 2
/*
 For the adventurous gambler—use a stack and a Random object in a program
 to simulate a game of Blackjack for one player using two decks of cards.
 */

// Operates a blackjack game - no splits
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Blackjack {
  public static void main(String[] args) {
    System.out.println("Dealer stands on 17. Blackjack pays double.\nLet's play Blackjack!\n");

    while(true)
    {
      if(deck.markerCardFound())
      {
        System.out.println("New deck!");
        deck = new BlackjackDeck();
      }
      stake = getStake();

      playerHand = deck.dealBlackjackHand();                                       // Deal the player's hand
      dealerHand = deck.dealBlackjackHand();                                       // Deal the dealer's hand
      System.out.println("You were dealt  : " + playerHand);
      System.out.println("Dealer was dealt: " + dealerHand);

      if(playerPlay()) {
        dealerPlay();                                                              // Player has a good hand so dealer must play
      }

      System.out.print("\n\nAnother game(Y or N)? :");
      if(!yes()) {
        break;
      }
    }

    // End of game
    System.out.println("\nYou " + (playerCash < 0 ? "lost" : "won") + " a total of $" + Math.abs(playerCash));
  }

  // Play for the player. Returns false if the player hand is busted
  // and true otherwise
  private static boolean playerPlay() {
    System.out.println("\nYour hand is    : " + playerHand);
    if(playerHand.isBlackjack()) {
      System.out.println("You have blackjack!");
      return true;
    }

    while(true) {
      System.out.print("Another card(Y or N)? : ");
      if(yes()) {
        System.out.println("Your hand is now: " + deck.dealCard(playerHand));

        if(playerHand.isBust()) {
          System.out.println("Bust! You lose!");
          playerCash -= stake;
          return false;
        }
      } else {
        return true;
      }
    }
  }

  // Play for the dealer
  private static void dealerPlay() {
    // Check for dealer blackjack
    // If so, the player gets the stake back only if they have blackjack
    System.out.println("\nDealer hand is  : " + dealerHand);
    if(dealerHand.isBlackjack()) {
      if(playerHand.isBlackjack()) {
        System.out.println("Dealer has blackjack! It's a push!");
      } else {
        System.out.println("Dealer has blackjack! You lose!");
        playerCash -= stake;
      }
      return;
    }

    // No dealer blackjack so check for player blackjack
    if(playerHand.isBlackjack()) {
      System.out.println("You win! Twice the stake for blackjack!");
      playerCash += 2*stake;
      return;
    }

    int playerHandValue = playerHand.value();
    System.out.println("Dealer: "+dealerHand.value() + " Player: " + playerHandValue);             //********

    // No dealer or player blackjack. Dealer must draw cards until hand is 17 or above.
    while(dealerHand.value() < 17) {
      if(deck.dealCard(dealerHand).isBust()) {
        System.out.println("Dealer has: " + dealerHand + "\n Busted! You win!");
        playerCash += stake;
        return;
      }
      System.out.println("Dealer hand is: " + dealerHand);
      System.out.println("Dealer: " + dealerHand.value() + " Player: "+playerHandValue);           //********
    }

    // Dealer has not busted so figure out who won
    int dealerHandValue = dealerHand.value();                                                      // Get the last dealer hand value
    if(dealerHandValue == playerHandValue) {
    System.out.println("Dealer: " + dealerHand.value() + " Player: " + playerHandValue);           //********
      System.out.println("\nIt's a push. You get your stake back.");
    }
    else if(dealerHandValue > playerHandValue) {
    System.out.println("Dealer: " + dealerHand.value() + " Player: " + playerHandValue);           //********
      System.out.println("\nYou lose!");
      playerCash -= stake;
    } else {
      System.out.println("\nYou win!");
      playerCash += stake;
    }
    return;
  }

  private static int getStake() {
    int stake = 0;
    while(true) {
      System.out.print("    Place your bet($1 minimum - whole dollars only) : $");
      try {
        stake = Integer.parseInt(in.readLine().trim());
        return stake;
      }catch(NumberFormatException e) {
        System.out.println("Invalid input. Try again.");
      } catch(IOException e){
          System.out.println("Error reading for the keyboard." + e.getMessage());
      }
    }
  }

  private static boolean yes() {
    String str = null;
    while(true) {
      try {
        str = in.readLine().trim();
      } catch(IOException e) {
          System.out.println("Error reading for the keyboard." + e.getMessage());
      }

      if(str.equalsIgnoreCase("Y"))
        return true;
      else if(str.equalsIgnoreCase("N"))
        break;
      else
        System.out.print("Invalid input. Try again. Enter Y or N: ");
    }
    return false;
  }

  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));         // Keyboard input
  private static int stake = 0;                                                                    // Player's current stake in dollars
  private static int playerCash = 0;                                                               // Player's winnings(or losses!)

  private static BlackjackDeck deck = new BlackjackDeck();                                         // The card deck for the game
  private static BlackjackHand playerHand;                                                         // The player's hand
  private static BlackjackHand dealerHand;                                                         // The dealer's hand
}
