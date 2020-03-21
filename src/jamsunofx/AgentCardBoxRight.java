package jamsunofx;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Card;
import model.Player;

/**
 *
 * @author j_a_m
 */
public class AgentCardBoxRight extends HBox {

    ObservableList<CardImageView> userCards = FXCollections.observableArrayList();
    VBox cardsVBox = new VBox();
    VBox imageBox = new VBox();
    Player player;
    StackPane dealerPane;

    public AgentCardBoxRight(Player player) {

        this.player = player;
        Label labelName = new Label(player.getName());
        Image image = new Image("images/avatar/avatar3.png");
        ImageView imageView = new ImageView(image);
        imageBox.getChildren().addAll(labelName, imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        this.getChildren().addAll(imageBox, cardsVBox);
        this.setAlignment(Pos.CENTER);

    }

    public void setDealerPane() {
        dealerPane = new DealerImagePane();
        imageBox.getChildren().add(dealerPane);
    }

    public void removeDealerPane() {
        imageBox.getChildren().remove(dealerPane);
    }

    public void removeCard(Card card) {
        for (CardImageView cardImageView : userCards) {
            if (cardImageView.getCard().equals(card)) {
                userCards.remove(cardImageView);
                cardsVBox.getChildren().clear();
                cardsVBox.getChildren().addAll(userCards);
                break;
            }
        }
    }

    public void setCards() {
        for (Card card : this.player.getPlayerCards()) {

            Image image = new Image("images/UNO-Back-right.png");
            CardImageView cardImageView = new CardImageView(image, card);
            cardImageView.setFitWidth(98);
            cardImageView.setFitHeight(68);
            this.userCards.add(cardImageView);
        }

        cardsVBox.getChildren().clear();
        cardsVBox.getChildren().addAll(userCards);
        cardsVBox.setAlignment(Pos.CENTER);
    }

    public void addCard(Card card) {
        Image image = new Image("images/UNO-Back-right.png");
        CardImageView cardImageView = new CardImageView(image, card);
        cardImageView.setFitWidth(98);
        cardImageView.setFitHeight(68);
        this.userCards.add(cardImageView);
        cardsVBox.getChildren().clear();
        cardsVBox.getChildren().addAll(userCards);
    }

}
