package idiot.game;

import idiot.game.elements.Card;
import idiot.game.elements.CardContainer;
import idiot.util.FileHelper;

public class GameState {

    /* state attributes */

    private String state;
    
    /* constructors */

    public GameState(CardContainer... containers) {
        this.state = "|";
        for (CardContainer container : containers) {
            for (Card card : container) {
                this.state += card.toString();
                if (container.iterator().hasNext())
                this.state += "-";
            }
            this.state += "|";
        }
    }
    
    public GameState(String path, int line) {
        String state = FileHelper.readLines(path).get(line);
    }

    public String getGameState() {
        return this.state;
    }

    // public 

    /* save and load functionality */

}
