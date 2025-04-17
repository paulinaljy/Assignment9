package cs3500.pawnsboard.model;

import java.util.Random;

import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;

public class EnhancedPawnsBoardBuilder extends PawnsBoardBuilder {
  /**
   * Initializes an EnhancedPawnsBoardBuilder with no arguments.
   */
  public EnhancedPawnsBoardBuilder() {
    // builder with no arguments
  }

  @Override
  public QueensBlood build() {
    return new EnhancedPawnsBoardModel(width, height, new Random(), new PawnsBoardDeckConfig());
  }
}
