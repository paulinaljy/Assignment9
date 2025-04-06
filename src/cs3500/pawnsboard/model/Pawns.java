package cs3500.pawnsboard.model;

import java.awt.Color;
import java.util.Objects;

/**
 * Represents a Pawns with a color and count on the game board with behaviors including updating the
 * cell count or color if influenced.
 */
public class Pawns implements Cell {
  private Color color;
  private int count;

  /**
   * Initializes the Pawns with a color and count of 1.
   * @param color the color of the pawn
   */
  public Pawns(Color color) {
    this.color = color;
    this.count = 1;
  }

  @Override
  public int getCost() {
    return this.count;
  }

  @Override
  public int getValue() {
    return this.count;
  }

  @Override
  public Color getOwnedColor() {
    return this.color;
  }

  @Override
  public Color getCellColor() {
    return Color.gray;
  }

  /**
   * If this pawn is owned by the current player, increment the count by 1. If this pawn is owned
   * by the other player, update this pawn's color to the current player's color.
   * @param currentPlayer the current player of the game
   * @return the updated pawn cell
   */
  @Override
  public Cell influence(Player currentPlayer) {
    if (currentPlayer.getColor().equals(this.getOwnedColor()) && this.count < 3) {
      this.count += 1; // adds 1 to Pawns count
    } else { // if cell is other player => update color to current player color
      this.color = currentPlayer.getColor();
    }
    return this;
  }

  @Override
  public boolean isCardPlaceable() {
    return true;
  }

  @Override
  public boolean isGameCard() {
    return false;
  }

  @Override
  public Cell getCell() {
    return this;
  }

  /**
   * Returns the textual view of this pawns as a String for the number of pawns on the board
   * (integer of 1, 2, or 3).
   */
  public String toString() {
    return Integer.toString(this.count);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Pawns)) {
      return false;
    }
    Pawns that = (Pawns) other;
    return this.count == that.count && this.color.equals(that.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color);
  }
}
