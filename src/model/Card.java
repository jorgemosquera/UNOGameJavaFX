package model;

public class Card {
    
    private CardColour colour;
    private CardValue value;

    public Card(CardColour colour, CardValue value) {
        if(colour == CardColour.WILD || colour == CardColour.WILD_FOUR){
            this.colour = colour;
            this.value = CardValue.ONE;
        }
        else {
            this.colour = colour;
            this.value = value;
        }
    }

    @Override
    public String toString() {
        if (this.colour == CardColour.WILD || this.colour == CardColour.WILD_FOUR)
            return colour.toString();
        else
            return colour.toString() + " " + value.toString();
    }

    public CardColour getColour() {
        return colour;
    }

    public CardValue getValue() {
        return value;
    }
    
    public int getCardPoints() {
        if (this.colour == CardColour.WILD || this.colour == CardColour.WILD_FOUR)
            return this.colour.toPoints();
        else {
            return this.value.toPoints();
        }
    }
    
    public void setColour(CardColour colour) {
        this.colour = colour;
    }

    public void setValue(CardValue value) {
        this.value = value;
    }
    
}
