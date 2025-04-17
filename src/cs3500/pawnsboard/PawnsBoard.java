package cs3500.pawnsboard;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.model.PawnsBoardModelTextual;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.view.EnhancedTextualView;
import cs3500.pawnsboard.view.QueensBloodTextualView;

/**
 * Represents a PawnsBoard game.
 */
public class PawnsBoard {
  /**
   * Runs the PawnsBoardGame given the arguments.Initialize a model with a board size of 3 rows by
   * 5 columns. Reads a deck configuration file and creates a deck of card for player 1 and player
   * 2. Starts the game with the two decks of card and hand size of 5.
   * @param args an array of string with the arguments entered by the user
   */
  public static void main(String[] args) throws IOException {
    DeckConfiguration deckConfig = new PawnsBoardDeckConfig();
    QueensBlood model = new PawnsBoardModelTextual(5, 3, new Random(), deckConfig);
    QueensBloodTextualView view = new EnhancedTextualView(model);
    Appendable gameLog = new StringBuffer();
    File file = null;
    String path = "docs" + File.separator + "enhancedGameDeck.config";
    File config = new File(path);
    List<GameCard> p1Deck = deckConfig.loadDeckConfig(new FileReader(config));
    List<GameCard> p2Deck = deckConfig.loadDeckConfig(new FileReader(config));
    model.startGame(p1Deck, p2Deck, 5, false);

    System.out.println("Current Player: " + model.getCurrentPlayer());
    System.out.println("Current Player Hand: " + model.getHand(model.getCurrentPlayerID()));
    model.placeCardInPosition(4, 1, 0); // player 1 placed mandragora
    view.render(gameLog);

    model.placeCardInPosition(0, 1, 4); // player 2 placed security
    view.render(gameLog);

    model.pass();
    model.pass();

    System.out.print(gameLog);
    System.out.println("Game Over: " + model.isGameOver());
    System.out.println("Winner: " + model.getWinner());
    System.out.println("Winning Score: " + model.getWinningScore());
  }
}

