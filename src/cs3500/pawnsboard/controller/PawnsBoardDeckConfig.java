package cs3500.pawnsboard.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import cs3500.pawnsboard.model.GameCard;
import cs3500.pawnsboard.model.Position;

/**
 * Represents a PawnsBoardDeckConfig that loads the deck configuration based on the given file
 * reader.
 */
public class PawnsBoardDeckConfig implements DeckConfiguration {

  @Override
  public List<GameCard> loadDeckConfig(Readable readable) {
    List<GameCard> deck = new ArrayList<>();
    Scanner scan = new Scanner(readable);
    HashMap<GameCard, Integer> cardCount = new HashMap<GameCard, Integer>();
    while (scan.hasNext()) {
      String name = scan.next();
      GameCard card = readCard(scan, name);
      if (cardCount.containsKey(card)) {
        Integer count = cardCount.get(card);
        if (count == 2) {
          throw new IllegalStateException("Cannot have more than 2 of the same card in the deck");
        } else {
          count += 1;
          cardCount.put(card, count);
        }
      } else {
        cardCount.put(card, 1);
      }
      deck.add(card);
    }
    return deck;
  }

  /**
   * Reads the file and returns the card with the given name, read cost, value, and influences.
   * The influences are a list of positions relative to the position of the card (2,2).
   * For example, if cell (2,1) is influenced, the added Position would be (0,-1), indicating there
   * is an influenced cell in the same row but one column to the left.
   *
   * @param scan the Scanner that reads the next value
   * @param name the name of the card that has already been read
   * @return a GameCard with a name, cost, value, and list of positions (influenced cells)
   */
  private GameCard readCard(Scanner scan, String name) {
    int cost = 0;
    int value = 0;
    if (scan.hasNext()) {
      cost = scan.nextInt();
    }
    if (scan.hasNext()) {
      value = scan.nextInt();
    }
    ArrayList<Position> influences = new ArrayList<>();
    for (int r = 0; r < 5 && scan.hasNext(); r++) {
      String row = scan.next();
      for (int i = 0; i < 5; i++) {
        Character cell = row.charAt(i);
        if (cell.equals('I')) {
          try {
            // relative reference position from card (2,2)
            influences.add(new Position(r - 2, i - 2));
          } catch (IndexOutOfBoundsException ignored) {

          }
        }
      }
    }
    return new GameCard(name, GameCard.valueToCost(cost), value, influences);
  }
}

