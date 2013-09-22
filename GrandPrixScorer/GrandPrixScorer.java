import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
/*
 * This program allows its user to enter racing data
 * and receive back randomized heats and score information
 * as well as a calculation of the final contestants.
 * 
 * @author: Bridgette Kuehn <bridgettekuehn@gmail.com>
 * Last Update 9-15-2013
 */


public class GrandPrixScorer {
    JTextArea textArea = new JTextArea();
    JTextArea scoreArea = new JTextArea();
    JTextArea lineupArea = new JTextArea();
    JLabel prompt = new JLabel();
    JTextField userResponse = new JTextField(20);
    JButton enter = new JButton();
    JButton shuffle = new JButton();
    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel bgPanel = new JPanel();
    JPanel mainPanel = new JPanel();
    JPanel lineupPanel = new JPanel();
    JPanel scoresPanel = new JPanel();
    int numCars;
    int numLanes;
    int numRounds;
    int numHeats;
    int keepCount = 0;
    int roundCount = 0;
    int heatCount = 0;
    int indexCount = 0;
    int carCount = 0;
    int numFinalists;
    String[] nameArray;
    String[][] holderNameArray;
    int[] scores;
    int[][] holderScoreArray;
    int[] listScores;
    int[] totalScores;
    String[] placeNames;
    String[] orderNames;
    int[] finalScores;
    ArrayList<String> finalArrayList;
    boolean go;
    
    public static void main(String[] args) {
        GrandPrixScorer g = new GrandPrixScorer();
        g.uISetup();
        g.getNumCars();
    }
    
    //Sets up the user interface. Creates a window the program will run in and places the main components.
    void uISetup() {
        JFrame f = new JFrame("Grand Prix Scorer");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        f.setSize(800,600);
        content.setBackground(Color.white);
        bgPanel.setLayout( new BorderLayout());
        content.add(bgPanel);
        mainPanelSetup();
        lineupPanelSetup();
        tabbedPane.addTab("Main Screen",mainPanel);
        tabbedPane.addTab("Lineup", lineupPanel);
        tabbedPane.addTab("Scores", scoresPanel);
        bgPanel.add(tabbedPane, BorderLayout.CENTER);
        f.setVisible(true);
    }
    
