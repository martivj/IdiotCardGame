package idiot.game;

import java.util.List;

import idiot.game.elements.Card;
import idiot.game.elements.CardDeck;
import idiot.game.elements.CardHand;
import idiot.game.elements.CardPile;
import idiot.game.elements.CardStack;

public class AIPlayer extends Player {

    /* constructor */

    public AIPlayer(Game game, CardDeck deck, CardPile mainPile, CardPile discardPile, CardHand hand,
            CardStack... stacks) {
        super(game, deck, mainPile, discardPile, hand, stacks);
    }

    /* basic AI logic */

    @Override
    public void beginTurn() {
        super.beginTurn();

        // make the best legal move until there are none left
        while (this.game.getActivePlayer() == this) {
            List<Card> legalMoves = this.getLegalMoves();
            if (legalMoves.isEmpty()) {
                return;
            }
            this.game.playAIMove(this.getLegalMoves().get(0));
        }
    }
}