/*
 * Daniel Lott July 31th 2015 
 * Karin Whiting's Java Class: Summer 2015
 * Assignment Four: a even more working version of Assignment Three
 */
package boggle;

import inputOutput.ReadDataFile;
import Core.*;
import userInterface.BoggleUI;
import java.util.ArrayList;

public class Boggle {

    public static ArrayList<String> boggleData;// = new ArrayList();
    public static ArrayList<String> wordsData;// = new ArrayList();
    private final static String inFileName = "BoggleData.txt";
    private final static String inDictFileName = "TemporaryDictionary.txt";

    //
    public static void main(String[] args) {
        boggleData = new ArrayList<String>(); //creates new boggleData
        ReadDataFile lettData = new ReadDataFile(inFileName); //creates new instance of ReadDataFile
        ReadDataFile dictData = new ReadDataFile(inDictFileName);
        lettData.populateData();// populates ReadDataFile
        dictData.populateData();
        boggleData = lettData.getData(); //boggleData gets what was populated
        wordsData = dictData.getData(); //boggleData gets what was populated
        for (int i = 0; i < wordsData.size(); i++) {
            wordsData.set(i, wordsData.get(i).toUpperCase());
        }
        Board boggleBoard = new Board(boggleData); //creates new instance of Board
        boggleBoard.shakeDice(); //Shakes dice and prints a board fit to play
        BoggleUI YouEye = new BoggleUI(boggleBoard, wordsData);//creates new instance of BoggleUI
    }
}
