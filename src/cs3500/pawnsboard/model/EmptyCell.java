package cs3500.pawnsboard.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an EmptyCell on the game board with behaviors, including updating the cell to a Pawns
 * if influenced.
 */
public class EmptyCell implements Cell {
  private int futureValue;

  public EmptyCell() {
    this.futureValue = 0;
  }

  @Override
  public int getCost() {
    return 4;
  }

  @Override
  public int getValue() {
    return 0;
  }

  @Override
  public Color getOwnedColor() {
    return Color.gray;
  }

  @Override
  public Color getCellColor() {
    return Color.gray;
  }

  /**
   * Updates this empty cell to a Pawns cell with a count of 1 pawn.
   * @param currentPlayer the current player of the game
   * @return a new Pawns cell
   */
  @Override
  public Cell influence(Player currentPlayer, int futureValue) {
    return new Pawns(currentPlayer.getColor(), 1, futureValue);
  }

  @Override
  public Cell upgrade() {
    this.futureValue += 1;
    return this;
  }

  @Override
  public Cell devalue() {
    this.futureValue -= 1;
    return this;
  }

  @Override
  public boolean isCardPlaceable() {
    return false;
  }

  @Override
  public boolean isGameCard() {
    return false;
  }

  @Override
  public Cell getCell() {
    return this;
  }

  @Override
  public String toString() {
    return "_";
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof EmptyCell)) {
      return false;
    }
    EmptyCell that = (EmptyCell) other;
    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash();
  }

  @Override
  public int getFutureValue() { return this.futureValue; }
}
