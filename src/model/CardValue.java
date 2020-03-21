package model;

public enum CardValue {
    
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, REVERSE, DRAW_TWO;
	
    @Override
    public String toString() {

            switch(this) {
                    case ZERO:		return "Zero";
                    case ONE:		return "One";
                    case TWO:		return "Two";
                    case THREE:		return "Three";
                    case FOUR:		return "Four";
                    case FIVE:		return "Five";
                    case SIX:		return "Six";
                    case SEVEN:		return "Seven";
                    case EIGHT:		return "Eight";
                    case NINE:		return "Nine";
                    case SKIP:		return "Skip";
                    case REVERSE:	return "Reverse";
                    case DRAW_TWO:	return "Draw Two";
            }
            return null;
	}
    
    
    public String toImage() {

            switch(this) {
                    case ZERO:		return "zero";
                    case ONE:		return "one";
                    case TWO:		return "two";
                    case THREE:		return "three";
                    case FOUR:		return "four";
                    case FIVE:		return "five";
                    case SIX:		return "six";
                    case SEVEN:		return "seven";
                    case EIGHT:		return "eight";
                    case NINE:		return "nine";
                    case SKIP:		return "skip";
                    case REVERSE:	return "reverse";
                    case DRAW_TWO:	return "drawTwo";
            }
            return null;
	}

	public int toPoints() {
            switch(this) {
                    case ZERO:		return 0;
                    case ONE:		return 1;
                    case TWO:		return 2;
                    case THREE:		return 3;
                    case FOUR:		return 4;
                    case FIVE:		return 5;
                    case SIX:		return 6;
                    case SEVEN:		return 7;
                    case EIGHT:		return 8;
                    case NINE:		return 9;
                    case SKIP:		return 20;
                    case REVERSE:	return 20;
                    case DRAW_TWO:	return 20;
            }
            return 0;
    }
}
