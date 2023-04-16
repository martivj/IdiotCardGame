
package idiot.game.elements;

public class Card {
    
    /* state attributes */

    private final char suit; // card suit
    private final int face; // card face value
    private CardContainer owner; // the container which contains this card

    /* constructor */

    public Card(char suit, int face) {

        // set the suit if it is valid
        switch (suit) {
            case 'S':
            case 'H':
            case 'D':
            case 'C':
                this.suit = suit;
                break;
            default:
                throw new IllegalArgumentException("Suit must be one of the characters 'S', 'H', 'D' or 'C'.");
        }
        
        // set the face if it is valid
        if (face < 1 || face > 13) 
        throw new IllegalArgumentException("Face value cannot be less than 1 or larger than 13.");

        this.face = face;
        
    }

    /* getters */

    public char getSuit() {
        return this.suit;
    }

    public int getFace() {
        return this.face;
    }

    public CardContainer getOwner() {
        return this.owner;
    }

    /* checking methods */

    public boolean isTopCard() {
        return this == this.owner.getTopCard();
    }

    /* setters */

    public void setOwner(CardContainer newOwner) {
        this.owner = newOwner;
    }

    /* text representation */

    @Override
    public String toString() {
        return String.valueOf(suit) + String.valueOf(face);
    }

}
