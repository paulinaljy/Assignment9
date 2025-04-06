package cs3500.pawnsboard.model;

import java.util.Random;

import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;

/**
 * Represents a pawns board builder to build a pawns board game.
 */
public class PawnsBoardBuilder {
  private int width;
  private int height;

  /**
   * Initializes a PawnsBoardBuilder with default width and height options.
   * @param width width of the board
   * @param height height of the board
   */
  public PawnsBoardBuilder(int width, int height) {
    this.width = 5;
    this.height = 3;
  }

  /**
   * Initializes a PawnsBoardBuilder with no arguments.
   */
  public PawnsBoardBuilder() {
    // builder with no arguments
  }

  /**
   * Sets the width of the pawns board game with the given width.
   * @param width the width of the board in the game (number of columns)
   * @return the builder after width is set
   */
  public PawnsBoardBuilder setWidth(int width) {
    this.width = width;
    return this;
  }

  /**
   * Sets the height of the pawns board game with the given height.
   * @param height the length of the board in the game (number of rows)
   * @return the builder after height is set
   */
  public PawnsBoardBuilder setHeight(int height) {
    this.height = height;
    return this;
  }

  public QueensBlood build() {
    return new PawnsBoardModel(width, height, new Random(), new PawnsBoardDeckConfig());
  }
}
