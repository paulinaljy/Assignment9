package cs3500.pawnsboard.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

import cs3500.pawnsboard.model.ReadOnlyCell;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a PawnsBoardPanel that has behaviors, including drawing and updating the game board
 * (cells, game cards, pawns) after clicked mouse events.
 */
public class PawnsBoardPanel extends AbstractPawnsBoardPanel {
  /**
   * Initializes a PawnsBoardPanel with a read only pawns board model.
   *
   * @param model the model of the game
   */
  public PawnsBoardPanel(ReadonlyPawnsBoardModel model) {
    super(model);
  }

  /**
   * Adds a new mouse listener with the given observer to this pawns board panel.
   *
   * @param observer the {@link ViewActions} instance that will receive updates
   */
  @Override
  public void subscribe(ViewActions observer) {
    this.observer = observer;
    this.addMouseListener(new PawnsBoardMouseListener(observer));
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
          if ((int) model.getX() > 0 && (int) model.getX() <= PawnsBoardPanel.this.model.getWidth()
                  && (int) model.getY() >= 0
                  && (int) model.getY() < PawnsBoardPanel.this.model.getHeight()) {
            selectedBoardCell = new Point((int) model.getX(), (int) model.getY()); // col, row
            observer.setSelectedCell((int) selectedBoardCell.getY(),
                    (int) selectedBoardCell.getX() - 1); // row, col - 1
          }
        }
        repaint();

      } catch (NoninvertibleTransformException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

}
