
package idiot.game.elements;

import java.util.Collections;

public class CardDeck extends CardContainer {

    /* constructor */

    public CardDeck() {
        
        // max count 52 for card decks
        super(52);

        // 13 cards of each suit
        fillDeck(13);
    }

    /* fill deck with cards of all suits */

    public void fillDeck(int n) {
        // suits to loop through
        char[] suits = {'S', 'H', 'D', 'C'};

        // check that we have enough space for the cards
        if (n < 0 || suits.length * n > super.getMaxCardCount())
        throw new IllegalArgumentException("The amount of cards in a deck must be in the range (0, 52).");

        // loop through each suit and add cards with faces 1 through n
        for (char c : suits) {
            for (int i = 0; i < n; i++) {
                Card newCard = new Card(c, i + 1);
                super.addCard(newCard);
            }
        }
    }

    /* shuffle the deck */

    public void randomShuffle() {
        Collections.shuffle(super.container);
    }

    /* deal cards from the top of the deck */

    public void deal(CardContainer target, int n) {
        
        // loop through n times, moving the top card from deck to target
        for (int i = 0; i < n; i++) {
            super.moveCard(super.getTopCard(), target);
        }

    }

    /* clear deck */

    public void clear() {
        super.container.clear();
    }
}
