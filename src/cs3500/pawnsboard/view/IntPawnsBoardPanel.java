package cs3500.pawnsboard.view;

import java.awt.Point;

/**
 * Represents a PawnsBoardPanel interface with behaviors, including getting the selected board cell
 * and resetting the state of a selected cell.
 */
public interface IntPawnsBoardPanel extends GamePanel {
  /**
   * Returns the selected board cell as a point (row, col) in the game board.
   * @return a point (row,col)
   */
  Point getSelectedBoardCell();

  /**
   * Resets the selected cell to be deselected (null).
   */
  void reset();
}
