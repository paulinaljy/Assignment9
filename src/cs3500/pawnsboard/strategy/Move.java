package cs3500.pawnsboard.strategy;

import java.util.Objects;


/**
 * Represents a Move with a card index, col, row, and boolean whether the player should pass.
 */
public class Move {
  private final int cardIdx;
  private final int col;
  private final int row;
  private final boolean pass;

  /**
   * Initializes a Move with a card index, row, col, and boolean pass.
   * @param cardIdx the card index in the player's hand (0-index)
   * @param row the row of the cell (0-index)
   * @param col the col of the cell (0-index)
   * @param pass boolean whether the player should pass
   */
  public Move(int cardIdx, int row, int col, boolean pass) {
    this.cardIdx = cardIdx;
    this.col = col;
    this.row = row;
    this.pass = pass;
  }

  /**
   * Returns whether this move is a pass.
   * @return boolean whether player should pass
   */
  public boolean isPass() {
    return this.pass;
  }

  /**
   * Returns card index in player's hand of this move.
   * @return card index (0-index)
   */
  public int getCardIdx() {
    return this.cardIdx;
  }

  /**
   * Returns row of cell in this move.
   * @return row (0-index)
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Returns col of cell in this move.
   * @return col (0-index)
   */
  public int getCol() {
    return this.col;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Move)) {
      return false;
    }
    Move that = (Move) other;
    return this.cardIdx == that.cardIdx && this.row == that.row
            && this.col == that.col && this.pass == that.pass;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row);
  }

  @Override
  public String toString() {
    return "Card " + cardIdx + " in (" + row + "," + col + ")";
  }
}
