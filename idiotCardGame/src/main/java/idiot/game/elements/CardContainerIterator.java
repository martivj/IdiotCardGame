
package idiot.game.elements;

import java.util.Iterator;

public class CardContainerIterator implements Iterator<Card> {
    
    /* state attributes */

    private CardContainer container;
    private int currentIndex;

    /* constructor */

    public CardContainerIterator (CardContainer container) {
        this.container = container;
        this.currentIndex = 0;
    }

    /* the required iterator methods */

    public boolean hasNext() {

        // more cards left
        if (this.currentIndex < this.container.getCardCount())
        return true;
        
        // empty
        return false;

    }

    public Card next() {
        Card currentCard = this.container.getCard(this.currentIndex);
        this.currentIndex += 1;
        return currentCard;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
