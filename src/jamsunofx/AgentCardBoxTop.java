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
public class AgentCardBoxTop extends VBox {

    ObservableList<CardImageView> userCards = FXCollections.observableArrayList();
    HBox cardsHBox = new HBox();
    HBox imageBox = new HBox();
    Player player;
    StackPane dealerPane;

    public AgentCardBoxTop(Player player) {

        this.player = player;
        Label labelName = new Label(player.getName());
        Image image = new Image("images/avatar/avatar2.png");
        ImageView imageView = new ImageView(image);
        imageBox.getChildren().addAll(labelName, imageView);
        imageBox.setAlignment(Pos.CENTER);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        this.getChildren().addAll(imageBox, cardsHBox);
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
                cardsHBox.getChildren().clear();
                cardsHBox.getChildren().addAll(userCards);
                break;
            }
        }
    }

    public void setCards() {
        for (Card card : this.player.getPlayerCards()) {

            Image image = new Image("images/UNO-Back_1.png");
            CardImageView cardImageView = new CardImageView(image, card);
            cardImageView.setFitWidth(68);
            cardImageView.setFitHeight(98);
            this.userCards.add(cardImageView);
        }

        cardsHBox.getChildren().clear();
        cardsHBox.getChildren().addAll(userCards);
        cardsHBox.setAlignment(Pos.CENTER);
    }

    public void addCard(Card card) {
        Image image = new Image("images/UNO-Back_1.png");
        CardImageView cardImageView = new CardImageView(image, card);
        cardImageView.setFitWidth(68);
        cardImageView.setFitHeight(98);
        this.userCards.add(cardImageView);
        cardsHBox.getChildren().clear();
        cardsHBox.getChildren().addAll(userCards);
    }
}
