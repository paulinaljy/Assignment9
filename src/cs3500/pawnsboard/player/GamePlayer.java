package cs3500.pawnsboard.player;

import cs3500.pawnsboard.view.ViewActions;

/**
 * Interface that defines the actions available to all players (Human and AI), including choosing
 * the next move, getting the player ID, checking if this player is a human, and subscribing to the
 * ViewActions observer who should be notified.
 */
public interface GamePlayer {
  /**
   * Chooses the next move of the game for the player.
   */
  void chooseMove();

  /**
   * Returns whether this player is human.
   * @return boolean whether this player is human
   */
  boolean isHumanPlayer();

  /**
   * Returns the corresponding player ID of this player.
   * @return int representing a player
   */
  int getPlayerID();

  /**
   * Subscribes to the observer who should be notified about events.
   * @param observer the ViewActions observer who should be notified
   */
  void subscribe(ViewActions observer);
}
