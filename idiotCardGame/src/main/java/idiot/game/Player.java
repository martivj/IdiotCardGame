package idiot.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import idiot.game.elements.Card;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.game.elements.CardHand;
import idiot.game.elements.CardPile;
import idiot.game.elements.CardStack;

public class Player {

    /* the game in which the player takes part in */

    protected Game game;

    /* game elements */

    protected CardDeck deck;
    protected CardPile mainPile;
    protected CardPile discardPile;
    protected CardHand hand;
    protected List<CardStack> stacks;

    /* tracks every active turn */

    private int movesPlayed = 0;    

    /* constructor */

    public Player(Game game, CardDeck deck, CardPile mainPile, CardPile discardPile, CardHand hand, CardStack ... stacks) {
        
        this.game = game;

        this.deck = deck;
        this.mainPile = mainPile;
        this.discardPile = discardPile;
        this.hand = hand;
        this.stacks = Arrays.asList(stacks);

    }

    /* activate the player's turn */

    public void beginTurn() {

        // no legal moves -> draw all main pile cards and end turn
        if (this.getLegalMoves().isEmpty()) {
            this.drawFromPile();
            return;
        }
    }
    
    /* available in-game actions */

    public void endTurn() {
        
        // reset move counter for next turn
        this.movesPlayed = 0;

        // switch turn over to the other player
        this.game.switchPlayer();

    }

    public void drawFromDeck() {

        // deck empty or hand full -> either do nothing or end turn
        if (this.deck.isEmpty() || this.hand.getCardCount() >= 3) {
            
            if (this.getLegalMoves().isEmpty())
            this.endTurn();
            
            return;
        }
        
        // draw the top card from the deck
        System.out.println("Drawn from deck: " + this.deck.getTopCard());
        this.deck.deal(this.hand, 1);
        this.hand.sortHand();

        if (this.getLegalMoves().isEmpty())
        this.endTurn();
        
    }

    public void drawFromPile() {

        // empty main pile -> do nothing
        if (this.mainPile.isEmpty())
        return;

        // draw all cards from the main pile to hand
        System.out.println("Drawn from pile: " + this.mainPile);
        this.mainPile.flushCards(this.hand);
        this.hand.sortHand();
        this.endTurn();

    }

    public void playCard(Card card) {

        // illegal move -> do nothing
        if (!this.getLegalMoves().contains(card))
        return;

        // save main pile values
        int oldValue; 
        int newValue = card.getFace();
        
        // face value of 0 when main pile is empty
        if (this.mainPile.isEmpty())
        oldValue = 0;
        
        // otherwise face value of the top card in the main pile
        else oldValue = this.mainPile.getTopCard().getFace();

        // legal move -> play the card to the main pile
        card.getOwner().moveCard(card, this.mainPile);
        System.out.println("Played card: " + card);

        // update number of moves played
        this.movesPlayed += 1;

        // adjust the value of aces
        if (oldValue == 1) oldValue = 14;
        if (newValue == 1) newValue = 14;

        // run the aftermath
        this.runAftermath(oldValue, newValue);

    }

    /* checks for special cases after a card is played */

    private void runAftermath(int oldValue, int newValue) {

        // special cards have face value 2 or 10
        boolean isSpecial = newValue == 2 || newValue == 10;

        // special cards reset the move counter
        if (this.isFourStacked() || isSpecial)
        this.movesPlayed = 0;

        // special cases that discard the main pile
        if (this.isFourStacked() || newValue == 10) {
            this.mainPile.flushCards(this.discardPile);
        }

        // value too low -> draw all main pile cards to hand
        if (!isSpecial && newValue < oldValue)
        this.drawFromPile();

        // checking if the move was winning
        if (this.hand.isEmpty() && this.stacks.stream().allMatch(CardStack::isEmpty)) {
            this.game.setGameOver(this);
            return;
        }
        
        // draw from deck to fill up hand
        if (!this.game.isOver())
        this.drawFromDeck();

    }

    /* get all legal moves in the current state of the game */

    public List<Card> getLegalMoves() {
        
        List<Card> legalMoves = new ArrayList<>();
        
        // check for legal hand moves
        for (Card card : this.hand) {
            if (this.isLegalMove(card))
            legalMoves.add(card);
        }

        // check for legal stack moves
        for (CardStack stack : this.stacks) {
            for (Card card : stack) {
                if (this.isLegalMove(card))
                legalMoves.add(card);
            }
        }

        // check for legal deck moves
        for (Card card : this.deck) {
            if (this.isLegalMove(card))
            legalMoves.add(card);
        }

        return legalMoves;
    }

    /* checks if four cards of the same face are stacked on the top of the main pile */

    private boolean isFourStacked() {
        
        // find the number of cards in the main pile
        int mainPileCount = this.mainPile.getCardCount();

        // not enough cards for a 4-stack
        if (mainPileCount < 4)
        return false;

        // find the face value of the top card
        int mainPileValue = this.mainPile.getTopCard().getFace();

        // check if the 3-stack below the top card have the same face value
        for (int i = mainPileCount - 4; i < mainPileCount - 1; i++) {
            if (this.mainPile.getCard(i).getFace() != mainPileValue)
            return false;
        }

        return true;
    }

    /* checks if a card can be played or not */

    private boolean isLegalMove(Card card) {
        
        // card is null
        if (card == null)
        return false;

        // face value of the main pile
        int mainPileValue; 
        
        // face value of 0 when main pile is empty
        if (this.mainPile.isEmpty())
        mainPileValue = 0;
        
        // face value of the top card in the main pile
        else mainPileValue = this.mainPile.getTopCard().getFace();

        // retrieve the owner of the given card
        CardContainer owner = card.getOwner();

        // card from deck as the first move
        if (owner == this.deck && this.movesPlayed == 0) {

            // top card can be used if the deck is not empty
            if (!this.deck.isEmpty() && card.isTopCard())
            return true;
        
        }

        // card from hand as the first move
        else if (owner == this.hand && this.movesPlayed == 0) {

            // some cards can always be used
            if (card.getFace() == 1 || card.getFace() == 2 || card.getFace() == 10)
            return true;

            // the face value of the card is high enough
            else if (mainPileValue != 1 && card.getFace() >= mainPileValue)
            return true;
        
        }

        // card from hand when it is not the first move
        else if (owner == this.hand && this.movesPlayed > 0) {

            // the face value matches the face of the top card
            if (card.getFace() == mainPileValue)
            return true;

        }

        // top card from stack when the hand is empty as the first move
        else if (this.stacks.contains(owner) && owner.getCardCount() == 2 && card.isTopCard() && this.hand.isEmpty() && this.movesPlayed == 0) {
            
            // some cards can always be used
            if (card.getFace() == 1 || card.getFace() == 2 || card.getFace() == 10)
            return true;

            // the face value of the card is high enough
            else if (mainPileValue != 1 && card.getFace() >= mainPileValue)
            return true;

        }

        // top card from stack when the hand is empty and it is not the first move
        else if (this.stacks.contains(owner) && owner.getCardCount() == 2 && card.isTopCard() && this.hand.isEmpty() && this.movesPlayed > 0) {

            // the face value matches the face of the top card
            if (card.getFace() == mainPileValue)
            return true;

        }

        // bottom card in stack when the hand is empty as the first move
        else if (this.stacks.contains(owner) && owner.getCardCount() == 1 && this.hand.isEmpty() && this.movesPlayed == 0) {
            
            // the top layer of the stacks must be removed
            if (this.stacks.stream().allMatch(x -> x.getCardCount() < 2))
            return true;

        }

        return false;
    
    }
    
}
