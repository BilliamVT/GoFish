
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
import java.io.*;

public class Main {


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static boolean deckCheck(String rank, Player p1, Player p2, Deck d){
        boolean goFish = true;

        // TODO: Decide if P2 lying or not
        // Loop through P2 hands looking for cards of requested rank
        for (int i = 0; i < p2.hand.size(); i++) {

            // If we find a card with the requested rank...
            if (p2.hand.get(i).rank.equals(rank)) { //TODO: and not lying
                // we're not going fishing
                goFish = false;
                Card memoryCard = new Card(p2.hand.get(i).rank, p2.hand.get(i).suit);
                if(p1.getType() == "Computer"){
                    //need to add the card to the memory arraylist for the computer player
                    //in the case that the computer is smart

                }
                else if(p2.getType() == "Computer"){

                }

                // Add the card to P1's hand
                Card cardtoAdd = p2.hand.get(i);
                System.out.println("You got " + p2.hand.get(i).rank + " of " + p2.hand.get(i).suit);
                p1.hand.add(cardtoAdd);
            }


        }

        // TODO: If not lying...
        // Remove all (if any) cards of the requested rank from P2's hand // TODO: Need to make it so we can combine this with above for lie percentage
        p2.hand.removeIf(card -> card.rank.equals(rank));
        p2.hand.trimToSize();

        // if we found no cards (or P2 is lying), we FISH!
        if (goFish) { //TODO: && deck.cards.size != 0

            System.out.println("Player 2 says, GO FISH!");

            // Grab card off top of deck/pool
            Card draw = d.cards.get(0);

            // Add to P1 hand
            p1.hand.add(draw);

            // Display what you picked up
            System.out.println("You got " + draw.rank + " of " + draw.suit + " from the deck!");

            // Remove from deck and resize
            d.cards.remove(0);
            d.cards.trimToSize();

            // Check to see if card was what P1 asked for...continue game if yes
            if (draw.rank.equals(rank)) {

                return true;

            }

            // Else, end current players turn
            else{
                return false;
            }
        }

    }

