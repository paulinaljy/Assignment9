package cs3500.pawnsboard.view;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import cs3500.pawnsboard.model.ReadonlyPawnsBoardModel;

/**
 * Represents a PawnsBoardFrame that has behaviors, including making the game board and player's
 * hand panel visible, refreshing the game state, and responding to user key press events that
 * allow players to place, pass, or quit the game.
 */
public class PawnsBoardFrame extends JFrame implements PawnsBoardView {
  private final ReadonlyPawnsBoardModel pawnsBoardModel;
  private final PawnsBoardPanel boardPanel;
  private final PlayersHandPanel playersHandPanel;

  /**
   * Initializes a PawnsBoardFrame with a read only pawns board model and player ID.
   * @param pawnsBoardModel read only model in the game
   * @param playerID player id that corresponds to the player
   */
  public PawnsBoardFrame(ReadonlyPawnsBoardModel pawnsBoardModel, int playerID) {
    super("Player " + playerID);
    this.pawnsBoardModel = pawnsBoardModel;
    setSize((pawnsBoardModel.getWidth() + 2) * 100,
            (pawnsBoardModel.getHeight() + 2) * 100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    boardPanel = new PawnsBoardPanel(pawnsBoardModel);
    playersHandPanel = new PlayersHandPanel(pawnsBoardModel, playerID);
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(2, 1));
    panel.add(boardPanel);
    panel.add(playersHandPanel);
    this.add(panel);

    setFocusable(true);
  }

  @Override
  public void refresh() {
    playersHandPanel.refreshHand();
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void subscribe(ViewActions observer) {
    this.boardPanel.subscribe(observer);
    this.playersHandPanel.subscribe(observer);
    this.addKeyListener(new KeyListener() {
      /**
       * If the key "q" is pressed, notifies the observer (PawnsBoardPlayerController) to quit the
       * game. If the enter key is pressed, notifies the observer (PawnsBoardPlayerController) to
       * place the selected card in the selected board cell. If the space key is pressed, notifies
       * the observer (PawnsBoardPlayerController) to pass.
       * @param e the event to be processed
       */
      @Override
      public void keyPressed(KeyEvent e) {
        if (!observer.isViewEnabled()) { // machine player
          return;
        }
        switch (e.getKeyCode()) {
          case KeyEvent.VK_ENTER: // confirm move
            observer.placeCard();
            break;

          case KeyEvent.VK_SPACE: // pass move
            observer.pass();
            break;

          default:
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        //nothing when key released
      }

      @Override
      public void keyTyped(KeyEvent e) {
        //nothing when typed
      }
    });
  }

  @Override
  public void reset() {
    boardPanel.reset();
  }

  @Override
  public void displayMessage(String message, String title) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void displayGameOver() {
    JFrame gameOverFrame = new GameOverFrame(pawnsBoardModel);
    gameOverFrame.setVisible(true);
    gameOverFrame.setFocusable(true);
  }
}

