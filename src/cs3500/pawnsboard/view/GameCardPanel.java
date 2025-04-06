package cs3500.pawnsboard.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;

import cs3500.pawnsboard.model.ReadOnlyGameCard;
import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;


/**
 * Represents a GameCardPanel that draws the game cards used in the game, including the name, cost,
 * and value of the card and influence grid.
 */
public class GameCardPanel extends JPanel {
  private final ReadonlyPawnsBoardModel pawnsBoardModel;
  private final JPanel influenceGrid;
  private final int cardIdx;
  private final int playerID;

  /**
   * Initializes the GameCardPanel with a read only model, card index, and player ID.
   * @param pawnsBoardModel read only model of the game
   * @param cardIdx card index of this game card (0-index)
   * @param playerID the corresponding ID of this game card
   */
  public GameCardPanel(ReadonlyPawnsBoardModel pawnsBoardModel, int cardIdx, int playerID) {
    super();
    if (pawnsBoardModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.playerID = playerID;
    this.pawnsBoardModel = pawnsBoardModel;
    this.cardIdx = cardIdx;

    this.setOpaque(false);
    setPreferredSize(new Dimension(120, 200));

    // text panel for name, cost, value
    JPanel cardInfo = new JPanel();
    cardInfo.setLayout(new BoxLayout(cardInfo, BoxLayout.Y_AXIS));
    cardInfo.setOpaque(false);

    ReadOnlyGameCard card = pawnsBoardModel.getHand(playerID).get(cardIdx);
    JLabel name = new JLabel(card.getName());
    JLabel cost = new JLabel("Cost: " + card.getCost());
    JLabel value = new JLabel("Value: " + card.getValue());

    cardInfo.add(name);
    cardInfo.add(cost);
    cardInfo.add(value);

    // grid panel for influence grid
    influenceGrid = new JPanel(new GridLayout(5, 5));
    influenceGrid.setPreferredSize(new Dimension(100, 100));
    influenceGrid.setBackground(Color.DARK_GRAY);
    drawGrid();

    add(cardInfo, BorderLayout.NORTH);
    add(influenceGrid, BorderLayout.CENTER);
    setBackground(pawnsBoardModel.getPlayerColor(playerID));
    setBorder(BorderFactory.createLineBorder(Color.black));
  }

  /**
   * Draws the influence grid of this game card. Sets the color of the center cell (2,2) of the
   * influence grid to be orange. Sets the color of the influenced cells to be cyan. Sets the color
   * of unaffected cells to be gray.
   */
  private void drawGrid() {
    ReadOnlyGameCard card = pawnsBoardModel.getHand(playerID).get(cardIdx);
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        JPanel cell = new JPanel();
        cell.setBorder(BorderFactory.createLineBorder(Color.black));
        if (row == 2 && col == 2) {
          cell.setBackground(Color.orange);
        } else if (card.isCellInfluencedAt(row, col)) {
          cell.setBackground(Color.CYAN);
        } else {
          cell.setBackground(Color.DARK_GRAY);
        }
        influenceGrid.add(cell);
      }
    }
    influenceGrid.revalidate();
  }

  /**
   * Returns the corresponding card index of this game card.
   * @return card index (0-based)
   */
  public int getIndexID() {
    return cardIdx;
  }
}
