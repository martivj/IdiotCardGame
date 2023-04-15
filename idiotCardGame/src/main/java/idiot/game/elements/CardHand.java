
package idiot.game.elements;

public class CardHand extends CardContainer {

    /* constructor */

    public CardHand() {
        super(52);
    }
    
    /* normal sorting */

    public void sortHand() {
        super.container.sort(new CardComparator());
    }

    /* finding the lowest card with hand comparison sorting */

    public Card getLowestCard() {

        // select the lowest card after sorting
        this.sortHand();
        return super.getCard(0);
    }

}
