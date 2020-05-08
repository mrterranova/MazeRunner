package Maze;

import java.util.*;
import java.lang.*;

public class MazeRunner{

    public static void main(String[] args) {
        //go straight to the game
        intro();
    }

    //
    public static void intro(){
        // global variables
        Scanner input = new Scanner(System.in);
        Maze myMap = new Maze();
        int moves = 0;

        //Introduction
        System.out.println("Welcome to ");
        art();
        try {
            Thread.sleep(1500);
            System.out.println("\n\tNote:\tIf you wish to stop at any time type: STOP\n\t\t\tJumping a pit will cost you 2 moves.");
            Thread.sleep(3000);
            System.out.println("Here is your current position: ");
            myMap.printMap();

            //now send to game
            gameBody(input, myMap, moves);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //all main gaming functions
    public static void gameBody(Scanner input, Maze myMap, int moves) {
        // as long as you did not win continue game procedures
        while (myMap.didIWin() == false) {
            String userInput = userMove(input);
            int moving = canItMove(myMap, userInput, input, moves);
            moves = 1 + moving;
            getMovesMessage(moves, input);

            //if you did win handle replay option
            if (myMap.didIWin()){
                System.out.println("Congrats! You won and you did it in "+ moves +" moves!");
                endGame(input);
            }
        }
    }

    //sends messages to the user of progress
    public static int getMovesMessage(int moves, Scanner input) {
        if (moves == 50 ){
            System.err.println("Warning: You have made 50 moves, you have 50 remaining before the maze exit closes");
        }
        if (moves == 75){
            System.err.println("Alert! You have made 75 moves, you only have 25 moves left to escape.");
        }
        if (moves == 80){
            System.err.println("DANGER! You have made 90 moves, you only have 10 moves left to escape!!");
        }
        if (moves >=100){
            System.err.println("Oh no! You took too long to escape, and now the maze exit is closed FOREVER >:[\n");
        System.out.println("Would you care to play again? (y/n)");
        char playAgain = input.next().charAt(0);
        if (playAgain == 'y')
            intro();
        } else {
//        System.exit(0);
        }
        return moves;
    }

    // determine if user command is applicable
    public static String userMove(Scanner input) {
        String userInput = "";
        //guarantee user selection is only one option
        boolean incorrect = true;
        while (incorrect) {
            System.out.println("Which direction?(R,L,D,U) ");
            userInput = input.nextLine().toUpperCase();
            if(userInput.equals("R") || userInput.equals("L") || userInput.equals("D") || userInput.equals("U") || userInput.equals("STOP")){
                incorrect = false;
            }
        }
        return userInput;
    }

    //determine if can move and move
    public static int canItMove(Maze myMap, String userInput, Scanner input, int moves) {
//        System.out.println(myMap.canIMoveRight() + " "+ userInput);
//        if (userInput.equals("R") && myMap.canIMoveRight() == true){
//            myMap.moveRight();
//        } else if(userInput.equals("L") && myMap.canIMoveLeft() == true){
//            myMap.moveLeft();
//        } else if(userInput.equals("U") && myMap.canIMoveUp() == true){
//            myMap.moveUp();
//        } else if(userInput.equals("D") && myMap.canIMoveDown() == true){
//            myMap.moveDown();
//        } else {
//            if (myMap.isThereAPit(userInput)){
//                navigatePit(myMap, input, userInput, moves);
//            } else {
//                System.err.println("You have reached a wall.");
//            }
//        }

        if(myMap.isThereAPit(userInput)){
            moves = navigatePit(myMap, input, userInput, moves);
        } else {
            switch (userInput) {
                case "D":
                    checkMoveDown(myMap);
                    break;
                case "U":
                    checkMoveUp(myMap);
                    break;
                case "L":
                    checkMoveLeft(myMap);
                    break;
                case "R":
                    checkMoveRight(myMap);
                    break;
                case "STOP":
                    System.out.println("Now terminating game...");
                    endGame(input);
                    break;
                default:
                    //this should never happen because of userMove();
                    System.err.println("Incorrect command. Try again.");
                    break;
            }
        }
        //always show new instance of map and return moves int.
        myMap.printMap();
        return moves;
    }

    //all methods to switch case in canIMove() for further customization
    public static void checkMoveRight(Maze myMap){
        if (myMap.canIMoveRight() == true){
            myMap.moveRight();
        } else {
            System.out.println("You have reached a wall.");
        }
    }
    public static void checkMoveDown(Maze myMap){
        if (myMap.canIMoveDown() == true){
            myMap.moveDown();
        } else {
            System.out.println("You have reached a wall.");
        }
    }
    public static void checkMoveUp(Maze myMap){
        if (myMap.canIMoveUp()){
            myMap.moveDown();
        } else {
            System.out.println("You have reached a wall.");
        }
    }
    public static void checkMoveLeft(Maze myMap){
        if(myMap.canIMoveLeft()){
            myMap.moveLeft();
        } else {
            System.out.println("You have reached a wall.");
        }
    }

    // to navigate the pit in canIMove()
    public static int navigatePit(Maze myMap, Scanner input, String userInput, int moves){
        System.out.println("Watch out! There's a pit ahead, jump it? (y/n)");
        String jump = input.nextLine();
        if (jump.equals("y")) {
            myMap.jumpOverPit(userInput);
            moves = moves + 2;

        } else {
            System.out.println(moves);
            moves = moves--;
        }
        return moves;
    }

    // end game scenario for winning and early termination.
    public static void endGame(Scanner input) {
        System.out.print("Try again for a better score? (yes/no) ");
        String tryThis = input.nextLine();
        if (tryThis.equals("yes")) {
            intro();
        } else {
            System.out.println("Thank you for playing Maze Runner.");
            System.exit(0);
        }
    }

    //make pretty intro
    public static void art(){
        System.out.println(                 " _______  _______  _______  _______    _______           _        _        _______  _______ ");
        System.out.println(                "(       )(  ___  )/ ___   )(  ____ \\  (  ____ )|\\     /|( (    /|( (    /|(  ____ \\(  ____ )");
        System.out.println(                "| () () || (   ) |\\/   )  || (    \\/  | (    )|| )   ( ||  \\  ( ||  \\  ( || (    \\/| (    )|");
        System.out.println(                "| || || || (___) |    /   )| (__      | (____)|| |   | ||   \\ | ||   \\ | || (__    | (____)|");
        System.out.println(                "| |(_)| ||  ___  |   /   / |  __)     |     __)| |   | || (\\ \\) || (\\ \\) ||  __)   |     __)");
        System.out.println(                "| |   | || (   ) |  /   /  | (        | (\\ (   | |   | || | \\   || | \\   || (      | (\\ (");
        System.out.println(                "| )   ( || )   ( | /   (_/\\| (____/\\  | ) \\ \\__| (___) || )  \\  || )  \\  || (____/\\| ) \\ \\__");
        System.out.println(                "|/     \\||/     \\|(_______/(_______/  |/   \\__/(_______)|/    )_)|/    )_)(_______/|/   \\__/");
    }
}
