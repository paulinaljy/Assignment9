package cs3500.pawnsboard.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the immutable behaviors of the pawns board game, including getting the current player,
 * getting the width and height of the board, getting the cell at a specific row and col, getting
 * the player's hand, calculating the player scores and winner, making a copy of the board, and
 * getting the current player based on color or id.
 */
public interface ReadonlyPawnsBoardModel {
  /**
   * Returns the current player.
   *
   * @return the current player.
   * @throws IllegalStateException if the game has not started
   */
  Player getCurrentPlayer();

  /**
   * Returns true if the game is over.
   *
   * @return true if the game is over, false otherwise
   * @throws IllegalStateException if the game has not started
   */
  boolean isGameOver();

  /**
   * Retrieve the number of cards that make up the width of the board.
   * (e.g. the number of columns in the widest row)
   *
   * @return the width of the board
   */
  int getWidth();

  /**
   * Retrieve the number of cards that make up the height of the board.
   * (e.g. the number of rows in the highest column)
   *
   * @return the height of the board
   */
  int getHeight();

  /**
   * Returns the type of cell in the indicated position on the board.
   *
   * @param row the row to access (0-index based)
   * @param col the column to access (0-index based)
   * @return the card in the valid position or null if the position has no card
   * @throws IllegalArgumentException if the row and column are not a valid location
   *                                  for a card in the board
   * @throws IllegalStateException    if the game has not started
   */
  ReadOnlyCell getCellAt(int row, int col);

  /**
   * Returns a copy of the player's current hand. If their hand is empty, then an empty
   * list is returned.
   *
   * @param playerID the current player of the game
   * @return a copy of the player's current hand
   * @throws IllegalStateException if the game has not started
   */
  List<GameCard> getHand(int playerID);

  /**
   * Returns the winner of the game based on the player scores.
   *
   * @return the player with the highest score
   * @throws IllegalStateException if the game has not started
   */
  Player getWinner();

  /**
   * Returns the winning score of the game.
   *
   * @return the winning score of the game
   * @throws IllegalStateException if the game has not started
   */
  int getWinningScore();

  /**
   * Calculate the score of player 2 cells of the given row.
   *
   * @param row given row
   * @return the score of Player 2 based on the given row
   * @throws IllegalStateException if the game has not started
   */
  int getP2RowScore(int row);

  /**
   * Calculates the score of Player 1 based on the given row.
   *
   * @param row given row
   * @return the score of Player 1 of the given row
   * @throws IllegalStateException if the game has not started
   */
  int getP1RowScore(int row);

  /**
   * Returns a copy of the current board.
   * @return list of array list of cells
   */
  List<ArrayList<Cell>> getBoard();

  /**
   * Returns the player that owns the cell.
   * @return Player that owns the cell
   */
  Player getOwnerOfCell(int row, int col);

  /**
   * Returns the player color based on the given player ID.
   * @param playerID the id of the player, either 1 or 2 representing a player in the game
   * @return color of the player
   */
  Color getPlayerColor(int playerID);

  /**
   * Returns the player's current score in the game given the player ID.
   * @param playerID the ide of the player, either 1 or 2 representing a player in the game
   * @return value representing the score
   */
  int getPlayerTotalScore(int playerID);

  /**
   * Returns the corresponding player ID of the current player. If the current player is player 1,
   * returns 1. If the current player is player 2, returns 2.
   * @return the player ID of the current player
   */
  int getCurrentPlayerID();

  /**
   * Returns the Player that corresponds with the given color. If the given color is red, returns
   * Player 1. If the given color is blue, returns Player 2.
   * @param color the color of the Player
   * @return the Player associated with the color
   */
  Player getPlayerByColor(Color color);
}