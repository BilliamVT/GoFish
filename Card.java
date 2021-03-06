
//Defining the Card Class

public class Card implements Comparable<Card> {

    // This attribute is for the rank of the card.
    // (i.e 2,3,4,ace... etc.)
    String rank;

    // This attribute is for the suite of the card.
    // (i.e heart, diamond, spade, or club)
    String suit;


    public String getRank() {
        return rank;
    }

    //Constructor for the Card Class giving it a rank and a suit.
    Card(String rank, String suit) {

        this.rank = rank;
        this.suit = suit;

    }

    @Override
    public int compareTo(Card c) {
        int comCard = Integer.parseInt(c.getRank());

        int intRank = Integer.parseInt(this.rank);

        return intRank-comCard;
    }
}