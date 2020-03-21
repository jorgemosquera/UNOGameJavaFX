package controller;

import java.util.Collections;
import model.Card;
import model.CardColour;
import model.CardValue;
import model.Deck;
import model.DiscardPile;
import model.DrawPile;
import model.Player;

public class Round {

    private MyLinkedList<Player> playerList;
    private Deck deck = new Deck();
    private DiscardPile discardPile;
    private DrawPile drawPile;
    private boolean continuePlaying;
    private CardColour wildColour;          // to define the Colour when a Wild card is played.

    public Round(Player dealer, MyLinkedList players) {
        playerList = players;
        playerList.setHead(dealer);
        this.deck.shuffle();
        continuePlaying = true;
    }

    public int calculatePoints(Player winner) {
        int totalPoints = 0;
        for (Player player : playerList) {
            if (player == winner) {
            } else {
                totalPoints = totalPoints + player.calculatePoints();
            }
        }
        return totalPoints;
    }

    public void setContinuePlaying(boolean continuePlaying) {
        this.continuePlaying = continuePlaying;
    }

    public boolean isContinuePlaying() {
        return continuePlaying;
    }

    public CardColour getWildColour() {
        return wildColour;
    }

    public void setWildColour(CardColour wildColour) {
        this.wildColour = wildColour;
    }

    public void dealCards() {
        //todo: Depending on the rules, 7 must be changed to number in changes of the rules.
        for (int i = 0; i < 7; i++) {
            for (Player player : playerList) {
                player.receiveCard(deck.dealCard());
            }
        }
    }

    public void createDiscardPile() {
        discardPile = new DiscardPile();
        discardPile.addCard(drawPile.drawCard(discardPile));
    }

    public void createDrawPile() {
        drawPile = new DrawPile(this.deck);
    }

    public Card showDrawCard() {
        return discardPile.peekCard();
    }

    public DiscardPile getDiscardPile() {
        return this.discardPile;
    }

    public DrawPile getDrawPile() {
        return this.drawPile;
    }

    public MyLinkedList<Player> getPlayerList() {
        return this.playerList;
    }

    public void checkFinalCard() {
        if (showDrawCard().getValue() == CardValue.DRAW_TWO) {
            getPlayerList().nextNode();
            for (int i = 0; i < 2; i++) {
                getCurrentPlayer().receiveCard(drawPile.drawCard(discardPile));
            }
        } else if (showDrawCard().getColour() == CardColour.WILD_FOUR) {
            getPlayerList().nextNode();
            for (int i = 0; i < 4; i++) {
                getCurrentPlayer().receiveCard(drawPile.drawCard(discardPile));
            }
        }
    }

    public void checkCard() {
        if (showDrawCard().getValue() == CardValue.SKIP) {
            getPlayerList().skip();
        } else if (showDrawCard().getValue() == CardValue.REVERSE) {
            getPlayerList().reverse();
            getPlayerList().nextNode();
        } else if (showDrawCard().getValue() == CardValue.DRAW_TWO) {
            getPlayerList().nextNode();
            for (int i = 0; i < 2; i++) {
                getCurrentPlayer().receiveCard(drawPile.drawCard(discardPile));
            }
            getPlayerList().nextNode();
        } else if (showDrawCard().getColour() == CardColour.WILD_FOUR) {
            getPlayerList().nextNode();
            for (int i = 0; i < 4; i++) {
                getCurrentPlayer().receiveCard(drawPile.drawCard(discardPile));
            }
            getPlayerList().nextNode();
        } else {
            getPlayerList().nextNode();
        }
    }

    public void checkFirstDrawCard() {
        if (showDrawCard().getValue() == CardValue.SKIP) {
            getPlayerList().skip();
        } else if (showDrawCard().getValue() == CardValue.REVERSE) {
            getPlayerList().reverse();
        } else if (showDrawCard().getValue() == CardValue.DRAW_TWO) {
            getPlayerList().nextNode();
            for (int i = 0; i < 2; i++) {
                getCurrentPlayer().receiveCard(drawPile.drawCard(discardPile));
            }
            getPlayerList().nextNode();
        } else if (this.showDrawCard().getColour() == CardColour.WILD) {
            getPlayerList().nextNode();
            if (this.getWildColour() == null) {
                if (getCurrentPlayer().getType().equals("user")) {
                    this.setWildColour(utils.askPlayerWildColour(getCurrentPlayer()));
                } else {
                    this.setWildColour(AgentUtils.askPlayerWildColour(getCurrentPlayer()));
                }
            }
        } else if (showDrawCard().getColour() == CardColour.WILD_FOUR) {
            //todo: este sout no debe ir aca para una aplicacion GUI
            System.out.println("Reshuffeling Discard Pile");
            drawPile.addCard(discardPile.drawCard());
            Collections.shuffle(drawPile.getDrawPile());
            discardPile.addCard(drawPile.drawCard(discardPile));
            checkFirstDrawCard();
        } else {
            getPlayerList().nextNode();
        }

    }

    public Player getCurrentPlayer() {
        return this.playerList.get(playerList.getCurrentIndex());
    }

    public void clearPlayerCards() {
        for (Player player : playerList) {
            player.getPlayerCards().clear();
        }
    }
    
    public void setCurrentPlayer(int index) {
        this.playerList.setCurrentIndex(index);
    }

}
