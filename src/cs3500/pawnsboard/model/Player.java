package cs3500.pawnsboard.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a Player in the PawnsBoard game that has behaviors, including drawing the next card
 * from the deck and removing a card from their hand.
 */
public class Player {
  private final Color color;
  private final List<GameCard> deck;
  private final List<GameCard> hand;
  private final Random rand;
  private final boolean shuffle;

  /**
   * Initializes a Player with a color, empty deck, and empty hand. Adds cards to the starting
   * hand based on the given hand size and random object.
   * @param color the color of the Player
   * @param deck the list of this Player's game cards used in the game
   * @param handSize the starting hand size of the game
   * @param rand random object used to draw the starting hand
   * @param shuffle whether the starting hand size should be dealt randomly
   */
  public Player(Color color, List<GameCard> deck, int handSize, Random rand, boolean shuffle) {
    this.color = color;
    this.deck = new ArrayList<GameCard>(deck);
    this.rand = rand;
    this.shuffle = shuffle;
    this.hand = new ArrayList<GameCard>();
    this.drawStartingHand(handSize);
  }

  /**
   * Deals the player's starting hand based on the given hand size. If shuffle is true, cards are
   * dealt randomly. If shuffle is false, cards are dealt by drawing the first card in the deck.
   * @param handSize the player's hand size
   */
  private void drawStartingHand(int handSize) {
    for (int i = 0; i < handSize; i++) {
      int cardPos = 0;
      if (shuffle) {
        cardPos = rand.nextInt(deck.size()); // generates random int
      }
      this.drawNextCard(cardPos);
    }
  }

  /**
   * Deals the card from the deck to the player's hand based on the given card position in the
   * deck.
   * @param cardPos the given index of the card
   */
  public void drawNextCard(int cardPos) {
    //ArrayList<GameCard> deckCopy = new ArrayList<GameCard>(this.deck);
    if (deck.size() <= 0) {
      throw new IllegalStateException("Cannot draw from deck");
    } else {
      GameCard card = deck.remove(cardPos);
      this.hand.add(card);
    }
  }

  /**
   * Returns the card in the player's hand based on the given card index.
   * @param cardIdx card index of card to get
   * @return the card of the given index
   */
  public GameCard getCard(int cardIdx) {
    return this.hand.get(cardIdx);
  }

  /**
   * Returns the color of this player.
   * @return color of the player
   */
  public Color getColor() {
    return this.color;
  }

  /**
   * Returns a copy of the player's current hand. If the hand is empty, returns an empty list.
   * @return list of game cards
   */
  public List<GameCard> getHand() {
    return new ArrayList<GameCard>(this.hand);
  }

  /**
   * Removes the card from the player's hand given the cardIdx.
   * @param cardIdx the index of the card to be removed (0-based)
   * @return the removed game card
   */
  public GameCard removeCard(int cardIdx) {
    return this.hand.remove(cardIdx);
  }

  /**
   * Returns the hand size of this player.
   * @return the hand size
   */
  public int getHandSize() {
    return this.hand.size();
  }

  /**
   * Returns a copy of this player's deck.
   * @return a list of game cards
   */
  public List<GameCard> getDeck() {
    return new ArrayList<GameCard>(this.deck);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Player)) {
      return false;
    }
    Player that = (Player) other;
    return this.color.equals(that.color);
  }



  @Override
  public int hashCode() {
    return Objects.hash(color);
  }

  @Override
  public String toString() {
    String player = "Player 1";
    if (this.getColor().equals(Color.blue)) {
      player = "Player 2";
    }
    return player;
  }
}
