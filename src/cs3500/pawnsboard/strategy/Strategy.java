package cs3500.pawnsboard.strategy;

import cs3500.pawnsboard.model.Player;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a Strategy with behaviors including choosing a move.
 */
public interface Strategy {

  /**
   * Chooses a move based on the strategy. Strategy is determined by implementation.
   * @param model the read only pawns board model of the game
   * @param player the current player
   * @return a Move with the card index, row, col, and boolean whether the player should pass
   */
  Move chooseMove(ReadonlyPawnsBoardModel model, Player player);
}
