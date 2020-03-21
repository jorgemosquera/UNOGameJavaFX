
package controller;

import java.io.IOException;
import java.util.Scanner;
import model.Card;
import model.CardColour;
import model.Player;

public class utils {
    
    public static void cardSelectionIsValid(Card cardSelection, Card drawCard, CardColour wildColour ) throws IOException {

        // Checks if card selected matches the colour from wildColour
        if(drawCard.getColour() == CardColour.WILD || drawCard.getColour() == CardColour.WILD_FOUR){
            if(cardSelection.getColour().equals(wildColour)) {}
            //case when player draws a another wild card
            else if (cardSelection.getColour() == CardColour.WILD || cardSelection.getColour() == CardColour.WILD_FOUR) {}
            else {
                throw new IOException("The Card is not valid");
            }
        }
        // Checks if card selected matches colour
        else if(cardSelection.getColour().equals(drawCard.getColour())) {}

        //checks if card selected matches number or symbol
        else if (cardSelection.getValue().equals(drawCard.getValue())) {}

        // player puts down a WildCard
        else if (cardSelection.getColour() == CardColour.WILD || cardSelection.getColour() == CardColour.WILD_FOUR) {}

        else throw new IOException("The Card is not valid");



//        a.	A player matches a card from his hand to the card on the top of the DISCARD pile, either by number, color or symbol.
//        b.	A player puts down a Wild card and calls the colour to be matched.

    }

    public static CardColour askPlayerWildColour(Player player) {

        int inputUser = 0;
        Scanner input = new Scanner(System.in);
        boolean continueInput = true;
        do {
            try {
                System.out.println("Player "+ player.getName() + " plays.");
                System.out.println("The Card is Wild");
                System.out.println("Please enter a Colour to play: ");
                System.out.println("1. "+ CardColour.BLUE.toString());
                System.out.println("2. "+ CardColour.GREEN.toString());
                System.out.println("3. "+ CardColour.RED.toString());
                System.out.println("4. "+ CardColour.YELLOW.toString());
                System.out.print("Your option: ");
                inputUser = input.nextInt();

                if(inputUser==0 || inputUser>4){
                    throw new Exception("Option not Valid");
                }

                switch (inputUser){
                    case 1: return CardColour.BLUE;
                    case 2: return CardColour.GREEN;
                    case 3: return CardColour.RED;
                    case 4: return CardColour.YELLOW;
                }
            }

            catch (Exception E) {
                System.out.println("Invalid Option");

            }
        } while (continueInput);

        return null;
    }

    public static boolean cardCanPlay(Card card, Card drawCard, CardColour wildColour){
        // Checks if card selected matches the colour from wildColour
        if(drawCard.getColour() == CardColour.WILD || drawCard.getColour() == CardColour.WILD_FOUR){
            if(card.getColour().equals(wildColour)) {return true;}
            //case when player draws a another wild card
            else if (card.getColour() == CardColour.WILD || card.getColour() == CardColour.WILD_FOUR) {return true;}
            else {
                return false;
            }
        }
        // Checks if card selected matches colour
        else if(card.getColour().equals(drawCard.getColour())) { return true;}

        //checks if card selected matches number or symbol
        else if (card.getValue().equals(drawCard.getValue())) {return true;}

        // player puts down a WildCard
        else if (card.getColour() == CardColour.WILD || card.getColour() == CardColour.WILD_FOUR) {return true;}

    return false;
    }
    
}
