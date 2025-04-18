package cs3500.pawnsboard.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a HighContrastPawnsBoardPanel that has behaviors, including drawing and updating the
 * game board (cells, game cards, pawns) in high contrast colors after clicked mouse events.
 */
public class HighContrastBoardPanel extends PawnsBoardPanel {
  /**
   * Initializes a HighContrastBoardPanel with a read only pawns board model.
   *
   * @param model the model of the game
   */
  public HighContrastBoardPanel(ReadonlyPawnsBoardModel model) {
    super(model);
  }

  /**
   * Draws the high contrast board with white lines.
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int height = model.getHeight();
    int width = model.getWidth();

    g2d.transform(getTransformForLogicalToPhysical());

    drawBoard(g2d);

    g2d.setColor(Color.WHITE);
    // draws vertical lines
    for (int col = 1; col < width + 3; col++) {
      drawLine(g2d, 0, col, height, col);
    }

    // draws horizontal lines
    for (int row = 0; row < height + 1; row++) {
      drawLine(g2d, row, 1, row, width + 1);
    }

    drawScore(g2d);
  }

  /**
   * Draws the game board in high contrast mode based on the type of cells in the model. If the cell
   * is an EmptyCell, draws a cell with a black background. If the cell is a Pawns, draws a cell with
   * a black background and circles with corresponding count of pawns and color of player. If the
   * player is red, draws pure red circles. If the player is blue, draws cyan colored circles.
   * If the cell is selected, draws a cell with yellow background.
   *
   * <p>If the cell is a GameCard, draws a cell with the corresponding color of
   * player and value of card.
   *
   * @param g2d Graphics2D object
   */
  @Override
  protected void drawBoard(Graphics2D g2d) {
    Point selectedBoardCell = null;
    if (observer != null) {
      selectedBoardCell = observer.getSelectedCell();
    }
    for (int row = 0; row < model.getHeight(); row++) {
      for (int col = 0; col < model.getWidth(); col++) {
        ReadOnlyCell cell = model.getCellAt(row, col);
        Color backgroundColor = cell.getCellColor();
        if (backgroundColor.equals(Color.gray)) {
          backgroundColor = Color.black;
        }
        if (backgroundColor.equals(Color.blue)) {
          backgroundColor = Color.cyan;
        }

        if (selectedBoardCell != null && row == selectedBoardCell.getX() && col
                == selectedBoardCell.getY())  {
          backgroundColor = Color.yellow;
        }

        // empty or pawns cell = fill gray, game card = fill color
        drawRect(g2d, row, col + 1, 1, 1, backgroundColor);

        // pawns cell
        Color ownedColor = cell.getOwnedColor();
        if (!cell.isGameCard() && (ownedColor.equals(Color.red) || ownedColor.equals(Color.blue))) {
          if (ownedColor == Color.blue) {
            ownedColor = Color.cyan;
          }
          drawPawns(g2d, row, col + 1, cell.getValue(), ownedColor);
        }

        // game card
        if (cell.isGameCard()) {
          int value = cell.getValue();
          drawValue(g2d, row, col + 1, value);
        }
      }
    }
  }

  /**
   * Adds a new mouse listener with the given observer to this pawns board panel.
   *
   * @param observer the {@link ViewActions} instance that will receive updates
   */
  @Override
  public void subscribe(ViewActions observer) {
    this.observer = observer;
    this.addMouseListener(new HighContrastBoardPanel.PawnsBoardMouseListener(observer));
  }

  /**
   * Represents a PawnsBoardMouseListener that responds to mouse clicked events in the game board
   * (PawnsBoardPanel class).
   */
  class PawnsBoardMouseListener extends MouseAdapter {
    private ViewActions observer;

    /**
     * Initializes a PawnsBoardMouseListener with an observer.
     */
    public PawnsBoardMouseListener(ViewActions observer) {
      this.observer = observer;
    }

    /**
     * Responds to mouse clicked events in the game board. Converts the clicked physical coordinates
     * to logical coordinates to model coordinates. Sets and highlights the selectedBoardCell to a
     * point with the clicked model coordinates. Deselects the cell if the current selectedBoardCell
     * is selected. After each mouse event, notifies the observer (controller) to set the selected
     * cell coordinates and redraws the game board.
     *
     * @param evt the mouse event to be processed
     */
    public void mouseClicked(MouseEvent evt) {
      Point2D physical = evt.getPoint();
      Point selectedBoardCell = null;
      if (observer != null) {
        selectedBoardCell = observer.getSelectedCell();
      }

      if (model.isGameOver() || !observer.isViewEnabled()) {
        System.out.println("not your turn");
        return;
      }

      try {
        // create objects to convert
        AffineTransform physicalToLogical = getTransformForLogicalToPhysical();
        physicalToLogical.invert();

        AffineTransform logicalToModel = getTransformForModelToLogical();
        logicalToModel.invert();

        // convert using objects to coordinates
        Point2D logical = physicalToLogical.transform(physical, null);
        Point2D model = logicalToModel.transform(logical, null);

        // observer takes coordinates and performs action
        if (selectedBoardCell != null && selectedBoardCell.getX() == (int) model.getX()
                && selectedBoardCell.getY() == (int) model.getY()) {
          selectedBoardCell = null;
        } else {
          if ((int) model.getX() > 0 && (int) model.getX() <= HighContrastBoardPanel.this.model.getWidth()
                  && (int) model.getY() >= 0
                  && (int) model.getY() < HighContrastBoardPanel.this.model.getHeight()) {
            selectedBoardCell = new Point((int) model.getX(), (int) model.getY());
            observer.setSelectedCell((int) selectedBoardCell.getY(),
                    (int) selectedBoardCell.getX() - 1);
          }
        }
        repaint();

      } catch (NoninvertibleTransformException ex) {
        throw new RuntimeException(ex);
      }
    }
  }
}