    public static void main(String[] args) {
        // create writer to write to file and one to clear file
        PrintWriter fileRecord = null;
        PrintWriter newGame = null;

        // create new game printer
        try {
            newGame =  new PrintWriter(new BufferedWriter(new FileWriter("goFishRecord.txt",false)));
        } catch (IOException ex) {
            System.out.println("File does not exist could not clear");
        }

        // clears file prints new game
        newGame.append("New Game:");
        // close file
        newGame.close();

        // create game recorder
        try {
            fileRecord = new PrintWriter(new BufferedWriter(new FileWriter("goFishRecord.txt",true)));
        } catch (IOException ex){
            System.out.println("File does not exist game will not be recorded");
        }


        System.out.println(ANSI_RED + "This text is red!" + ANSI_RESET);

        // TEST MODE ON / OFF
        boolean test = false;

        // Ask to Start Game
        Scanner scanner = new Scanner(System.in);

        System.out.println("Start New Game? (y)");
        System.out.println("Quit? (n)\n");

        String start = scanner.nextLine();

        while (!start.toLowerCase().equals("y") && !start.toLowerCase().equals("n")){

            System.out.println("Invalid Response...");
            System.out.println("Start New Game? (y)");
            System.out.println("Quit? (n)\n");

            start = scanner.nextLine();

        }

        // player determines if the computer is smart or not
        System.out.println("Would you like the computer to be smart (remember all guesses)? (y)");
        System.out.println("Or not? (n)\n");

        String smart = scanner.nextLine();
        while (!smart.toLowerCase().equals("y") && !smart.toLowerCase().equals("n")){

            System.out.println("Invalid Response...");
            System.out.println("Would you like the computer to be smart (remember all guesses)? (y)");
            System.out.println("Or not? (n)\n");

            smart = scanner.nextLine();
        }
        boolean isComputerSmart = false;

        if (smart.toLowerCase().equals("y")){
            isComputerSmart = true;
        } else if (smart.toLowerCase().equals("n")){
            isComputerSmart = false;
        }

        // Start Game!
        if (start.toLowerCase().equals("y")) {

            // Initialize new deck of cards

            Deck deck = new Deck();

            // Note: in IntelliJ, to hide the blocks of code for testing (which we can get rid of soon), click the if statement and
            // do ctrl-shift-period / command-shift-period

            // FOR TESTING
            if (test) {

                System.out.println("\n");

                System.out.println("---------------FULL DECK CARDS------------------------\n");

                for (int p = 0; p < deck.cards.size(); p++) {

                    System.out.println(deck.cards.get(p).rank + " of " + deck.cards.get(p).suit);

                }

            }

            // Shuffle the deck

            System.out.println("shuffling...");
            Collections.shuffle(deck.cards);


            // FOR TESTING
            if (test) {

                System.out.println("\n");

                System.out.println("---------------SHUFFLED FULL DECK------------------------\n");

                for (int p = 0; p < deck.cards.size(); p++) {

                    System.out.println(deck.cards.get(p).rank + " of " + deck.cards.get(p).suit);

                }

            }

            // Initialize Player 1 and Player 2
            Player P1 = new HumanPlayer();
            Player P2 = new ComputerPlayer(isComputerSmart);


            System.out.println("dealing...\n");
            // Deal 7 cards to P1's hand, remove cards from deck, update deck size
            for (int i = 0; i < 7; i++) {
                P1.hand.add(deck.cards.get(i));
                deck.cards.remove(i);
            }

            // Deal 7 cards to P2's hand, remove cards from deck, update deck size
            for (int i = 0; i < 7; i++) {
                P2.hand.add(deck.cards.get(i));
                deck.cards.remove(i);
            }

            // Adjust deck size after dealing
            deck.cards.trimToSize();

            // FOR TESTING
            if (test) {

                System.out.println("\n");

                System.out.println("---------------P1 HAND------------------------\n");

                for (int p = 0; p < P1.hand.size(); p++) {

                    System.out.println(P1.hand.get(p).rank + " of " + P1.hand.get(p).suit);
                }

                System.out.println("---------------P2 HAND------------------------\n");

                for (int p = 0; p < P2.hand.size(); p++) {
                    System.out.println(P2.hand.get(p).rank + " of " + P2.hand.get(p).suit);
                }
            }

            // Rank Selection Menu
            System.out.println("\n");
            System.out.println("Rank selection menu:");
            System.out.println("2   3   4   5   6");
            System.out.println("7     8    9    10");
            System.out.println("Jack - 11    Queen - 12");
            System.out.println("King - 13   Ace - 1\n");


            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // GAME LOOP //

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // initially turn number 1
            int turnNumber = 1;
            while (true) {
                // print turn number
                fileRecord.append("Turn number " + turnNumber);
                // increment turn number
                turnNumber++;
                // print to file
                fileRecord.append("---------------P1 HAND------------------------\n");

                for (int p = 0; p < P1.hand.size(); p++) {
                    // print to file
                    fileRecord.append(P1.hand.get(p).rank + " of " + P1.hand.get(p).suit + "\n");
                }
                // print to file
                fileRecord.append("---------------P2 HAND------------------------\n");

                for (int p = 0; p < P2.hand.size(); p++) {
                    // print to file
                    fileRecord.append(P2.hand.get(p).rank + " of " + P2.hand.get(p).suit + "\n");
                }

                // Start P1 turn (keep asking until go fish and go fish is not card requested)
                boolean P1Turn = true;
                boolean P2Turn;

                while (P1Turn) {
                    // Ask Player 1 which rank they would like to ask for
                    String rankAskP1 = P1.ask();
                    // print asks to file
                    fileRecord.append("Player 1 asked Player 2 for: " + rankAskP1 + "\n");
                    // Gameflow for the rank asked for
                    P1Turn = deckCheck(rankAskP1, P1, P2, deck);
                    P2Turn = !P1Turn;

                while(P2Turn){
                    String rankAskP2 = P2.ask();
                    // print asks to file
                    fileRecord.append("Player 2 asked Player 1 for: " + rankAskP2 + "\n");

                    P2Turn = deckCheck(rankAskP2, P2, P1, deck);
                    P1Turn = !P2Turn;
                }


                    // Check for new books!
                    P1.checkBooks();


                    // display to console and print to file the books
                    System.out.println("Player 1 books: " + P1.books);
                    System.out.println("Player 2 books: " + P2.books);
                    fileRecord.append("Player 1 books " + P1.books);
                    fileRecord.append("Player 2 books " + P2.books);

                }




                // TODO: Check to make sure game is not over (check P1 books + P2 books = ?)


                System.out.println("P2 turn begins...");



                //TODO - P2.turn()

                // TODO: Computer Player (smart and dumb modes)
                // TODO: Check if game is not over
                // TODO: Make it loop
                // TODO: Add lie percentage - (directions say on responses so that's just changing one spot highlighted above)


                if (test) {
                    System.out.println("---------------P2 HAND------------------------\n");

                    for (int p = 0; p < P2.hand.size(); p++) {

                        System.out.println(P2.hand.get(p).rank + " of " + P2.hand.get(p).suit);

                    }
                }
                
            }
        }

        // Quit game
        else{

            System.out.println("Bye now");
            System.exit(0);
        }

    }
}