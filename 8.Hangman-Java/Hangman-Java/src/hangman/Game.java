package hangman;

import java.util.Random;
import java.util.Scanner;

public class Game {
	
    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.runHangman(); 
    }

    private static final String[] wordForGuesing = { "computer", "programmer",
            "software", "debugger", "compiler", "developer", "algorithm",
            "array", "method", "variable" };

    private String guessWord;
    private FileReadWriter filerw;
    private boolean isHelpUsed;
    private StringBuffer dashBuff;

    //Constructor
    public Game() {
        filerw = new FileReadWriter();
    }

    //Sets the variables and start the guess-loop
    public void runHangman() {
        StringBuilder dashedWord;
        guessWord = wordForGuesing[new Random().nextInt(9)];
        isHelpUsed = false;
        dashedWord = new StringBuilder(new String(new char[guessWord.length()]).replace('\0', '_'));
        dashBuff = new StringBuffer(dashedWord);
        System.out.println("Welcome to �Hangman� game. Please, try to guess my secret word.\n"
                + "Use 'TOP' to view the top scoreboard, 'RESTART' to start a new game,"
                + "'HELP' to cheat and 'EXIT' to quit the game.");

        gameLoop();
    }

    //Method containing the main game logic with loops and end-of-game message
    private void gameLoop() {
        String letter;
        int mistakes = 0;

        //Loops until word is complete
        do {
            letter = inputLoop();

            int counter = replaceDashes(letter);

            if (counter == 0) {
                ++mistakes;
                System.out.printf("Sorry! There are no unrevealed letters \'%s\'. \n", letter);
            } else {
                System.out.printf("Good job! You revealed %d letter(s).\n", counter);
            }

        } while (!dashBuff.toString().equals(guessWord));

        //Word complete, display end of game message
        System.out.println("You won with " + mistakes + " mistake(s).");
        System.out.println("The secret word is: " + printDashes(dashBuff) + ".");
        if (!isHelpUsed) {

            System.out.println("Please enter your name for the top scoreboard:");
            Scanner input = new Scanner(System.in);
            String playerName = input.next();

            filerw.openAndWritetoFile(mistakes, playerName);
            filerw.openAndReadfromFile();
            filerw.printAndSortScoreBoard();
        

        } else {
            System.out.println("But since you cheated. You are not allowed to enter into the scoreboard.");
        }

        // restart the game
        runHangman();

    }

    //Loop which handles input, returns a letter
    private String inputLoop() {
        String letter;
        Scanner input = new Scanner(System.in);

        System.out.println("The secret word is: " + printDashes(dashBuff));
        System.out.println("DEBUG " + guessWord);

        do {
            System.out.println("Enter your guess(1 letter allowed): ");
            letter = input.next();

            if(dashBuff.indexOf(letter) > -1 && letter.length() < 2) {
                letter = "-";
                System.out.println("Letter already guessed.");
            }
            letter = commandCheckAndExecute(letter.toLowerCase());
        } while (!letter.matches("[a-z]"));

        return letter;
    }

    //Check if String word is a command, and executes action if it is
    private String commandCheckAndExecute(String word) {
        if(word.equals(Command.help.toString())) {
            isHelpUsed = true;
            int i = dashBuff.indexOf("_");
            word = String.valueOf(guessWord.charAt(i));
        } else if (word.equals(Command.restart.toString())) {
            runHangman();
        } else if (word.equals(Command.top.toString())) {
            sortAndPrintScore();
            runHangman();
        } else if (word.equals(Command.exit.toString())) {
            System.exit(1);
        }
        return word;
    }

    //Wrapper for turning a StringBuffer word into a space-separated
    private String printDashes(StringBuffer word) {
        String toDashes = "";
        for (int i = 0; i < word.length(); i++) {
            toDashes += (" " + word.charAt(i));
        }
        return toDashes;

    }

    //Wrapper for reading and printing scores
    private void sortAndPrintScore() {
        filerw.openAndReadfromFile();
        filerw.printAndSortScoreBoard();
    }

    //Wrapper which replaces underscores in dashBuff if the correct letter is guessed
    private int replaceDashes(String word) {
        int counter = 0;
        for (int i = 0; i < guessWord.length(); i++) {
            String currentLetter = Character.toString(guessWord.charAt(i));
            if (word.equals(currentLetter)) {
                ++counter;
                dashBuff.setCharAt(i, word.charAt(0));
            }
        }
        return counter;
    }
}