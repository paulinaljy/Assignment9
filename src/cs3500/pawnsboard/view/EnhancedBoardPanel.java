
package cs3500.pawnsboard.view;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents an EnhancedBoardPanel that has behaviors, including drawing the upgrade and devalue
 * influences of each cell in the game board.
 */
public class EnhancedBoardPanel extends PawnsBoardPanel {
  /**
   * Initializes an EnhancedPawnsBoardPanel with a read only pawns board model.
   *
   * @param model the model of the game
   */
  public EnhancedBoardPanel(ReadonlyPawnsBoardModel model) {
    super(model);
  }

  @Override
  protected void drawBoard(Graphics2D g2d) {
    super.drawBoard(g2d);
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        ReadOnlyCell cell = model.getCellAt(row, col);
        int futureValue = cell.getFutureValue();
        drawFutureValue(g2d, row, col + 1, futureValue);
      }
    }
  }

  /**
   * Draws the future values of each cell on the board. Converts the given model coordinates to
   * logical and then draws the given future value as a string based on that converted coordinates.
   * @param g2d the Graphics2D object
   * @param row row of the cell to be drawn in (0-index based)
   * @param col col of the cell to be drawn in (0-index based)
   * @param value future value of the cell
   */
  private void drawFutureValue(Graphics2D g2d, int row, int col, int value) {
    AffineTransform modelToLogical = getTransformForModelToLogical();
    Point2D src = modelToLogical.transform(new Point(col, row), null); // starting point
    // starting point of next col
    Point2D dst = modelToLogical.transform(new Point(col + 1, row + 1), null);
    int x = ((int) src.getX() + (int) dst.getX()) / 2; // in between starting point and next col
    int y = ((int) src.getY() + (int) dst.getY()) / 2;

    g2d.setColor(Color.DARK_GRAY);

    Font font = g2d.getFont();
    Font smallerFont = font.deriveFont(font.getSize2D() - 7);
    g2d.setFont(smallerFont);

    String stringValue = Integer.toString(value);
    if (value > 0) {
      stringValue = "+" + stringValue;
    } else if (value == 0) {
      stringValue = "";
    }
    g2d.drawString(stringValue, x + 4, y - 5);

    g2d.setFont(font);
  }
}
