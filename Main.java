import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void fileIO(Player P1, ComputerPlayer P2, PrintWriter fileRecord){

        fileRecord.append("---------------Player 1 HAND------------------------\n");
        for (int p = 0; p < P1.hand.size(); p++) {

            if(P1.hand.get(p).suit == ("\u001B[31m"+ "\u2665" + "\u001B[0m")){
                P1.hand.get(p).suit = "\u2665";
                fileRecord.append(P1.hand.get(p).rank + " of " + P1.hand.get(p).suit + "\n");
                P1.hand.get(p).suit = "\u001B[31m"+ "\u2665" + "\u001B[0m";
            }
            else if(P1.hand.get(p).suit == "\u001B[31m" + "\u2666" + "\u001B[0m"){
                P1.hand.get(p).suit = "\u2666";
                fileRecord.append(P1.hand.get(p).rank + " of " + P1.hand.get(p).suit + "\n");
                P1.hand.get(p).suit = "\u001B[31m" + "\u2666" + "\u001B[0m";
            }
            else{
                fileRecord.append(P1.hand.get(p).rank + " of " + P1.hand.get(p).suit + "\n");
            }

        }

        fileRecord.append("---------------Player 2 HAND------------------------\n");
        for (int p = 0; p < P2.hand.size(); p++) {
            if(P2.hand.get(p).suit == ("\u001B[31m"+ "\u2665" + "\u001B[0m")){
                P2.hand.get(p).suit = "\u2665";
                fileRecord.append(P2.hand.get(p).rank + " of " + P2.hand.get(p).suit + "\n");
                P2.hand.get(p).suit = "\u001B[31m"+ "\u2665" + "\u001B[0m";
            }
            else if(P2.hand.get(p).suit == "\u001B[31m" + "\u2666" + "\u001B[0m"){
                P2.hand.get(p).suit = "\u2666";
                fileRecord.append(P2.hand.get(p).rank + " of " + P2.hand.get(p).suit + "\n");
                P2.hand.get(p).suit = "\u001B[31m" + "\u2666" + "\u001B[0m";
            }
            else{
                fileRecord.append(P2.hand.get(p).rank + " of " + P2.hand.get(p).suit + "\n");
            }

        }
    }
    public static void main(String[] args) {

        //Unicode values for colors and reset.
        String RESET = "\u001B[0m";
        String BLACK = "\u001B[30m";
        String RED = "\u001B[31m";

        // Unicode Values for suits. Can combine them with colors so have
        // diamonds and hearts be red.
        String heart = "\u2665";
        String diamond = "\u2666";
        String spade = "\u2660";
        String club = "\u2663";

        // TEST MODE ON / OFF
        boolean test = false;

        // Ask to Start Game
        Scanner scanner = new Scanner(System.in);


        System.out.println("----- Welcome to Go Fish! -----");
        System.out.println("----- Start New Game? (y) -----");
        System.out.println("----- Quit? (n) -----");

        String start = scanner.nextLine();

        while (!start.toLowerCase().equals("y") && !start.toLowerCase().equals("n")){

            System.out.println("----- Invalid Response... -----");
            System.out.println("----- Start New Game? (y) -----");
            System.out.println("----- Quit? (n) -----");

            start = scanner.nextLine();

        }

        // Start Game if "y"
        if (start.toLowerCase().equals("y")) {

            // Initialize new deck of cards
            Deck deck = new Deck();

            // Shuffle the deck
            Collections.shuffle(deck.cards);

            // Initialize Player 1 and Player 2
            Player P1 = new Player();
            ComputerPlayer P2 = new ComputerPlayer();

            // Get game mode (smart / dumb)
            System.out.println("----- Would you like to play against a smart or dumb Computer? -----");
            System.out.println("----- Smart (s) or Dumb (d)? -----");
            String mode = scanner.nextLine();

            while (!mode.toLowerCase().equals("s") && !mode.toLowerCase().equals("d")){

                System.out.println("----- Invalid Response... -----");
                System.out.println("----- Smart (s) or Dumb (d) Computer Mode? -----");

                mode = scanner.nextLine();

            }

            // Set Computer Mode
            if (mode.toLowerCase().equals("s")){
                P2.isSmart = true;
                mode = "Smart";
            }
            else{
                P2.isSmart = false;
                mode = "Dumb";
            }

            // Get Computer Lie Percentage
            System.out.println("----- What would you like the Computer Lie Percentage to be? (0-100) -----");

            while (!scanner.hasNextInt()) {
                String input = scanner.next();
                System.out.printf("\"%s\" is not a valid number.\n", input);
            }

            Integer lie = scanner.nextInt();

            while (lie < 0 || lie > 100) {

                while (!scanner.hasNextInt()) {
                    String input = scanner.next();
                    System.out.printf("\"%s\" is not a valid number.\n", input);

                    System.out.println("Invalid Response...");
                    System.out.println("Computer Lie Percentage? (0-100)");

                    lie = scanner.nextInt();

                }
            }

            // Print settings
            System.out.println("Your settings:");
            System.out.println("Computer mode set to: " + mode);
            System.out.println("Computer Lie Percentage set to: " + lie + "%");



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

            System.out.println("Dealing out the cards now...");

            // Adjust deck size after dealing
            deck.cards.trimToSize();
            Collections.sort(P1.hand);

            // Rank Selection Menu
            System.out.println("----- Rank selection menu -----");
            System.out.println("     2   3   4   5   6");
            System.out.println("     7   8   9   10");
            System.out.println("     Jack - 11    Queen - 12");
            System.out.println("     King - 13   Ace - 1");

            System.out.println("----- Suits key -----");
            System.out.println("Clubs - " + club);
            System.out.println("Spades - " + spade);
            System.out.println("Hearts - " + RED + heart + RESET);
            System.out.println("Diamonds - " + RED + diamond + RESET + "\n");


            // new game to start file over
            PrintWriter newGame = null;

            // create new game printer
            try {
                newGame =  new PrintWriter(new BufferedWriter(new FileWriter("goFishRecord.txt",false)));
            } catch (IOException ex) {
                System.out.println("File does not exist could not clear");
            }

            // clears file prints new game
            newGame.append("New Game:\n");
            // close file
            newGame.close();

            int turnCounter = 0;

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////

            // START GAME LOOP //

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////


            boolean game = true;

            // there are no cards in deck and no cards in hands ends game
            while (game) {

                turnCounter++;
                // create writer to write to file
                PrintWriter fileRecord = null;
                // create game recorder
                try {
                    fileRecord = new PrintWriter(new BufferedWriter(new FileWriter("goFishRecord.txt",true)));
                } catch (IOException ex){
                    System.out.println("File does not exist game will not be recorded");
                }

                // turn number
                fileRecord.append("----- Turn number: " + turnCounter + " -----\n");

                // print books to file
                fileRecord.append("Player 1 books: " + P1.books + "\n");
                fileRecord.append("Player 2 books: " + P2.books + "\n");

                // print hands to file
                fileIO(P1, P2, fileRecord);


                //////////////////// Start P1 turn (keep asking until go fish (and go fish is not card requested))//////////////////////

                boolean P1Turn = true;
                while (P1Turn) {
                    Collections.sort(P1.hand);
                    System.out.println("----- Book Count -----");
                    System.out.println("Your Books: " + P1.books);
                    System.out.println("Player 2 Books: " + P2.books + "\n");

                    String rankAsk1;
                    String tempRankAsk1;

                    if (P1.hand.size() == 0 && deck.cards.size() != 0){

                        // Grab card off top of deck/pool
                        Card draw = deck.cards.get(0);

                        // Add to P1 hand
                        P1.hand.add(draw);

                        // Remove from deck and resize
                        deck.cards.remove(0);
                        deck.cards.trimToSize();

                        System.out.println("You ran out of cards, you went fishing...");
                        System.out.println("You drew a: " + draw.rank + " of " + draw.suit + " !");

                        tempRankAsk1 = draw.rank;
                        fileRecord.append("Player 1 had no cards so they had to draw from the deck\n");
                        fileRecord.append("---------------Player 1 HAND------------------------\n");

                    } else if(P1.hand.size() != 0 && deck.cards.size() != 0){
                        // Ask Player 1 which rank they would like to ask P2 for
                        tempRankAsk1 = P1.ask();
                    }
                    tempRankAsk1 = "0";
                    rankAsk1 = tempRankAsk1;
                    // put what player 1 asked for and then player 1 and 2 hands in file
                    fileRecord.append("Player 1 asked for " + rankAsk1 + "\n");
                    fileIO(P1, P2, fileRecord);

                    // Add the requested rank to P2's "memory" if it is not already in it
                    // (P2 believes P1 has at least one of this rank (remove when P2 gets this card rank back or a book is laid down of that rank)
                    if (P2.isSmart && !P2.memory.contains(rankAsk1)) {
                        P2.memory.add(rankAsk1);
                    }

                    // Go Fish flag
                    boolean goFish = true;

                    // Set if P2 should be lying based on lie percentage
                    boolean compLie;

                    if(new java.util.Random().nextInt(100) <= lie){
                        compLie = true;
                    }
                    else{
                        compLie = false;
                    }


                    // Loop through P2 hands looking for cards of requested rank
                    for (int i = 0; i < P2.hand.size(); i++) {

                        // If we find a card with the requested rank and P2 is not lying...
                        if (P2.hand.get(i).rank.equals(rankAsk1) && !compLie) {

                            // we're not going fishing
                            goFish = false;

                            // Add the card to P1's hand
                            Card cardtoAdd = P2.hand.get(i);
                            System.out.println("You got " + P2.hand.get(i).rank + " of " + P2.hand.get(i).suit + " from P2!");
                            P1.hand.add(cardtoAdd);
                        }
                    }

                    // Also if P2 not lying, this removes cards from their hand
                    if (!compLie) {
                        // Remove all (if any) cards of the requested rank from P2's hand
                        P2.hand.removeIf(card -> card.rank.equals(rankAsk1));
                        P2.hand.trimToSize();
                    }


                    // if we found no cards (or P2 is lying), we FISH!
                    if (goFish && deck.cards.size() != 0) {

                        System.out.println("Player 2 says, GO FISH!");

                        // Grab card off top of deck/pool
                        Card draw = deck.cards.get(0);

                        // Add to P1 hand
                        P1.hand.add(draw);

                        System.out.println("You drew a: " + draw.rank + " of " + draw.suit + " !");

                        // Remove from deck and resize
                        deck.cards.remove(0);
                        deck.cards.trimToSize();

                        // Check to see if card was what P1 asked for...continue game if yes
                        if (draw.rank.equals(rankAsk1)) {

                            P1Turn = true;

                            fileIO(P1, P2, fileRecord);

                        }

                        // Else, end P1's turn
                        else{

                            P1Turn = false;
                        }
                    }

                    // Check for new books!

                    if (goFish) {
                        P1.checkBooks(P2, P1.hand.get(P1.hand.size()-1).rank);
                    }
                    else{
                        P1.checkBooks(P2, rankAsk1);
                    }

                    if (P1.books + P2.books == 13){

                        System.out.println("\nGame Over!");
                        System.out.println("You: "  + P1.books);
                        System.out.println("Computer: "  + P2.books);
                        game = false;

                    }
                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                System.out.println("P2 turn begins...");

                boolean P2Turn = true;
                String rankAsk2Temp;

                while (P2Turn) {
                    if (P2.hand.size() == 0 && deck.cards.size() != 0){

                        // Grab card off top of deck/pool
                        Card draw = deck.cards.get(0);

                        // Add to P1 hand
                        P2.hand.add(draw);

                        // Remove from deck and resize
                        deck.cards.remove(0);
                        deck.cards.trimToSize();

                        System.out.println("P2 ran out of cards and went fishing...");
                        rankAsk2Temp = draw.rank;
                        System.out.println("P2 asks for " + rankAsk2Temp);

                        fileRecord.append("Player 2 had no cards so they had to draw from the deck\n");
                        fileIO(P1, P2, fileRecord);
                    } else if (P2.hand.size() == 0) {
                        rankAsk2Temp = "1";
                    } else {
                        rankAsk2Temp = P2.determineAsk();
                    }

                    String rankAsk2 = rankAsk2Temp;
                    // put in file what computer asked for and then player 1 and 2 hand
                    fileRecord.append("Player 2 asked for " + rankAsk2 + "\n");
                    fileIO(P1, P2, fileRecord);
                    // Go Fish flag
                    boolean goFish = true;

                    // Loop through P1 hands looking for cards of requested rank
                    for (int i = 0; i < P1.hand.size(); i++) {

                        // If we find a card with the requested rank and P2 is not lying...
                        if (P1.hand.get(i).rank.equals(rankAsk2)) {

                            P2.memory.removeIf(m -> m.equals(rankAsk2));
                            P2.memory.trimToSize();


                            // we're not going fishing
                            goFish = false;

                            // Add the card to P2's hand
                            Card cardtoAdd = P1.hand.get(i);
                            System.out.println("You gave " + P1.hand.get(i).rank + " of " + P1.hand.get(i).suit + " to P2!");
                            P2.hand.add(cardtoAdd);
                        }
                    }


                    P1.hand.removeIf(card -> card.rank.equals(rankAsk2));
                    P1.hand.trimToSize();

                    // if we found no cards, make them FISH!
                    if (goFish && deck.cards.size() != 0) {

                        System.out.println("You tell P2 to, GO FISH!");

                        // Grab card off top of deck/pool
                        Card draw = deck.cards.get(0);

                        // Add to P1 hand
                        P2.hand.add(draw);

                        // Remove from deck and resize
                        deck.cards.remove(0);
                        deck.cards.trimToSize();

                        // Check to see if card was what P2 asked for...continue game if yes
                        if (draw.rank.equals(rankAsk2)) {

                            P2Turn = true;
                            System.out.println("Computer got their wish!");

                        }

                        // Else, end P2's turn
                        else{

                            P2Turn = false;
                        }
                    }

                    // Check for new books!

                    if (goFish) {
                        if(P2.hand.size()-1 >= 0 ) {
                            P2.checkBooks(P2, P2.hand.get(P2.hand.size() - 1).rank);
                        }
                    }

                    else{

                        P2.checkBooks(P2, rankAsk2);

                    }
                    if (P1.books + P2.books == 13){

                        System.out.println("\nGame Over!");
                        System.out.println("You: "  + P1.books);
                        System.out.println("Computer: "  + P2.books);
                        game = false;
                        P2Turn = false;
                    }




                }

                
                fileRecord.close();


            }

        }

        // Quit game
        else{

            System.out.println("Bye now");
            System.exit(0);
        }

    }
}