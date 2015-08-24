import java.util.Vector;
import java.util.Collections;
public class BlackjackHand
{
  // Add a card to the hand
  public BlackjackHand add(Card card) {
    hand.add(card);
    return this;
  }

  // Calculates the value of the hand.
  // Ace is counted as 11 unless this will cause the value to exceed 21
  public int value() {
    int handValue = 0;
    for(Card card : hand) {
      handValue += card.value();
    }
    if(hasAce() && handValue < 12)
      handValue += 10;

    return handValue;

  }

  // Tests for blackjack
  public boolean isBlackjack() {
    return (hand.size() == 2 && hasAce() && hasTen());
  }

  // Calculates whether the value of a hand exceeds 21
  public boolean isBust() {
    return value() > 21;
  }

  // Tests for an Ace in the hand
  public boolean hasAce() {
    for(Card card : hand) {
      if(card.value() == 1) {
        return true;
      }
    }
    return false;
  }

  // Tests for a 10, J, Q, or K in the hand
  public boolean hasTen() {
    for(Card card : hand) {
      if(card.value() == 10) {
        return true;
      }
    }
    return false;
  }


  public String toString() {
    StringBuffer str = new StringBuffer();
    Collections.sort(hand);
    for(Card card : hand)
      str.append(card).append(" | ");
    return str.toString();

  }

	private Vector<Card> hand = new Vector<>();                                                       // Stores the cards in the hand
}
