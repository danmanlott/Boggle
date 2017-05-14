/*
 * Daniel Lott July 31th 2015 
 * Karin Whiting's Java Class: Summer 2015
 * Assignment Four, a even more working version of Assignment Three
 */
package userInterface;

import Core.Board;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.util.Timer;
import java.util.TimerTask;

public class BoggleUI {

    private GridLayout boardPrintGridLayout;//DiceBoard
    private JFrame frame;
    private JMenuBar MenuBar;
    private JMenu BoggleMenu;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;
    private JTextArea wordsTextArea;
    private JScrollPane scrollPane;
    private JButton shakeButton;
    private JButton submitButton;//
    private JPanel boggesPanel;
    private JPanel timeLeftPanel;
    private JPanel dicePanel;
    private JPanel alignPanel;
    private JPanel makeWordPanel;//    
    private JPanel currentWordPanel;
    private JPanel scorePanel;// 
    private JLabel timerLabel;
    private JLabel currentWord;//
    private JLabel currentScore;//
    private JButton[] buttons;
    private final Board board;
    private int playerOneScore;//
    private int delayTime;//
    private int periodTime;//
    private String currentWordString;//
    private Timer timerTheTimer;
    private String secsString;
    private int interval;
    public static ArrayList<String> wordsData;
    public ArrayList<String> duplicateList;
    public ArrayList<String> computerFoundList;
    public ArrayList<String> yourFoundList;
    private ArrayList<Integer> blackList;

    public BoggleUI(Board inBoard, ArrayList<String> inWordsData) {
        board = inBoard;
        wordsData = inWordsData;
        System.out.println(board.chosenLetters);
        blackList = new ArrayList();
        duplicateList = new ArrayList();
        computerFoundList = new ArrayList();
        yourFoundList = new ArrayList();
        initComponents();
    }//end BoggleUI Constructor

    private void initComponents() {

        //Nimbus feel
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        //layout stuff
        BorderLayout layout = new BorderLayout();
        boggesPanel = new JPanel();//panel for text and timer and button
        boggesPanel.setLayout(new BoxLayout(boggesPanel, BoxLayout.Y_AXIS));

        //Frame Stuff
        frame = new JFrame("Boggle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);//Res fit for 1995
        frame.setLayout(layout);

        //MenuBar Stuff
        MenuBar = new JMenuBar();//makes bar
        BoggleMenu = new JMenu("Boggle");//makes Boggle Menu
        BoggleMenu.setMnemonic('B');//Mnemonic stuff
        newGameMenuItem = new JMenuItem("New Game");//makes New File Menu option
        newGameMenuItem.setMnemonic('N');//Mnemonic stuff
        newGameMenuItem.addActionListener(new newGameListener());
        exitMenuItem = new JMenuItem("Exit");//makes Exit Menu option
        exitMenuItem.setMnemonic('E');//Mnemonic stuff
        exitMenuItem.addActionListener(new ExitListener());
        BoggleMenu.add(newGameMenuItem);//adding items
        BoggleMenu.add(exitMenuItem);//adding items
        MenuBar.add(BoggleMenu);//adding Menu for Boggle
        frame.add(MenuBar, BorderLayout.NORTH);//framing

        //ScrollPane stuff
        wordsTextArea = new JTextArea();//Makes Text Area
        wordsTextArea.setLineWrap(true);
        wordsTextArea.setWrapStyleWord(true);//Wrapping this shit up!
        scrollPane = new JScrollPane(wordsTextArea);//Puts Text Area in Scroll Pane
        scrollPane.setPreferredSize(new Dimension(240, 1000));//Sets Size
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);//No Horizontal Scrollbar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);// Vertical Scrollbar if needed only
        boggesPanel.setBorder(BorderFactory.createTitledBorder("Enter Words"));//Titles
        boggesPanel.add(scrollPane);//Adds to Daddy Panel

        //Timer Stuff
        timeLeftPanel = new JPanel();//Makes new Panel
        timeLeftPanel.setBorder(BorderFactory.createTitledBorder("Time Left"));//Titles border 
        timerLabel = new JLabel();//HTML span style for bold
        timerLabel.setFont(new Font("Serif", Font.BOLD, 36));
        //timer.setText("3: 00");
        //Scanner sc = new Scanner(System.in);

        timerTheTimer = new Timer();
        timerTheTimer = TimerBullshit(timerTheTimer);
        timeLeftPanel.add(timerLabel);//adds Label to Panel
        boggesPanel.add(timeLeftPanel);//adds Panel to Daddy Panel

        //Shake Dice Button Stuff
        shakeButton = new JButton("Shake Dice");//Names buttons
        shakeButton.setPreferredSize(new Dimension(100, 50));//Sizes Button
        shakeButton.addActionListener(new ShakeListener());

        alignPanel = new JPanel();//Makes new panel
        alignPanel.setLayout(new BorderLayout());//makes new layout just for button
        alignPanel.add(shakeButton, BorderLayout.WEST);//aligns the button on the left
        boggesPanel.add(alignPanel);//adds to Daddy Panel

        //Dice Buttons Printing Stuff
        boardPrintGridLayout = new GridLayout(4, 4);//makes a grid
        dicePanel = new JPanel(boardPrintGridLayout);
        dicePanel.setBorder(BorderFactory.createTitledBorder("Dice"));//sets title
        buttons = new JButton[16];//declares local Array
        ArrayList<String> temp = board.getArrayList();//Makes a String ArrayList for the chosen Letters
        Collections.shuffle(temp);
        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton(temp.get(i));//Makes a button out of each letter, then ransfers to a local Array
            int c = 0, r = 0;
            c = (i % 4);//column calculations
            r = (i / 4);//row calculations
            buttons[i].putClientProperty("column", c);//sets each button with a column number
            buttons[i].putClientProperty("row", r);//sets each button with a row number
            buttons[i].addActionListener(new ButtonListener());
            dicePanel.add(buttons[i]);//adds buttons to panel
        }//end for loop

