package cs3500.pawnsboard.model;

import java.util.List;

/**
 * Represents the immutable behaviors of a game card, including checking whether the cell in its
 * influenced grid is influenced at a specific row and col, getting the list of position in its
 * influence grid, and getting the name of the game card.
 */
public interface ReadOnlyGameCard extends ReadOnlyCell {
  /**
   * Returns whether this cell is an influenced at the given row and col. A cell is influenced if
   * its row position equals the given target row and col position equals the given target col.
   * @param row the row of the cell (0-index)
   * @param col the col of the cell (0-index)
   * @return boolean whether the cell is influenced
   */
  boolean isCellInfluencedAt(int row, int col);

  /**
   * Returns the list of positions of the influence grid in this game card.
   *
   * @return list of positions
   */
  List<Position> getPositions();


  /**
   * Returns the name of this game card.
   * @return the name
   */
  String getName();
}