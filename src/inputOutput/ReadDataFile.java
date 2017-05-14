/*
 * Daniel Lott July 31th 2015 
 * Karin Whiting's Java Class: Summer 2015
 * Assignment Four, a even more working version of Assignment Three
 */
package inputOutput;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.net.URISyntaxException;

public class ReadDataFile {

    private Scanner input;
    private String inFileName;// = "BoggleData.txt";    //dataFileName
    private ArrayList<String> data = new ArrayList<String>();

    public ReadDataFile(String inputName) {
        inFileName = inputName;
    }//end ReadDataFile

    public void populateData() {
        String mess;
        try {
            URL url = getClass().getResource(inFileName); //creates new instance of url and sets to inFileName
            File file = new File(url.toURI());  //creates new instance of file to scan in
            input = new Scanner(file);// scans file
            while (input.hasNextLine()) { //while there is a line to read in
                mess = input.nextLine();//entire line is read in and passed to a string
                for (String messTwo : mess.split(" ", 0)) {//string is then split into single letters
                    data.add(messTwo);//single letters are added
                }
            }//while loop
        } catch (URISyntaxException | FileNotFoundException ex) {
        } finally {
            input.close();//closes input file
        }
    }//end populateData
    
    

    public ArrayList getData() {
        return data;//returns ArrayList of strings that was populated
    }//end getData
}
