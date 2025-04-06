package cs3500.pawnsboard.view;

import java.io.IOException;

/**
 * Represents the behaviors of the textual view of a QueensBlood game.
 */
public interface QueensBloodTextualView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.)
   * to the given appendable.
   * @param append where to send the model information to
   * @throws IllegalArgumentException if append is null
   * @throws IOException if the rendering fails for some reason
   */
  void render(Appendable append) throws IOException;
}
