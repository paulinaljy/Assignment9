package cs3500.pawnsboard.model;

import java.util.Objects;

/**
 * Represents an InfluencePosition in an influence grid of a GameCard. Each Position has a row delta
 * and col delta, which represents the distance from the center card position (2,2).
 */
public class InfluencePosition implements Position {
  private final int rowDelta;
  private final int colDelta;

  public InfluencePosition(int rowDelta, int colDelta) {
    this.rowDelta = rowDelta;
    this.colDelta = colDelta;
  }

  public int getRowDelta() {
    return this.rowDelta;
  }

  /**
   * Returns the vertical delta from the position (2,2).
   * @return col delta
   */
  public int getColDelta() {
    return this.colDelta;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof InfluencePosition)) {
      return false;
    }
    InfluencePosition that = (InfluencePosition) other;
    return this.rowDelta == that.rowDelta && this.colDelta == that.colDelta;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rowDelta);
  }
}
