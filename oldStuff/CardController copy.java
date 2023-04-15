package idiot.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CardController {

    @FXML
    private Pane root;
    @FXML
    private ImageView suitSymbol1;
    @FXML
    private ImageView suitSymbol2;
    @FXML
    private ImageView suitSymbol3;
    @FXML
    private ImageView cardBack;
    @FXML
    private Label faceValue1;
    @FXML
    private Label faceValue2;
    @FXML
    public void handleOnClickAction(ActionEvent event) {
        System.out.println("Card (X"+faceValue1.getText()+")");
    }

    public void setCardFront(char suit, int face) {

        String suitString;

        switch (suit) {
            case 'S':
                suitString = "spades";
                break;
            case 'H':
                suitString = "hearts";
                break;
            case 'D':
                suitString = "diamonds";
                break;
            case 'C':
                suitString = "clubs";
                break;
            default:
                throw new IllegalArgumentException("Suit must be one of the characters 'S', 'H', 'D' or 'C'.");
        }

        String imgurl = "/idiot/images/"+suitString+".png";

        this.suitSymbol1.setImage(new Image(imgurl));
        this.suitSymbol2.setImage(new Image(imgurl));
        this.suitSymbol3.setImage(new Image(imgurl));

        String faceString;
        
        switch (face) {
            case 1:
                faceString = "A";
                break;
            case 11:
                faceString = "J";
                break;
            case 12:
                faceString = "Q";
                break;
            case 13:
                faceString = "K";
                break;
            default:
                if (face < 1 || face > 13) 
                throw new IllegalArgumentException("Face value cannot be less than 1 or larger than 13.");
        
                faceString = Integer.toString(face);
                break;
        }

        this.faceValue1.setText(faceString);
        this.faceValue2.setText(faceString);
    }

    public void setCardBack(boolean isVisible) {
        this.cardBack.setVisible(isVisible);
    }

    public Pane getRoot() {
        return root;
    }

}
