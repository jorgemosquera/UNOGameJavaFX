package controller;

import java.util.ArrayList;
import java.util.Random;
import model.Card;
import model.CardColour;
import model.Player;

public class AgentUtils {

    public static ArrayList<Card> getPlayableCards(Player player, Card drawCard, CardColour wildColour) {
        ArrayList<Card> playCards = new ArrayList<>();
        for (Card card : player.getPlayerCards()) {
            if (drawCard.getColour() == CardColour.WILD || drawCard.getColour() == CardColour.WILD_FOUR) {
                if (card.getColour().equals(wildColour)) {
                    playCards.add(card);
                }
            } else if (card.getColour().equals(drawCard.getColour())) {
                playCards.add(card);
            } else if (card.getValue().equals(drawCard.getValue())) {
                playCards.add(card);
            } else if (card.getColour() == CardColour.WILD || card.getColour() == CardColour.WILD_FOUR) {
                playCards.add(card);
            }
        }
        return playCards;
    }
    
    public static CardColour askPlayerWildColour(Player player){
        Random random = new Random();
        switch (random.nextInt(4)){
            case 0: return CardColour.BLUE;
            case 1: return CardColour.GREEN;
            case 2: return CardColour.RED;
            case 3: return CardColour.YELLOW;
        }
        
        for(Card card : player.getPlayerCards()) {
            //TODO: to implement something here
        }
        return null;
    }
    
    public static int randomCard(ArrayList<Card> playCards) {
        Random random = new Random();
        int randomCard = random.nextInt(playCards.size());
        return randomCard;
    }
}
