package idiot.gui;

import java.io.IOException;

import idiot.game.Replay;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.util.FileHelper;
import idiot.util.GameStateConverter;
import idiot.util.GuiHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ReplayController {

    @FXML private AnchorPane root;
    @FXML private ChoiceBox<String> fileChooser;
    @FXML private Label moveCounterLabel, discardPileCounter, mainPileCounter, deckCounter;
    @FXML private Button quitBtn, goForwardBtn, goBackwardBtn, goToEndButton, goToStartBtn;
    @FXML private StackPane discardPile, mainPile, deck, player1Stack1, player1Stack2, player1Stack3, player2Stack1, player2Stack2, player2Stack3;
    @FXML private HBox player1Hand, player2Hand;
    
    /* gui elements */

    private Node[] guiElements = new Node[11];

    /* connection to game */

    private Replay replay;

    /* initializing the replay page */

    public void initialize() {

        // grouping the gui elements
        this.guiElements[0] = this.deck;
        this.guiElements[1] = this.mainPile;
        this.guiElements[2] = this.discardPile;
        this.guiElements[3] = this.player1Hand;
        this.guiElements[4] = this.player2Hand;
        this.guiElements[5] = this.player1Stack1;
        this.guiElements[6] = this.player1Stack2;
        this.guiElements[7] = this.player1Stack3;
        this.guiElements[8] = this.player2Stack1;
        this.guiElements[9] = this.player2Stack2;
        this.guiElements[10] = this.player2Stack3;

        // making replay files available as choices in the filechooser
        ObservableList<String> options = FXCollections.observableArrayList(FileHelper.getReplayNames());
        this.fileChooser.setItems(options);

        // set status of buttons and labels
        this.disableButtons(true);
        this.discardPileCounter.setVisible(false);
        this.mainPileCounter.setVisible(false);
        this.deckCounter.setVisible(false);
    }

    
    @FXML
    void handleLoadAction() {

        // fetching the replay name
        String path = this.fileChooser.getValue();

        if (path == null)
        return;

        // making a new replay object
        this.replay = new Replay(path, this);

        // enabling replay flow buttons 
        this.disableButtons(false);

        // make card counter labels visible
        this.discardPileCounter.setVisible(true);
        this.mainPileCounter.setVisible(true);
        this.deckCounter.setVisible(true);

        // update gui
        this.replay.updateState();

    }

    @FXML
    void handleQuitAction(ActionEvent event) throws IOException {

        // load back into main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/idiot/MainMenu.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = this.root.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleGoForwardAction() {
        
        // go forward
        this.replay.goForward();

        // update gui
        this.replay.updateState();
    }

    @FXML
    void handleGoBackwardAction() {
        
        // go backward
        this.replay.goBackward();

        // update gui
        this.replay.updateState();     
    }

    @FXML
    void handleGoToStartAction() {
        
        // go to start
        this.replay.goToStart();

        // update gui
        this.replay.updateState();
    }

    @FXML
    void handleGoToEndAction() {
        
        // go to end
        this.replay.goToEnd();

        // update gui
        this.replay.updateState();
    }

    /* new replay -> initialize gui */

    public void initializeGuiDeck(String state) {
        CardDeck gameDeck = (CardDeck) GameStateConverter.createGameElements(state)[0];
        GuiHelper.initializeGuiDeck(gameDeck, this.deck);
    }

    /* updating the gui based on the given game elements and the list of legal moves */

    public void handleUpdateState(String state) {

        // creating new elements from the game state
        CardContainer[] gameElements = GameStateConverter.createGameElements(state);

        // update labels
        this.discardPileCounter.setText("Cards: " + gameElements[2].getCardCount());
        this.mainPileCounter.setText("Cards: " + gameElements[1].getCardCount());
        this.deckCounter.setText("Cards: " + gameElements[0].getCardCount());

        // updating gui based on the new game elements
        GuiHelper.updateGui(gameElements, this.guiElements, null, true);
        this.setMoveCounter(false);

    }

    /* helping methods: disable, enable or hide buttons/labels related to traversing the replay */

    private void disableButtons(boolean isDisabled) {

        this.goForwardBtn.setDisable(isDisabled);
        this.goBackwardBtn.setDisable(isDisabled);
        this.goToEndButton.setDisable(isDisabled);
        this.goToStartBtn.setDisable(isDisabled);

        // move counter only visible when the buttons are available
        this.setMoveCounter(isDisabled);

    }

    private void setMoveCounter(boolean isHidden) {
        this.moveCounterLabel.setVisible(!isHidden);
        if (!isHidden)
        this.moveCounterLabel.setText(this.replay.getMoveLabel());
    }

    
}
