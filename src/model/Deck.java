package model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    private final ArrayList<Card> deck = new ArrayList<>();

    public Deck() {
        for(int i = 0; i < 2; i++) {

            for (CardColour colour : CardColour.values()) {
                for (CardValue value : CardValue.values()) {
                    if (colour == CardColour.WILD || colour == CardColour.WILD_FOUR) { }
                    else {
                        if(i==1 && value == CardValue.ZERO) {}
                        else{
                            deck.add(new Card(colour, value));
                        }
                    }
                }
            }
        }


        for(int i = 0; i < 4; i++) {
            deck.add(new Card(CardColour.WILD,CardValue.ONE));
            deck.add(new Card(CardColour.WILD_FOUR,CardValue.ONE));
        }
    }

    public Card getCard() {
        return deck.get(0);
    }

    public Card getCard(int index) {
        return deck.get(index);
    }

    public void shuffle() {
        Collections.shuffle(this.deck);
    }

    public Card dealCard() {
        
        return deck.remove(0);
    }

    public void clear() {
        deck.clear();
    }

    public ArrayList getDeck() {
        return deck;
    }
    
}
