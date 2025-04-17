package cs3500.pawnsboard.model;

/**
 * Represents behaviors of a Position, including getting the row and column deltas
 */
public interface Position {
  /**
   * Returns the horizontal delta from the position (2,2).
   * @return row delta
   */
  int getRowDelta();

  /**
   * Returns the vertical delta from the position (2,2).
   * @return col delta
   */
  int getColDelta();
}