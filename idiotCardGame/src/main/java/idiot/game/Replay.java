package idiot.game;

import java.io.IOException;
import java.util.List;

import idiot.gui.ReplayController;
import idiot.util.FileHelper;
import idiot.util.GameStateConverter;

public class Replay {

    /* keep track of game states within the replay file */

    private List<String> replay;
    private String currentState;
    private int cursor = 0;

    /* connection to gui controller */

    private ReplayController controller;

    /* constructor */

    public Replay(String path, ReplayController controller) {

        this.controller = controller;

        try {

            // get list of states from replay file
            this.replay = FileHelper.readLines(path);
            
            // empty replay file not allowed
            if (this.replay.isEmpty())
            throw new IllegalStateException("Replay is empty!");

            // set the current state at the start of the replay
            this.currentState = replay.get(this.cursor);

        } catch (IOException e) {
            System.out.println("Could not load the replay file");
        }

        System.out.println(currentState);
        System.out.println(GameStateConverter.createGameElements(currentState)[0]);

        // load initial set of cards to gui deck
        this.initializeGuiDeck();

    }

    /* methods to navigate between states in the replay file */

    public void goForward() {

        // out of bounds -> do nothing
        if (this.cursor == this.replay.size() - 1)
        return;

        this.cursor += 1;
        this.currentState = this.replay.get(this.cursor);
    }

    public void goBackward() {

        // out of bounds -> do nothing
        if (this.cursor == 0)
        return;

        this.cursor -= 1;
        this.currentState = this.replay.get(this.cursor);
    }

    public void goToStart() {
        this.cursor = 0;
        this.currentState = this.replay.get(this.cursor);
    }

    public void goToEnd() {
        this.cursor = this.replay.size() - 1;
        this.currentState = this.replay.get(this.cursor);
    }

    /* update gui */

    public void updateState() {
        this.controller.handleUpdateState(this.currentState);
    }

    /* generate label to keep track of the cursor */

    public String getMoveLabel() {
        String formatString = "State %s/%s";
        return String.format(formatString, this.cursor, this.replay.size() - 1);
    }

    /* new game -> initialize gui */

    public void initializeGuiDeck() {
        this.controller.initializeGuiDeck(this.currentState);
    }
}
