package cs3500.pawnsboard.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a GameOverFrame with behaviors, including drawing the game over message with the
 * winner and winning score and exiting the game.
 */
public class GameOverFrame extends JFrame {

  /**
   * Constructs a game over frame given the model.
   *
   * @param pawnsBoardModel model
   */
  public GameOverFrame(ReadonlyPawnsBoardModel pawnsBoardModel) {
    super("Game Over");
    if (pawnsBoardModel == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    setSize(125, 125);
    setLocation(600, 200);

    JLabel gameOverLabel = new JLabel("Game Over!");
    String winner = "Winner: ";
    if (pawnsBoardModel.getWinner() == null) {
      winner += "Tie!";
    } else if (pawnsBoardModel.getWinner().getColor().equals(Color.red)) {
      winner += "Player 1";
    } else {
      winner += "Player 2";
    }
    JLabel winnerLabel = new JLabel(winner);

    //getting winner's score:
    int score = pawnsBoardModel.getWinningScore();
    JLabel winScoreLabel = new JLabel("Winning Score: " + score);

    JButton okButton = new JButton("OK");
    okButton.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
    });

    setLayout(new FlowLayout());
    this.getContentPane().add(gameOverLabel);
    this.getContentPane().add(winnerLabel);
    this.getContentPane().add(winScoreLabel);
    this.getContentPane().add(okButton);

    this.setVisible(true);
    this.setFocusable(true);
  }
}
