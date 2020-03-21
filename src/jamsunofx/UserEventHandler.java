package jamsunofx;

import controller.Round;
import controller.utils;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Card;
import model.CardColour;

public class UserEventHandler implements EventHandler<MouseEvent> {

    Round round;
    UserCardBox userCardBox;
    CardBoxCenter cardBoxCenter;
    BooleanValue runAction;
    BooleanValue cardPlayed;
    BooleanValue userOption;
    BooleanValue wildOption;
    DrawPileEventHandler drawPileHandler;

    public UserEventHandler(Round round, UserCardBox userCardBox, CardBoxCenter cardBoxCenter, BooleanValue runAction, BooleanValue cardPlayed, BooleanValue userOption, BooleanValue wildOption, DrawPileEventHandler drawPileHandler) {
        this.round = round;
        this.userCardBox = userCardBox;
        this.cardBoxCenter = cardBoxCenter;
        this.runAction = runAction;
        this.cardPlayed = cardPlayed;
        this.userOption = userOption;
        this.wildOption = wildOption;
        this.drawPileHandler = drawPileHandler;
        
    }

    @Override
    public void handle(MouseEvent event) {

        try {
            final CardImageView selectedCard = (CardImageView) event.getSource();
            Card card = selectedCard.getCard();
            utils.cardSelectionIsValid(card, round.showDrawCard(), round.getWildColour());
            for (ImageView imageView : userCardBox.userCards) {
                imageView.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
            if (card.getColour() == CardColour.WILD || card.getColour() == CardColour.WILD_FOUR) {

                userCardBox.askWildColour(round, userCardBox, cardBoxCenter, card, runAction, cardPlayed, wildOption);
//                        System.out.println("the colour selected is: " + round.getWildColour().toString());

            } else {
                Platform.runLater(() -> {
                    userCardBox.player.play(card, round.getDiscardPile());
                    userCardBox.removeCard(card);
                    cardBoxCenter.addCardToDiscardPile(new CardImageView(card));
                });
                runAction.setValue(false);
                cardPlayed.setValue(true);
                cardBoxCenter.drawPilePane.removeEventHandler(MouseEvent.MOUSE_CLICKED, drawPileHandler);
//                        System.out.println("I played: " + selectedCard.getCard().toString());
            }
                       
            cardBoxCenter.drawPileRemoveEventHandler(drawPileHandler);
            
        } catch (IOException E) {
            System.out.println(E.getMessage());
            System.out.println("Option invalid, please select another card");
            runAction.setValue(true);
        }
    }
}
