package cs3500.pawnsboard.model;


/**
 * Represents the mutable behaviors of a cell in the board (empty cell, pawns, or game card),
 * including applying the influence of the card on the cell.
 */
public interface Cell extends ReadOnlyCell {

  /**
   * Updates the cell based on the influence of the card and the given player.
   * @param currentPlayer the current player of the game
   * @return new cell that is updated
   */
  Cell influence(Player currentPlayer);
}