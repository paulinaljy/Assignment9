package cs3500.pawnsboard;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import cs3500.pawnsboard.controller.DeckConfiguration;
import cs3500.pawnsboard.controller.PawnsBoardDeckConfig;
import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.PawnsBoardModelTextual;
import cs3500.pawnsboard.model.QueensBlood;
import cs3500.pawnsboard.view.EnhancedTextualView;
import cs3500.pawnsboard.view.QueensBloodTextualView;

/**
 * Represents a textual representation of a pawns board game that shows the effect of enhancing and
 * devaluing empty and pawns cells.
 */
public class EnhDevEmpty {
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

    view.render(gameLog); // initial board

    model.placeCardInPosition(3, 1, 0); // player 1 placed crab
    view.render(gameLog);

    model.pass();

    model.placeCardInPosition(0, 1, 1); // player 1 placed security
    view.render(gameLog);
    System.out.print(gameLog);
  }
}
