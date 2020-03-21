package controller;

import java.util.Random;
import model.Player;

public class Game {
    private MyLinkedList<Player> playerList = new MyLinkedList<>();
    private Player dealer = null;

    private Round round;
    
    public MyLinkedList<Player> getPlayerList() {
        return this.playerList;
    }

    public void addPlayer(String name, String type) {
        playerList.add(new Player(name, type));
    }

    public Player getPlayer(int index) {
        return (Player) playerList.get(index);
    }

    public String getPlayerName(int index) {
        return getPlayer(index).getName();
    }

    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < playerList.size(); i++) {
            result += playerList.get(i).toString();
        }
        return result;
    }

    public Round getRound() {
        return round;
    }

    public boolean isThereAWinner() {
        for(Player player:playerList) {
            if (player.getPoints() >= 500)
                return true;
        }
        return false;
    }

    public String getWinner() {
        for(Player player:playerList) {
            if (player.getPoints() >= 500)
                return player.getName();
        }
        return "";
    }

    public int defineDealer() {
        if(dealer == null){
            Random rand = new Random();
            int pos = rand.nextInt(this.playerList.size());
            dealer = this.getPlayer(pos);
            return pos;
        }
        else {
            return playerList.indexOf(dealer);
        }
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer){
        this.dealer = dealer;
    }

    public void setupRound() {
        round = new Round(dealer, playerList);
        round.dealCards();       
        round.createDrawPile();
        round.createDiscardPile();
        round.getPlayerList().nextNode();
        round.checkFirstDrawCard();
    }
    
}
