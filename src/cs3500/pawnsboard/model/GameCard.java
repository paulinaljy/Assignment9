package cs3500.pawnsboard.model;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

/**
 * Represents a GameCard in the game board and the deck of cards used in the game with a name,
 * cost, value, and influence grid. A GameCard also has a color that sets the player ownership
 * when placed on the game board.
 */
public class GameCard implements Card {
  /**
   * Represents a Cost, which is either 1, 2, or 3.
   */
  public enum Cost {
    ONE(1),
    TWO(2),
    THREE(3);
    private final int value;

    Cost(int value) {
      this.value = value;
    }
  }

  private final String name;
  private final Cost cost;
  private final int valueScore;
  private final List<Position> influenceGrid;
  private Color color;

  /**
   * Initializes a GameCard with a name, cost, value, and influence grid.
   *
   * @param name          name of the card
   * @param cost          cost of the card
   * @param valueScore    value of the card
   * @param influenceGrid list of relative positions representing cells influenced
   */
  public GameCard(String name, Cost cost, int valueScore, List<Position> influenceGrid) {
    if (valueScore <= 0) {
      throw new IllegalArgumentException("Value score of the card must be a positive integer");
    }
    this.name = name;
    this.cost = cost;
    this.valueScore = valueScore;
    this.influenceGrid = influenceGrid;
    this.color = Color.white;
  }

  /**
   * Returns the corresponding cost of the given value.
   * @param value the value of the cost, either 1, 2, or 3
   * @return the Cost
   */
  public static Cost valueToCost(int value) {
    switch (value) {
      case 1:
        return Cost.ONE;
      case 2:
        return Cost.TWO;
      case 3:
        return Cost.THREE;
      default:
        throw new IllegalArgumentException("Value must be 1, 2, or 3");
    }
  }

  @Override
  public int getCost() {
    return this.cost.value;
  }

  @Override
  public Color getOwnedColor() {
    return this.color;
  }

  @Override
  public Color getCellColor() {
    return this.color;
  }

  @Override
  public Cell influence(Player currentPlayer) {
    return this;
  }

  @Override
  public boolean isCardPlaceable() {
    return false;
  }

  @Override
  public int getValue() {
    return this.valueScore;
  }

  @Override
  public boolean isGameCard() {
    return true;
  }

  @Override
  public Cell getCell() {
    return this;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean isCellInfluencedAt(int row, int col) {
    for (int i = 0; i < influenceGrid.size(); i++) {
      int rowPosition = influenceGrid.get(i).getRowDelta() + 2;
      int colPosition = influenceGrid.get(i).getColDelta() + 2;
      if (rowPosition == row && colPosition == col) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void setColor(Color playersColor) {
    this.color = playersColor;
  }

  @Override
  public List<Position> getPositions() {
    return this.influenceGrid;
  }

  /**
   * Returns the textual view of this game card. If the card color is red (owned by red player),
   * return "R". If the card color is blue (owned by blue player), return "B". If the card is not
   * yet owned by a player (not placed on the board), return the name of the card.
   *
   * @return a string representing the color of the game card.
   */
  public String toString() {
    if (this.color.equals(Color.red)) {
      return "R";
    } else if (this.color.equals(Color.blue)) {
      return "B";
    } else {
      return this.name;
    }
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof GameCard)) {
      return false;
    }
    GameCard that = (GameCard) other;
    return this.name.equals(that.name) && this.cost.equals(that.cost)
            && this.valueScore == that.valueScore && this.influenceGrid.equals(that.influenceGrid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
