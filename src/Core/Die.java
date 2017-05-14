/*
 * Daniel Lott July 31th 2015 
 * Karin Whiting's Java Class: Summer 2015
 * Assignment Four, a even more working version of Assignment Three
 */
package Core;

import boggle.*;
import java.util.ArrayList;
import java.util.Random;

public class Die {

    private final int NUMBER_OF_SIDES = 6;
    private final int NUMBER_OF_DICE = 16;
    private final int NUMBER_OF_FACES = (6 * 16);
    public ArrayList<String> dieData = Boggle.boggleData; // stores dice data for the sides
    public String lett; // stores the current letter of each die

    public Die(ArrayList<String> al) {
        dieData = new ArrayList<String>();
        dieData = al;
    }//end Die

    public void randomLetter(int a) {
        int e = 0, f = 5;//range to seed randomizer
        int randomNum = randomizer(e, f);//gets random number between e and f
        lett = dieData.get(((randomNum) + (6 * (a))));//gets element at returned random number, times 6 a to move to a new die with each instance so the same die is not picked
    }//end randomLetter

    public int randomizer(int min, int max) {//find a random number between min and max
        Random rand = new Random();//new Random
        int randomNum = rand.nextInt((max - min) + 1) + min;//picks a random number
        return randomNum;
    }//end randomizer

    public String getLetter(int a) {
        randomLetter(a);//calls random letter, a is the dice number
        return lett;
    }//end getLetter

    void addLetter(String a) {
        dieData.add(a);
    }// end addLetter

    public void displayAllLetters() {
        int counter = 0;// ,cOne , cTwo = 0;
        String mess = null;
        for (int dice = 0; dice < NUMBER_OF_DICE; dice++) {
            //System.out.print("Die " + (dice + 1) + ": ");//at the beginning of a new Die it is printed
            for (int side = 0; side < NUMBER_OF_SIDES; side++) {
                mess = dieData.get(counter);//passes letter to messenger string
                //System.out.print(mess + " ");//printer letter from mess
                counter++;
            }//end number_of_sides
            //System.out.println();//formatting
        }//end number of dice
    }//end die class
}