        //makeWord Panel Stuff
        makeWordPanel = new JPanel();
        makeWordPanel.setBorder(BorderFactory.createTitledBorder("Time Left"));//Titles border
        makeWordPanel.setLayout(new BorderLayout());//makes new layout for makeWordPanel

        currentWordString = null;
        currentWord = new JLabel(currentWordString);
        currentWord.setFont(new Font("Serif", Font.BOLD, 30));
        currentWordPanel = new JPanel();//Makes new Panel
        currentWordPanel.setBorder(BorderFactory.createTitledBorder("Current Word"));//Titles border
        currentWordPanel.setPreferredSize(new Dimension(50, 25));
        currentWordPanel.add(currentWord);
        submitButton = new JButton("Submit Word");

        submitButton.addActionListener(new submitListener());
        currentScore = new JLabel(String.valueOf(playerOneScore));//gets current score for Jlabel
        currentScore.setFont(new Font("Serif", Font.BOLD, 30));//sets font and size to something not shitty
        scorePanel = new JPanel();//Makes new Panel
        scorePanel.setBorder(BorderFactory.createTitledBorder("Score"));//Titles border
        scorePanel.add(currentScore);
        makeWordPanel.add(currentWordPanel, BorderLayout.WEST);//setting up make word panel and sizing
        currentWordPanel.setPreferredSize(new Dimension(250, 25));//setting up make word panel and sizing
        makeWordPanel.add(submitButton, BorderLayout.CENTER);//setting up make word panel and sizing
        submitButton.setPreferredSize(new Dimension(50, 25));//setting up make word panel and sizing
        makeWordPanel.add(scorePanel, BorderLayout.EAST);//setting up make word panel and sizing
        scorePanel.setPreferredSize(new Dimension(250, 25));//setting up make word panel and sizing
        //Frame Stuff
        frame.add(dicePanel, BorderLayout.CENTER);//Makes Dice grid center becuase west did weird things
        frame.add(boggesPanel, BorderLayout.EAST);//Makes Text, Timer, and Shake Dice Button on the East
        frame.add(makeWordPanel, BorderLayout.SOUTH);
        makeWordPanel.setPreferredSize(new Dimension(2000, 125));
        frame.setVisible(true);//Don't Forget!
    }//initComponents

    public void finisher(int i) {
        buttons[i].setEnabled(false);
    }

    private class ExitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            UIManager.put("OptionPane.yesButtonText", "Propane!");//Propane is good so it means yes
            UIManager.put("OptionPane.noButtonText", "Charcoal!");//Charcoal is worse than Butane(A bastard gas) it means no
            int response = JOptionPane.showConfirmDialog(null, "Confirm to exit Peggy Hill's Boggle?",//It is peggy's favorite game after all
                    "Exit?", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int r, c, total;
            JButton btn = (JButton) e.getSource();//makes a local JButton of the one that was clicked
            String tempLetter = btn.getText();//gets the letter from JButton
            String tempWord;
            if (currentWordString != null) {//if at least one letter has been added to the current word
                tempWord = currentWordString + tempLetter;//the newest letter is added to the word
                currentWordString = tempWord;
                currentWord.setText(currentWordString);//updates the currentWord label
            }//
            if (currentWordString == null) {//if this is the first letter
                tempWord = tempLetter;//the word so far will just be the first letter then
                currentWordString = tempWord;
                currentWord.setText(currentWordString);//updates the currentWord label
            }
            c = (int) (btn.getClientProperty("column"));//pulls column number
            r = (int) (btn.getClientProperty("row"));//pulls row number
            total = c + (r * 4);//gets the button's number in the JButton Array
            blackList.add(total);//a list of buttons already clicked for this word (no repeats)
            for (int i = 0; i < 16; i++) {//will disable all buttons
                buttons[i].setEnabled(false);
            }//end for loop
            if (c == 1 || c == 2) {//for the middle two column
                int a, s, d, f, g, h, j, k;
                total = c + (r * 4);
                a = total - 1;
                s = total + 1;
                buttons[a].setEnabled(true);//button to the left is enabled
                buttons[s].setEnabled(true);//button to the right is enabled
                if (r != 3) {//if this isn't the bottom column
                    d = a + 4;//button to the left in the next row down is enabled
                    f = total + 4;//center button in the next row down is enabled
                    g = s + 4;//button to the right in the next row down is enabled
                    buttons[d].setEnabled(true);
                    buttons[f].setEnabled(true);
                    buttons[g].setEnabled(true);

                }//end r!= 3
                if (r != 0) {//if this isn't the top row
                    h = a - 4;//button to the right in the previous row is enabled
                    j = total - 4;//center button in the previous row is enabled
                    k = s - 4;//button to the left in the previous row is enabled
                    buttons[h].setEnabled(true);
                    buttons[j].setEnabled(true);
                    buttons[k].setEnabled(true);
                }//end r!= 0
            }//end c==1 || c==2
            if (c == 0) {//if this is the leftmost column
                int a, s, d, f, g, h, j, k;
                total = c + (r * 4);
                s = total + 1;//button to the right is enabled
                buttons[s].setEnabled(true);
                if (r != 3) {//if this isn't the rightmost column
                    f = total + 4;//center button in the next row down is enabled
                    g = s + 4;//button to the right in the next row down is enabled
                    buttons[f].setEnabled(true);
                    buttons[g].setEnabled(true);
                }//end r!= 3
                if (r != 0) {//if this isn't the left most column
                    j = total - 4;//center button on row up is enabled
                    k = s - 4;//button to the right on the row up is enabled
                    buttons[j].setEnabled(true);
                    buttons[k].setEnabled(true);
                }//end r!= 0
            }//end c==0
            if (c == 3) {
                int a, s, d, f, g, h, j, k;
                total = c + (r * 4);
                s = total - 1;//button to the left is enabled
                buttons[s].setEnabled(true);
                if (r != 3) {//if this isn't the rightmost column
                    f = total + 4;//center button in the next row down is enabled
                    g = s + 4;//button to the left in the next row down is enabled
                    buttons[f].setEnabled(true);
                    buttons[g].setEnabled(true);
                }//end r!= 3
                if (r != 0) {//if this isn't the left most column
                    j = total - 4;//center button in the previous row is enabled
                    k = s - 4;//button to the left in the previous row is enabled
                    buttons[j].setEnabled(true);
                    buttons[k].setEnabled(true);
                }//end r!= 0
            }//end c==3
            for (int i = 0; i < blackList.size(); i++) {
                buttons[blackList.get(i)].setEnabled(false);//goes through the list of buttons already chosen for this word and disables them
            }
        }
    }

    //}
    private class ShakeListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            timerTheTimer.cancel();
            board.shakeDice();
            //buttons = new JButton[16];//declares local Array
            playerOneScore = 0;//resets score
            currentScore.setText(String.valueOf(playerOneScore));//resets score label
            currentWordString = null;//resets current word
            interval = 180;//resets timer
            timerTheTimer = new Timer();
            timerTheTimer = TimerBullshit(timerTheTimer);
            wordsTextArea.setText(null);//clears jTextArea
            shakeButton.setEnabled(false);//disables shake button
            ArrayList<String> temp = board.getArrayList();//Makes a String ArrayList for the chosen Letters
            Collections.shuffle(temp);//shuffles the letters so dice 1 isn't in the first box and so on
            for (int i = 0; i < 16; i++) {
                buttons[i].setText(temp.get(i));//sets the chosen letters to the grid of dice
            }//end for loop
        }
    }//end ShakeListener  

    private class submitListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String wordCheck = null;
            int length = 0;
            wordCheck = currentWordString;//pulls current word in
            if (wordsData.contains(wordCheck)) {//checks if the current word is in the dictionary
                length = currentWordString.length();
                if (duplicateList.contains(wordCheck)) {
                    JOptionPane.showMessageDialog(frame, "You entered a duplicate word, You cannot double dip, I mean it's like putting your entire mouth in the dip!");
                    currentWordString = null;//sets current word to null
                    currentWord.setText(currentWordString);//updates current word text to null
                }//end if duplicate word check
                else {
                    duplicateList.add(wordCheck);
                    if (length == 3 || length == 4) {//if it is 3 or 4 it gets 1 point
                        playerOneScore = playerOneScore + 1;//adds score
                        wordsTextArea.append("\n" + currentWordString);//adds word to TextArea
                    } else if (length == 5) {//if 5 it gets 2 points
                        playerOneScore = playerOneScore + 2;//adds score
                        wordsTextArea.append("\n" + currentWordString);//adds word to TextArea
                    } else if (length == 6) {//if 6 it gets 3 points
                        playerOneScore = playerOneScore + 3;//adds score
                        wordsTextArea.append("\n" + currentWordString);//adds word to TextArea
                    } else if (length == 7) {//if 7 it gets 5 points
                        playerOneScore = playerOneScore + 5;//adds score
                        wordsTextArea.append("\n" + currentWordString);//adds word to TextArea
                    } else if (length > 7) {//if 8 or more it gets 11 points
                        playerOneScore = playerOneScore + 11;//adds score
                        wordsTextArea.append("\n" + currentWordString);//adds word to TextArea
                    } else {//if it was found but 1 or 2 letters it gets no points
                        wordsTextArea.append("\nWord was found but was too short, only words with 3 or more letters are allowed.");
                    }
                    currentWordString = null;//sets current word to null
                    currentWord.setText(currentWordString);//updates current word text to null
                    currentScore.setText(String.valueOf(playerOneScore));//updates current score
                }//end duplicate word check else    
            } else {//if the word was not found in the dictionary
                wordsTextArea.append("\nThat is not a word, you have failed.");//a taunt 
                currentWordString = null;
                currentWord.setText(currentWordString);
            }
            for (int i = 0; i < 16; i++) {//re-enables all the buttons
                buttons[i].setEnabled(true);
            }//end for loop
            blackList.clear();//clears all previously chosen buttons
        }
    }//end SubmitListener  

    private class newGameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            timerTheTimer.cancel();//cancels timer
            board.shakeDice();//gets new dice
            //buttons = new JButton[16];//declares local Array
            playerOneScore = 0;//resets score
            currentScore.setText(String.valueOf(playerOneScore));//updates label to reset score
            currentWordString = null;//resets currentWord
            timerTheTimer = new Timer();//starts a new timer
            interval = 180;//3 mins
            timerTheTimer = TimerBullshit(timerTheTimer);//timer stuff
            shakeButton.setEnabled(true);//re-enables shake button
            submitButton.setEnabled(true);//re-enables submit button
            wordsTextArea.setText("");//clears text area
            ArrayList<String> temp = board.getArrayList();//Makes a String ArrayList for the chosen Letters
            Collections.shuffle(temp);//shuffles the chosen letters
            for (int i = 0; i < 16; i++) {
                buttons[i].setEnabled(true);//re-enables all the buttons
                buttons[i].setText(temp.get(i));// = new JButton(temp.get(i));//Makes a button out of each letter, then ransfers to a local Array
                //dice.add(buttons[i]);//adds buttons to panel
            }//end for loop
        }//end actionPerformed
    }//end ShakeListener     

    public Timer TimerBullshit(Timer timerTheTimer) {
        //Scanner sc = new Scanner(System.in);
        secsString = "180";//180/60=3 minutes
        delayTime = 1000;
        periodTime = 1000;
        timerLabel.setText("3: 00");//a pretty start before the timer kicks in
        interval = Integer.parseInt(secsString);
        timerTheTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                //System.out.println(setInterval());
                int printTime, minutesTime, secondsTime;
                String secondsTimeString, minutesTimeString;
                printTime = setInterval();//gets number of seconds left
                minutesTime = printTime / 60;//finds minutes remaining
                secondsTime = printTime % 60;//finds seconds remaining
                minutesTimeString = String.valueOf(minutesTime);
                if (secondsTime == 0) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "00";
                } else if (secondsTime == 1) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "01";
                } else if (secondsTime == 2) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "02";
                } else if (secondsTime == 3) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "03";
                } else if (secondsTime == 4) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "04";
                } else if (secondsTime == 5) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "05";
                } else if (secondsTime == 6) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "06";
                } else if (secondsTime == 7) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "07";
                } else if (secondsTime == 8) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "08";
                } else if (secondsTime == 9) {//corrects printing seconds as a one digit number when they are below 10
                    secondsTimeString = "09";
                } else {//if no format corrections are needed it will just print
                    secondsTimeString = String.valueOf(secondsTime);
                }
                timerLabel.setText(minutesTimeString + ": " + secondsTimeString);
            }

        }, delayTime, periodTime);
        return timerTheTimer;
    }//endTimerBullshit

    private final int setInterval() {

        int minSize = 0, size = 0, computerWords = 0, wordLength;
        String tempWord;
        if (interval == 1) {//when time runs out
            timerTheTimer.cancel();//cancels timer
            wordsTextArea.append("\nGAME OVER");//tells the player the game is over
            shakeButton.setEnabled(false);//disables shakeDice Button
            submitButton.setEnabled(false);//disables submit Button
            for (int i = 0; i < 16; i++) {
                buttons[i].setEnabled(false);//disables the dice buttons
            }//end for loop
            JOptionPane.showMessageDialog(frame, "The Computer is comparing it's words to yours.");
            JOptionPane.showMessageDialog(frame, "Also a JTextArea cannot do strikethrough, so I took an alternate route");//K-dog you failed me
            size = duplicateList.size();
            computerWords = randomizer(minSize, size);//picks a random number between 0 and the number of words you found
            Collections.shuffle(duplicateList);//shuffles the words
            for (int i = 0; i < size; i++) {//starts a loop
                tempWord = duplicateList.get(i);//gets word
                wordLength = tempWord.length();//gets length
                if (i < computerWords) {//gives words to computer until it has found the number it wants
                    computerFoundList.add(tempWord);
                    scoreDestroyer(wordLength);//decreases your score based on that
                } else {//the words you uniquely found
                    yourFoundList.add(tempWord);//adds your words
                }
            }
            wordsTextArea.setText(null);//clears jTextArea
            wordsTextArea.append("\nBoth the computer and you found the following words, as a result neither of you get points for any of these words");
            for (int i = 0; i < computerFoundList.size(); i++) {//prints the words "found" by the computer
                wordsTextArea.append("\n" + computerFoundList.get(i));
            }
            wordsTextArea.append("\nOnly you found the following words");
            for (int i = 0; i < yourFoundList.size(); i++) {//prints the words uniquely found by you
                wordsTextArea.append("\n" + yourFoundList.get(i));
            }
            currentScore.setText(String.valueOf(playerOneScore));//shows the new score
            wordsTextArea.append("\nYour score has been updated to reflect which words were uniquely found by you");
        }//end if loop for final timer
        return --interval;//returns the time left minus 1 second
    }//end setInterval 

    public void scoreDestroyer(int length) {//subtracts the score based on the length of words the computer "found"
        if (length == 3 || length == 4) {//if it is 3 or 4 it gets 1 point
            playerOneScore = playerOneScore - 1;//subtracts score
        } else if (length == 5) {//if 5 it gets 2 points
            playerOneScore = playerOneScore - 2;//subtracts score
        } else if (length == 6) {//if 6 it gets 3 points
            playerOneScore = playerOneScore - 3;//subtracts score
        } else if (length == 7) {//if 7 it gets 5 points
            playerOneScore = playerOneScore - 5;//subtracts score
        } else if (length > 7) {//if 8 or more it gets 11 points
            playerOneScore = playerOneScore - 11;//subtracts score
        }
    }

    public int randomizer(int min, int max) {//find a random number between min and max
        Random rand = new Random();//new Random
        int randomNum = 0;
        randomNum = rand.nextInt((max - min) + 1) + min;//picks a random number
        return randomNum;
    }//end randomizer

}//BoggleUI class end
