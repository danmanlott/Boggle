/*
 * Daniel Lott July 31th 2015 
 * Karin Whiting's Java Class: Summer 2015
 * Assignment Four, a even more working version of Assignment Three
 */
package Core;

import java.util.ArrayList;

public class Board {

    private final static int NUMBER_OF_DICE = 16;
    private final static int NUMBER_OF_SIDES = 6;
    private final static int GRID = 4;
    public ArrayList<String> dieData; //= new ArrayList(); // stores all dice data
    public ArrayList<String> chosenLetters; //= new ArrayList(); // stores all dice data    
    public ArrayList<Die> dieObjects; //= new ArrayList(); // stores all dice objects

    public Board(ArrayList<String> al) {
        dieData = new ArrayList<String>();
        dieObjects = new ArrayList<Die>();
        dieData = al;
    }//end board

    public void populateDice() {
        Die die = null;//create new class Die die
        int counter = 0;
        die = new Die(dieData);//creates die using dieData
        for (int dice = 0; dice < NUMBER_OF_DICE; dice++) {
            for (int side = 0; side < NUMBER_OF_SIDES; side++) {
                die.addLetter(dieData.get(counter));//adds current letter to die
                counter++;
            }//end number_of_sides
        }//end number of dice
        die.displayAllLetters();
        dieObjects.add(die);//adds the populated die to dieObjects
    }

    public ArrayList<Die> shakeDice() {
        chosenLetters = null;
        int counter = 0;
        String mess;
        populateDice();//populates dice for game
        chosenLetters = new ArrayList<String>();
        //System.out.println("Boggle board");
        Die die = new Die(dieData);// //creates new instance of Die die using dieData
        //System.out.print("[ ");//formatting 
        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            mess = die.getLetter(counter);//gets a random letter from each dice and passes to a messenger string          
            counter++;
            chosenLetters.add(mess);
            //System.out.print(mess + " ");//string is read
            if (counter % 4 == 0) {
                //System.out.print("]");//formatting
                //System.out.println();//formatting
                //System.out.print("[ ");//formatting
            }
        }
        return dieObjects;//returns dieObjects
    }//end shake dice
    
    public ArrayList<String> getArrayList(){
    return chosenLetters;
    }
}//end Board class