    //creats components for the main interacting screen
    void mainPanelSetup() {
        GroupLayout layout = new GroupLayout(mainPanel);
        textArea.setText("\n\n\t\t\tWELCOME TO GRAND PRIX SCORER!\n\t\t\t      Copyright Bridgette Kuehn, 2013");
        textArea.append("\n\n\n\tTo begin, please enter the number of cars below.");
        textArea.setBorder(BorderFactory.createLineBorder(Color.black));
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        scrollPane.setPreferredSize(new Dimension(600,400));
        textArea.setEditable(false);
        userResponse.setMaximumSize(new Dimension(200,100));
        enter.setText("Enter");
        prompt.setText("Number of cars:");
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                                  layout.createParallelGroup()
                                      .addComponent(scrollPane)
                                      .addGroup(layout.createSequentialGroup()
                                                    .addComponent(prompt)
                                                    .addComponent(userResponse)
                                                    .addComponent(enter))
                                 );
        layout.setVerticalGroup(
                                layout.createSequentialGroup()
                                    .addComponent(scrollPane)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                  .addComponent(prompt)
                                                  .addComponent(userResponse)
                                                  .addComponent(enter))
                               );
        mainPanel.setLayout(layout); 
    }
    
    //creates and places components that will later show the lineup for each heat and round
    void lineupPanelSetup() {
        BoxLayout bLayout = new BoxLayout(lineupPanel,BoxLayout.LINE_AXIS);
        lineupArea.setBorder(BorderFactory.createLineBorder(Color.black));
        JScrollPane scrollPane = new JScrollPane(lineupArea);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        lineupArea.setEditable(false);
        lineupArea.setText(" You haven't entered in your information yet. Please go back to the Main screen.");
        lineupPanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    //sets up the score screen components. Displays previously entered scores
    void scoresPanelSetup() {
        keepCount = 0;
        listScores = new int[numCars*numRounds];
        placeNames = new String[numCars];
        GridLayout gLayout = new GridLayout(numCars+2,numRounds+1);
        JLabel[] arrayLabels = new JLabel[((numCars+1)*(numRounds+1))];
        int currentIndex = 1;
        int scoreIndex = 0;
        arrayLabels[0] = new JLabel("");
        for(int r = 1; r < numRounds+1;r++) {
            arrayLabels[r] = new JLabel("Round " + r);
            currentIndex++;
        }
        for(int i = 0; i < numCars;i++) {
            scores = checkNameMatch(holderNameArray[0][i],holderNameArray);
            arrayLabels[currentIndex] = new JLabel(holderNameArray[0][i]);
            placeNames[i] = holderNameArray[0][i];
            currentIndex++;
            for(int s = 0; s < numRounds*2;s = s + 2) {
                arrayLabels[currentIndex] = new JLabel("" +holderScoreArray[scores[s]][scores[s+1]]);
                listScores[keepCount] = holderScoreArray[scores[s]][scores[s+1]];
                currentIndex++;
                keepCount++;
            }
        }
        for(int a = 0; a < arrayLabels.length;a++) {
            scoresPanel.add(arrayLabels[a]);
        }
        scoresPanel.setLayout(gLayout);
        getFinalists();
    }
    //get the number of cars
    void getNumCars() {
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCars = Integer.parseInt(userResponse.getText());
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                getNumLanes();
            }
        });  
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numCars = Integer.parseInt(userResponse.getText());
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                getNumLanes();
            }
        });
    }
    //get the number of lanes
    void getNumLanes() {
        prompt.setText("Number of Lanes:");
        userResponse.setText("");
        textArea.setText("You have entered " + numCars + " cars. Please enter the number of lanes you have available.");
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numLanes = Integer.parseInt(userResponse.getText());
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                getNumHeats();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numLanes = Integer.parseInt(userResponse.getText());
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                getNumHeats();
            }
        });
    }
    //calculate number of heats
    void getNumHeats() {
        int remainder = numCars % numLanes;
        numHeats = (numCars-remainder)/numLanes;
        if(remainder >= 1) {
            numHeats++;
        }
        textArea.append("\n\n\nWith " + numCars + " cars and " + numLanes + " lanes you will need " + numHeats + " heats. ");
        getRounds();
    }
    //get number of rounds
    void getRounds() {
        prompt.setText("Number of Rounds:");
        userResponse.setText("");
        textArea.append("\nHow many rounds would you like?");
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numRounds = Integer.parseInt(userResponse.getText());
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                nameCars();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numRounds = Integer.parseInt(userResponse.getText());
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                nameCars();
            }
        });
    }
    //allow users the option of naming contestants
    void nameCars() {
        userResponse.setText("y");
        prompt.setText("Would you like to name the cars?");
        textArea.setText("Now that we have all the needed information, would you like to enter names for each of the cars? Entering names can be helpful in later identification.\nIf you would like to enter names, please press 'y'. If you would not like to enter names, press 'n' and names will be automatically generated for you.");
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                nameArray = new String[numCars];
                if(userResponse.getText().equals("y")) {
                    textArea.setText("You have chosen to enter the names for your cars.");
                    enterNames();
                } else {
                    automatedNames();
                }
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                nameArray = new String[numCars];
                if(userResponse.getText().equals("y")) {
                    textArea.setText("You have chosen to enter the names for your cars.");
                    enterNames();
                } else {
                    automatedNames();
                }
            }
        });
    }
    //user enters names manually 
    void enterNames() {
        prompt.setText("Name for Car " + (keepCount+1));
        userResponse.setText("");
        textArea.append("\nCar " + (keepCount+1) + " name is: ");
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameArray[keepCount] = userResponse.getText();
                textArea.append(nameArray[keepCount]);
                keepCount++;
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                repeatEnterNames();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameArray[keepCount] = userResponse.getText();
                textArea.append(nameArray[keepCount]);
                keepCount++;
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                repeatEnterNames();
            }
        });
    }
    //helps to repeat the user entering manually due to anonymous class restrictions
    void repeatEnterNames() {
        if(keepCount < numCars) {
            enterNames();
        } else {
            generateHeats();
        }
    }
    //automates car naming process
    void automatedNames() {
        textArea.setText("You have chosen to have us generate names.");
        for(int i = 0; i < numCars;i++) {
            textArea.append("\nCar " + (i+1) + " name is: Car " + (i+1));
            nameArray[i] = "Car " + (i+1);
        }
        generateHeats();
    }
    //creates the lineup given the number of cars, lanes, and rounds wanted. Displays lineup in Lineup tab
    void generateHeats() {
        textArea.append("\n\nWe have now made up a possible linup for your racers using " + numRounds + " rounds with " + numHeats + " heats. Click on the Lineup tab to view the roster.\nWhen you're ready to enter the scores for each round, please click enter. Keep in mind you can view your Lineup at anytime after this point by selecting the Lineup tab.");
        prompt.setText("Are you ready to enter your scores?");
        userResponse.setText("Bring it on!");
        //creating heats here
        String[][] arrayOfNameArrays = new String[numRounds][numCars];
        lineupArea.setText("");
        for(int i = 0; i < numRounds;i++) {
            Collections.shuffle(Arrays.asList(nameArray));
            int lastPosition = 0;
            lineupArea.append("\nRound " + (i+1) + ":");
            if (numCars % numLanes == 0) {    
                for(int h = 0; h < numHeats;h++) {
                    lineupArea.append("\n\tHeat " + (h+1) + ":");
                    for(int l = 0; l < numLanes;l++) {
                        lineupArea.append("\n\t\tPosition " + (l+1) + ":" + nameArray[lastPosition]);
                        arrayOfNameArrays[i][lastPosition] = nameArray[lastPosition];
                        lastPosition++;
                    }
                }
            } else {
                for(int h = 0; h < numHeats-1;h++) {
                    lineupArea.append("\n\tHeat " + (h+1) + ":");
                    for(int l = 0; l < numLanes;l++) {
                        lineupArea.append("\n\t\tPosition " + (l+1) + ":" + nameArray[lastPosition]);
                        arrayOfNameArrays[i][lastPosition] = nameArray[lastPosition];
                        lastPosition++;
                    }
                }
                lineupArea.append("\n\tHeat " + numHeats + ":" );
                for(int n = 0; n <= numCars - lastPosition;n++) {
                    lineupArea.append("\n\t\tPosition " + (n + 1) + ":" + nameArray[lastPosition]);
                    arrayOfNameArrays[i][lastPosition] = nameArray[lastPosition];
                    lastPosition++;
                }
            }
        }
        //finished creating heats
        holderNameArray = arrayOfNameArrays;
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                int[][] arrayOfScoreArrays = new int[numRounds][numCars];
                holderScoreArray = arrayOfScoreArrays;
                textArea.setText("You will need to enter the scores for each racer in each heat and round. They will be listed in the orders listed in the Lineup tab. Enter 1 for first, 2 for second, 3 for third, etc.");
                enterScores();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                int[][] arrayOfScoreArrays = new int[numRounds][numCars];
                holderScoreArray = arrayOfScoreArrays;
                textArea.setText("You will need to enter the scores for each racer in each heat and round. They will be listed in the orders listed in the Lineup tab. Enter 1 for first, 2 for second, 3 for third, etc.");
                enterScores();
            }
        });
    }
    //allows user to enter the places for each car in each heat in each round
    void enterScores() {
        if(indexCount == 0 || (indexCount) % numLanes == 0) {
            textArea.append("\nRound " + (roundCount + 1) + " Heat " + (heatCount+1) + ": ");
        }
        userResponse.setText("");
        prompt.setText(holderNameArray[roundCount][indexCount] + ": ");
        textArea.append("\n" + holderNameArray[roundCount][indexCount] + ": ");
        userResponse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                holderScoreArray[roundCount][indexCount] = Integer.parseInt(userResponse.getText());
                textArea.append("" + holderScoreArray[roundCount][indexCount]);
                indexCount++;
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                repeatEnterScores();
            }
        });
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                holderScoreArray[roundCount][indexCount] = Integer.parseInt(userResponse.getText());
                textArea.append("" + holderScoreArray[roundCount][indexCount]);
                indexCount++;
                for(ActionListener act : enter.getActionListeners()) {
                    enter.removeActionListener(act);
                }
                for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                repeatEnterScores();
            }
        });
        
    }
    //helps repeat enterScores() due to anon class restrictions
    //checks to change the number of heats/rounds
    void repeatEnterScores() {
        if((indexCount) % numLanes == 0 || indexCount == numCars) {
            heatCount++;
        }
        if(indexCount == numCars) {
            indexCount = 0;
            heatCount = 0;
            roundCount++;
        }
        if(roundCount < numRounds) {
            enterScores();
        } else {
            textArea.setText("You may now click the Scores tab to see the scores you have just entered.");
            scoresPanelSetup();
        }
    }
    //used with scores to match car names to car score data
    int[] checkNameMatch(String nameA, String[][] nameB) {
        int count = 0;
        int[] matches = new int[numRounds*2];  
        for(int r = 0; r < numRounds;r++) {
            for(int c = 0; c < numCars;c++) {
                if (nameA.equals(nameB[r][c])) {
                    matches[count] = r;
                    matches[count+1] = c;
                    count = count + 2;
                }
            }
        }
        return matches;
    }
    //calculates the car total scores and puts them in order from highest to lowest
    void getFinalists() {
        userResponse.setText("");
        totalScores = new int[numCars];
        orderNames = new String[numCars];
        finalScores = new int[numCars];
        boolean[] used = new boolean[numCars];
        for(int c = 0; c < numCars;c++) {
            used[c] = false;
        }
        int addScores = 0;
        keepCount = 0;
        for(int i = 0; i < numCars;i++) {
            for(int s = 0; s < numRounds;s++) {
                addScores = addScores + listScores[keepCount];
                keepCount++;
            }
            totalScores[i] = addScores;
            addScores = 0;
        }
        int lowest = 1000000;
        int lowestIndex = 0;
        for(int t = 0; t < numCars;t++) {
            for(int i = 0; i < numCars;i++) {
                if(totalScores[i] < lowest && !used[i]) {
                    lowest = totalScores[i];
                    lowestIndex = i;
                } else if(totalScores[i] == lowest && !used[i]) {
                    if(t == 0) {
                        lowest = totalScores[i];
                        lowestIndex = i;
                    } else if (finalScores[t-1] == lowest) {
                        lowest = totalScores[i];
                        lowestIndex = i;
                    }
                }
            }
            finalScores[t] = lowest;
            orderNames[t] = placeNames[lowestIndex];
            used[lowestIndex] = true;
            lowest = 1000000;
        }
        textArea.append("\n\nHere are your cars final scores using the data from the Scores tab: ");
        for(int i = 0; i < numCars;i++) {
            textArea.append("\nPlace " + (i+1) + ": " + orderNames[i] + " with a score of " + finalScores[i]);
        }
        textArea.append("\n\nThese contestants are in order from 1st place to last as of the current heats.");
        if(finalScores[numLanes-1] < finalScores[numLanes]) {
            numFinalists = numLanes;
            calculateFinals();
        } else {
            int highestScore = finalScores[numLanes-1];
            int index = 0;
            for(int i = 0; i < finalScores.length;i++) {
                if(finalScores[i] == highestScore) {
                    index = i;
                }
            }
            numFinalists = index + 1;
            calculateFinals();
        }
    }
    void calculateFinals() {
        textArea.setText("Here are your finalists: ");
        String[] finalistNames = new String[numFinalists];
        for(int i = 0; i < numFinalists;i++) {
            textArea.append("\n" + (i+1) + ": " + orderNames[i]);
            finalistNames[i] = orderNames[i];
        }
        //only one heat to decide the winner
        if(numFinalists <= numLanes) {
            prompt.setText("");
            userResponse.setVisible(false);
            enter.setVisible(false);
            textArea.append("\nYou will only need one final heat!\nPlease check the Lineup tab for an updated final lineup!");
            Collections.shuffle(Arrays.asList(finalistNames));
            lineupArea.append("\nFINAL ROUND:\n\tFINAL HEAT:");
            for(int i = 0; i < numFinalists;i++) {
                lineupArea.append("\n\t\tPosition " + (i+1) + ": " + finalistNames[i]);
            }
            textArea.append("\n\nAnd you're finished! The winner of this heat is the overall winner! Congrats to them!");
        } 
        else { //there are way too many people in the finals, yo. or they only have one lane in which case I respond WHAAAA
            tournamentTime(finalistNames);
        }
    }
    void tournamentTime(String[] finalists) {
        finalArrayList = new ArrayList();
        int numFinalHeats = (finalists.length - (finalists.length % numLanes))/numLanes;
        if(finalists.length % numLanes >= 1) {
            numFinalHeats++;
        }
        int numFinalRounds;
        if(numFinalHeats <= numLanes) {
            numFinalRounds = 1;
        } else {
            numFinalists = numFinalHeats;
            numFinalRounds = 1;
            do {
                numFinalists = (numFinalists - (numFinalists%numLanes))/numLanes;
                numFinalRounds++;
            } while(numFinalists > numLanes);
        }
        textArea.append("\nYou will need " + numFinalRounds + " rounds for your finals. In this situation, the winner of each heat will move on to the next round. The final round will give the winner.");
        keepCount = 0;
        indexCount = 0;
        Collections.shuffle(Arrays.asList(finalists));
        do {
            userResponse.setText("");
            keepCount++;
            lineupArea.append("\nFINAL ROUND " + keepCount + ": ");
            for(int h = 0; h < numFinalHeats;h++) {
                lineupArea.append("\n\tFINAL HEAT " + (h+1) + ": ");
                for(int p = 0; p < numLanes;p++) {
                    lineupArea.append("\n\t\tPosition " + (p+1) + ": " + finalists[indexCount]);
                    if(indexCount == finalists.length-1) {
                        p = numLanes;
                    } else {
                    indexCount++;
                    }
                }
            }
            textArea.append("\nYour Lineup tab has been updated to accomodate Round " + keepCount + ". When you are ready to enter the winners of each heat to move on to the next round, please enter the names of their cars EXACTLY as they appear on the score sheet and lineup.");
            for(int h = 0; h < numFinalHeats;h++) {
                go = false;
                prompt.setText("Please enter the winner of Heat " + (h+1) +": ");
                textArea.append("\nHeat " + (h+1) + " winner: ");
                do{
                    userResponse.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(keepCount > 1) {
                                finalArrayList.clear();
                            }
                            finalArrayList.add(userResponse.getText());
                            textArea.append(userResponse.getText());
                            for(ActionListener act : enter.getActionListeners()) {
                                enter.removeActionListener(act);
                            }
                            for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                            go = true;
                        }
                    });
                    enter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(keepCount > 1) {
                                finalArrayList.clear();
                            }
                            finalArrayList.add(userResponse.getText());
                            textArea.append(userResponse.getText());
                            for(ActionListener act : enter.getActionListeners()) {
                                enter.removeActionListener(act);
                            }
                            for(ActionListener act : userResponse.getActionListeners()) {
                                userResponse.removeActionListener(act);
                            }
                            go = true;
                        }
                    });
                } while(!go);
            }
        } while(keepCount < numFinalRounds-1);
    }
}