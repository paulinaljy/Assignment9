package cs3500.pawnsboard.model;

import java.awt.Color;

/**
 * Represents the mutable behaviors of a game card, including setting the card to a player's color
 * to claim ownership. Extends the behaviors of Cell and ReadOnlyGameCard.
 */
public interface Card extends Cell, ReadOnlyGameCard {
  /**
   * Sets the color of the card to the given player's color.
   *
   * @param playersColor the color of the current player
   */
  void setColor(Color playersColor);

}