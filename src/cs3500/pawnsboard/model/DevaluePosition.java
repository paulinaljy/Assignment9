package cs3500.pawnsboard.model;

import java.util.Objects;

/**
 * Represents a DevaluePosition in an influence grid of a GameCard. Each Position has a row delta
 * and col delta, which represents the distance from the center card position (2,2).
 */
public class DevaluePosition implements Position {
  private final int rowDelta;
  private final int colDelta;

  /**
   * Initializes an DevaluePosition with a rowDelta and colDelta.
   * @param rowDelta the horizontal distance from position (2,2)
   * @param colDelta the vertical distance from position (2,2)
   */
  public DevaluePosition(int rowDelta, int colDelta) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
  }

  public int getRowDelta() {
    return this.rowDelta;
  }

  public int getColDelta() {
    return this.colDelta;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof DevaluePosition)) {
      return false;
    }
    DevaluePosition that = (DevaluePosition) other;
    return this.rowDelta == that.rowDelta && this.colDelta == that.colDelta;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rowDelta);
  }
}
