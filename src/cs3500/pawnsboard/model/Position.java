package cs3500.pawnsboard.model;

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