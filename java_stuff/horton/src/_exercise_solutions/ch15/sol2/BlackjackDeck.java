import java.util.Stack;
import java.util.Random;
import java.util.Collections;
// A blackjack deck is an ordinary deck consisting of two (or more) standard decks
// with a marker card inserted that indicates when the dealer should shuffle the deck,
// or in our case, create a new deck.
public class BlackjackDeck {
  // This constructor creates a deck containing two standard decks.
  public BlackjackDeck() {
    this(2);
  }

  // This constructor creates a deck containing one or more standard decks
  public BlackjackDeck(int numberOfDecks) {
    assert numberOfDecks > 0;

    // Create deck by adding cards from the specified number of decks
    for(int i = 0 ;  i < numberOfDecks ; ++i) {
      for(Card.Suit suit : Card.Suit.values()) {
        for(Card.Rank rank : Card.Rank.values()) {
          deck.push(new Card(rank, suit));
        }
      }
    }
    Collections.shuffle(deck);                                                                     // Shuffle the deck

    // Insert the marker card. Since Stack has Vector as a base class
    // we can use the inherited insertElementAt() method to insert the marker.
    // We will insert the marker at a random position in the third quarter of
    // the deck. The bottom of the stack is at index position 0.
    Random random = new Random();
    deck.insertElementAt(Card.MARKER_CARD, deck.size()/4 + random.nextInt(deck.size()/4));
  }

  // Deal one card into a given hand
  public BlackjackHand dealCard(BlackjackHand hand) {
    assert(!deck.isEmpty());
    Card card = deck.pop();
    if(card == Card.MARKER_CARD) {
      markerFound = true;
      card = deck.pop();
    }
    return hand.add(card);
  }

  public BlackjackHand dealBlackjackHand() {
    BlackjackHand hand = new BlackjackHand();
    return dealCard(dealCard(hand));
  }
  // Test for marker card found when dealing
  public boolean markerCardFound() {
    return markerFound;
  }

  // Cards remaining in the deck
  public int size() {
   return deck.size();
 }

  protected Stack<Card> deck = new Stack<>();                                                      // Deck of cards for blackjack
  private boolean markerFound = false;                                                             // Indicates that marker card was found.
}
