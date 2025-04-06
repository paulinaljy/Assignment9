package cs3500.pawnsboard.model;

import java.awt.Color;

import cs3500.pawnsboard.model.Cell;

/**
 * Represents the immutable behaviors of a cell, including getting the cost, value, owner color of
 * the cell, the cell color, whether the card is placeable on the board, and getting the cell.
 *
 */
public interface ReadOnlyCell {
  /**
   * Returns the cost of the cell. If the cell is an EmptyCell, return 4 (greater than any card
   * cost to indicate that the player cannot place a card here, as there are no pawns).
   * If the cell is Pawns, return the count of pawns in this class. If the cell is a GameCard,
   * return the cost of the card.
   * @return the cost of the cell
   */
  int getCost();

  /**
   * Returns the value of the cell. If the cell is an EmptyCell, return 0. If the cell is a Pawns,
   * return the count of pawns in this class. If the cell is a GameCard, return the value of the
   * card.
   * @return the value of the cell
   */
  int getValue();

  /**
   * Returns the color of the owner. If the cell is an EmptyCell, return gray. If the cell is Pawns,
   * return the color of the pawns in this class. If the cell is a GameCard, return the color of
   * the game cards.
   * @return the color of the owner of the cell
   */
  Color getOwnedColor();

  /**
   * Returns the color of this cell. If the cell is an EmptyCell or Pawns, return gray. If the cell
   * is a GameCard, return the color of the game cards.
   * @return the color of the cell
   */
  Color getCellColor();

  /**
   * Checks whether a game card is placeable in this cell. If this cell is empty or a game card,
   * return false. If this cell is a pawns, return true.
   * @return boolean whether a card can be placed in this cell
   */
  boolean isCardPlaceable();

  /**
   * Returns whether this cell is a game card. If the cell is an EmptyCell or Pawns, return false.
   * If the cell is a GameCard, return true.
   * @return whether the cell is a game card
   */
  boolean isGameCard();

  /**
   * Returns the cell, either an EmptyCell, Pawns, or GameCard.
   * @return the cell
   */
  Cell getCell();
}