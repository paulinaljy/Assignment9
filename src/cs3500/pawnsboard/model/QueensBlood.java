package cs3500.pawnsboard.model;

import java.util.List;

/**
 * Represents the mutable behaviors of a QueensBlood game, including setting the next player,
 * placing a card, passing, drawing the next card, and starting the game.
 */
public interface QueensBlood extends ReadonlyPawnsBoardModel {

  /**
   * Updates the player turn.
   *
   * @throws IllegalStateException if the game has not started
   */
  void setNextPlayer();

  /**
   * Places a card from the hand to a given position on the board and then draws a card from the
   * deck if able.
   *
   * @param cardIdx index of the card in hand to place (0-index based)
   * @param row     row to place the card in (0-index based)
   * @param col     column to place the card in (0-index based)
   * @throws IllegalArgumentException if cardIdx is out of bounds of the hand
   * @throws IllegalArgumentException if row or column do not indicate a position on the board
   * @throws IllegalStateException    if the game hasn't started
   * @throws IllegalStateException    if the given position does not contain pawns
   * @throws IllegalStateException    if the given card and position does not contain pawns the
   *                                  player owns
   * @throws IllegalStateException    if the player does not have enough pawns
   *                                  to cover the cost of the card
   */
  void placeCardInPosition(int cardIdx, int row, int col);

  /**
   * Current player's turn ends and switches to the other player.
   *
   * @throws IllegalStateException if the game hasn't started
   */
  void pass();

  /**
   * Current player draws the next card from the deck and adds it to their hand.
   *
   * @throws IllegalStateException if the game has not started
   */
  void drawNextCard();

  /**
   * Starts the game with the given deck and hand size. Players are dealt equal number of cards to
   * their hands randomly from their decks.
   *
   * @param p1Deck   player 1 list of cards to play the game with
   * @param p2Deck   player 2 list of cards to play the game with
   * @param handSize maximum hand size for the game
   * @param shuffle  whether the deck should be shuffled
   * @throws IllegalStateException    if the game has already been started
   * @throws IllegalArgumentException if either decks are null or contains a null object,
   *                                  or if the deck does not contain enough cards to fill the board
   * @throws IllegalArgumentException if handSize is not positive (i.e. 0 or less) or is greater
   *                                  than a third of the deck size
   */
  void startGame(List<GameCard> p1Deck, List<GameCard> p2Deck, int handSize, boolean shuffle);

  /**
   * Adds the observer to any listeners so actions on the view are
   * delegated to the observer.
   * @param observer observer
   */
  void subscribe(ModelActions observer, int playerID);
}
