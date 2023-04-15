
package idiot.game;

import idiot.game.elements.Card;

public interface OldCardContainer {
    public int getMaxCardCount();
    public int getCardCount();
    public Card getCard(int n);
}
