package cs3500.pawnsboard.view;

/**
 * Represents a ViewActions interface that make up the game play actions of the game, including
 * placing a card, quitting, passing, setting a card index, setting a selected cell, and checking
 * whether the view should be playable.
 */
public interface ViewActions {

  /**
   * Places the card on the game board given the card index, row, and column.
   */
  void placeCard();

  /**
   * Passes to the next player's turn.
   */
  void pass();

  /**
   * Sets the selected card index.
   * @param cardIdx card index of selected card in player's hand (0-index)
   */
  void setCardIdx(int cardIdx);

  /**
   * Sets the selected cell in the game board.
   * @param row row of the selected cell (0-index)
   * @param col col of the selected cell (0-index)
   */
  void setSelectedCell(int row, int col);

  /**
   * Returns whether the view should be playable (cells and cards clickable).
   * @return boolean whether the view should be playable
   */
  boolean isViewEnabled();

}

