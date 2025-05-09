package cs3500.pawnsboard.model;


/**
 * Represents the mutable behaviors of a cell in the board (empty cell, pawns, or game card),
 * including applying the influence of the card on the cell.
 */
public interface Cell extends ReadOnlyCell {

  /**
   * Updates the cell based on the influence of the card and the given player.
   * @param currentPlayer the current player of the game
   * @param futureValue the current future value of this cell
   * @return new cell that is updated
   */
  Cell influence(Player currentPlayer, int futureValue);

  /**
   * Updates the cell based on the upgrade influence of the card.
   * @return new cell that is updated
   */
  Cell upgrade();

  /**
   * Updates the cell based on the devalue influence of the card.
   * @return new cell that is updated
   */
  Cell devalue();
}