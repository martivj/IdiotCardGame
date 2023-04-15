
package idiot.game.elements;

public class CardPile extends CardContainer {

    /* constructor */

    public CardPile() {
        
        // max count 52 for card decks
        super(52);

    }

    /* flush all cards in the pile into another container */

    public void flushCards(CardContainer newContainer) {

        // adding the cards first
        for (int i = 0; i < super.getCardCount(); i++)
        newContainer.addCard(super.getCard(i));

        // then clearing out the pile
        super.container.clear();
    }
}
