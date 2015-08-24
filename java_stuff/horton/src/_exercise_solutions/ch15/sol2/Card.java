// Class defining a card.

class Card implements Comparable<Card> {
// Constructor:
  public Card(Rank rank , Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  // Constructor for a MARKER card, which is defined as a card with no suit/rank assigned
  // The constructor is private because it is only used inside the class.
  private Card() {}

  // Return the rank of a card
  public Rank getRank() {
    return rank;
  }

  // Return the rank of a card
  public Suit getSuit() {
    return suit;
  }

  // Return the Blackjack value of a card
  public int value() {
    return rank.value();
  }

  // Method to display a card:
  @Override
  public String toString() {
   return rank + " of " + suit;
  }

  // Compare two cards
  public int compareTo(Card card) {
    if(suit == card.suit) {
      return rank.compareTo(card.rank);
    }
    return suit.compareTo(card.suit);
  }

  // Definition of a marker card
  public static final Card MARKER_CARD = new Card();

  // Members defining the suit and value of a Card instance
  private Suit suit;
  private Rank rank;

  public static enum Suit{ Clubs, Diamonds, Hearts, Spades }
  public static enum Rank {
    Ace(1), deuce(2), three(3), four(4), five(5), six(6), seven(7),
    eight(8), nine(9), ten(10), Jack(10), Queen(10), King(10);

    // Constructor to set a value for enum constants
    Rank(int value) {
      this.value = value;
    }

    // Get the value
    int value() {
      return value;
    }
    private final int value;                                                                       // Enum value must not be changed
  }
}
