package idiot.gui;

import java.io.IOException;
import java.util.Optional;

import idiot.game.AIPlayer;
import idiot.game.Game;
import idiot.game.elements.CardContainer;
import idiot.game.elements.CardDeck;
import idiot.util.GuiHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class GameController {

    /* fxml elements */

    @FXML private AnchorPane root;
    @FXML private TilePane playerTurnPane, gameOverPane;
    @FXML private Label discardPileCounter, mainPileCounter, deckCounter, player1WinMsg, player2WinMsg;
    @FXML private Button startBtn, playBtn, drawPileBtn, endTurnBtn;
    @FXML private StackPane discardPile, mainPile, deck, player1Stack1, player1Stack2, player1Stack3, player2Stack1, player2Stack2, player2Stack3;
    @FXML private HBox player1Hand, player2Hand;

    /* important gui elements */

    private Node[] guiElements = new Node[11];

    /* connection to game */

    private Game game;

    /* initializing the game page */

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

        // set status of buttons and labels
        this.disableButtons(true);
        this.discardPileCounter.setVisible(false);
        this.mainPileCounter.setVisible(false);
        this.deckCounter.setVisible(false);
    }

    /* handlers for each button */

    @FXML
    void handleStartAction(ActionEvent event) {

        // enable buttons for in-game actions
        this.disableButtons(false);

        // disable start button
        this.startBtn.setDisable(true);

        // display player turn pane and hide game over labels
        this.playerTurnPane.setVisible(true);
        this.gameOverPane.setVisible(false);
        this.player1WinMsg.setVisible(false);
        this.player2WinMsg.setVisible(false);

        // make new game
        this.game = new Game(this);
        this.game.start();

        // make card counter labels visible
        this.discardPileCounter.setVisible(true);
        this.mainPileCounter.setVisible(true);
        this.deckCounter.setVisible(true);

        // save state and update gui
        this.game.updateState();
    }

    @FXML
    void handleQuitAction(ActionEvent event) {

        // skip confirmation if the game is already over
        if (this.game == null || (this.game.isOver() && this.game.isSaved())) {
            this.quitToMenu();
            return;
        }
        
        // check if the player really wants to exit
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This game will not be saved, \nare you sure you want to exit?");
        alert.setHeaderText(null);
        alert.setTitle("Exit confirmation");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                this.quitToMenu();
            }
        });
    }

    @FXML
    public void handlePlayAction(ActionEvent event) {

        // (try to) play the selected card
        this.game.getActivePlayer().playCard(GuiHelper.SELECTED_CARD);
        
        // save state and update gui
        this.game.updateState();
    }

    @FXML
    void handleEndTurnAction(ActionEvent event) {

        // end turn
        this.game.getActivePlayer().endTurn();
        
        // save state and update gui
        this.game.updateState();
    }

    @FXML
    void handleDrawPileAction(ActionEvent event) {

        // draw every card from the main pile
        this.game.getActivePlayer().drawFromPile();
        
        // save state and update gui
        this.game.updateState();
    }

    /* new game -> initialize gui */

    public void initializeGuiDeck(CardDeck gameDeck) {
        GuiHelper.initializeGuiDeck(gameDeck, this.deck);
    }

    /* state changed -> updating gui based on the given game elements and the list of legal moves */

    public void handleUpdateState(CardContainer[] gameElements) {

        // update labels
        this.discardPileCounter.setText("Cards: " + gameElements[2].getCardCount());
        this.mainPileCounter.setText("Cards: " + gameElements[1].getCardCount());
        this.deckCounter.setText("Cards: " + gameElements[0].getCardCount());

        // display all cards in correct positions on screen
        GuiHelper.updateGui(gameElements, this.guiElements, this.game.getActivePlayer().getLegalMoves(), true);

    }

    /* player switch -> disable/enable buttons and update label */

    public void handlePlayerSwitch() {

        int playerIndex;
        
        // AI player -> disable buttons for in-game actions
        if (this.game.getActivePlayer() instanceof AIPlayer){
            //this.disableButtons(true);
            playerIndex = 2;
        }

        // enable buttons for in-game actions
        else {
            this.disableButtons(false);
            playerIndex = 1;
        }
        
        // change label text for whose turn it is
        String formatString = "Player %s's";
        ((Label) this.playerTurnPane.getChildren().get(0)).setText(String.format(formatString, playerIndex));
    }

    /* game over -> change gui to "game over" screen */

    public void handleGameOver() {

        // display who won
        if (this.game.getActivePlayer() instanceof AIPlayer)
        this.player2WinMsg.setVisible(true);
        else this.player1WinMsg.setVisible(true);

        // display game over
        this.playerTurnPane.setVisible(false);
        this.gameOverPane.setVisible(true);
        
        // disable buttons for in-game actions
        this.disableButtons(true);

        // enable start button for new game
        this.startBtn.setDisable(false);

        // update the final game state
        this.game.updateState();

        // saving the game replay
        TextInputDialog dialog = new TextInputDialog();
        dialog.getEditor().setPromptText("name-of-replay");
        dialog.setTitle("Save replay file");
        dialog.setHeaderText(null);
        dialog.setContentText("To save the replay, please provide \na replay a name and press OK:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            this.game.saveReplay(result.get());
        }
       
    }

    /* helping method: disable or enable buttons related to in-game actions */

    private void disableButtons(boolean isDisabled) {
        this.playBtn.setDisable(isDisabled);
        this.endTurnBtn.setDisable(isDisabled);
        this.drawPileBtn.setDisable(isDisabled);
    }

    /* helping method: load the main menu screen as the new scene */

    private void quitToMenu() {
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
}
