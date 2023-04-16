
package idiot.game.elements;

import java.util.ArrayList;
import java.util.List;

public abstract class CardContainer implements Iterable<Card> {

    /* state attributes */

    protected List<Card> container; // list of card objects
    private int maxCardCount; // max amount of cards in the container

    /* constructor */

    protected CardContainer(int maxCardCount) {
        this.maxCardCount = maxCardCount;
        this.container = new ArrayList<>(this.maxCardCount);
    }

    /* getters */

    public int getMaxCardCount() {
        return this.maxCardCount;
    }

    public int getCardCount() {
        return this.container.size();
    }

    public Card getCard(int n) {
        
        if (n < 0 || n >= this.container.size())
        throw new IllegalArgumentException("The index must be within the set bounds given by the amount of cards.");
        
        return this.container.get(n);

    }

    public Card getTopCard() {

        if (this.isEmpty())
        throw new IllegalStateException("Cannot find the top card of an empty container");

        return this.getCard(this.getCardCount() - 1);
    }

    /* checking methods */

    public boolean isEmpty() {
        return this.container.isEmpty();
    }

    /* methods for moving cards across containers */

    public void moveCard(Card card, CardContainer newContainer) {
        newContainer.addCard(card);
        this.removeCard(card);
    }

    public void addCard(Card card) {

        if (this.getCardCount() == this.getMaxCardCount())
        throw new IllegalStateException("Cannot add card, the container is full!");
        
        this.container.add(card);
        card.setOwner(this);

    }

    public void removeCard(Card card) {

        if (this.isEmpty())
        throw new IllegalStateException("Cannot remove card, the container is empty!");

        this.container.remove(card);

    }

    /* iterator method */

    @Override
    public CardContainerIterator iterator() {
        return new CardContainerIterator(this);
    }

    /* text representation */

    @Override
    public String toString() {
        return this.container.toString();
    }
    
}
