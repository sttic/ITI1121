// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 2

import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems.
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough
 *  appropriate Getters.
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {

    private int widthOfGame;
    private int heigthOfGame;

    private int numberOfMines;
    private int numberOfSteps;
	private int numberUncovered;

    private DotInfo[][] model;

    /**
     * Constructor to initialize the model to a given size of board.
     *
     * @param width
     *            the width of the board
     *
     * @param heigth
     *            the heigth of the board
     *
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        widthOfGame = width;
        heigthOfGame = heigth;
        this.numberOfMines = numberOfMines;

        model = new DotInfo[widthOfGame][heigthOfGame];

        reset();
    }



    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up .
     */
    public void reset(){
        Random rand = new Random();
        numberOfSteps = 0;
		numberUncovered = 0;

        // initialize dots
        for (int i = 0; i < widthOfGame; i++) {
            for (int j = 0; j < heigthOfGame; j++) {
                model[i][j] = new DotInfo(i, j);
            }
        }

        // randomly set mine positions
        int minesSet = 0;
        while (minesSet < numberOfMines) {
            int x = rand.nextInt(widthOfGame);
            int y = rand.nextInt(heigthOfGame);
            if (!isMined(x, y)) {
                model[x][y].setMined();
                minesSet++;
            }
        }

        // set all the neighbouring mines values
        for (int i = 0; i < widthOfGame; i++) {
            for (int j = 0; j < heigthOfGame; j++) {
                if (!isMined(i, j)) {
                    int neighbooringMines = 0;
                    for (int a = i-1; a <= i+1; a++) {
                        for (int b = j-1; b <= j+1; b++) {
                            if (a >= 0 && a < widthOfGame && b >= 0 && b < heigthOfGame) {
                                if (isMined(a, b)) {
                                    neighbooringMines++;
                                }
                            }
                        }
                    }
                    model[i][j].setNeighbooringMines(neighbooringMines);
                }
            }
        }
    }


    /**
     * Getter method for the heigth of the game
     *
     * @return the value of the attribute heigthOfGame
     */
    public int getHeigth(){
        return heigthOfGame;
    }

    /**
     * Getter method for the width of the game
     *
     * @return the value of the attribute widthOfGame
     */
    public int getWidth(){
        return widthOfGame;
    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isMined(int i, int j){
        return model[i][j].isMined();
    }

    /**
     * returns true if the dot  at location (i,j) has
     * been clicked, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean hasBeenClicked(int i, int j){
        return model[i][j].hasBeenClicked();
    }

  /**
     * returns true if the dot  at location (i,j) has zero mined
     * neighboor, false otherwise
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isBlank(int i, int j){
        return getNeighbooringMines(i, j) == 0;
    }
    /**
     * returns true if the dot is covered, false otherwise
    *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public boolean isCovered(int i, int j){
        return model[i][j].isCovered();
    }

    /**
     * returns the number of neighbooring mines os the dot
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */
    public int getNeighbooringMines(int i, int j){
        return model[i][j].getNeighbooringMines();
    }


    /**
     * Sets the status of the dot at location (i,j) to uncovered
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void uncover(int i, int j){
        model[i][j].uncover();
		numberUncovered++;
    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void click(int i, int j){
        model[i][j].click();
    }
     /**
     * Uncover all remaining covered dot
     */
    public void uncoverAll(){
        for (int i = 0; i < widthOfGame; i++) {
            for (int j = 0; j < heigthOfGame; j++) {
                model[i][j].uncover();
            }
        }
    }



    /**
     * Getter method for the current number of steps
     *
     * @return the current number of steps
     */
    public int getNumberOfSteps(){
        return numberOfSteps;
    }



    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */
    public DotInfo get(int i, int j) {
        return model[i][j];
    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        numberOfSteps++;
    }

   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        return numberUncovered == (widthOfGame*heigthOfGame)-numberOfMines;
    }


   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        // cheats to show what each dot is in terminal

        // String s0 = "";
        // for (int j = 0; j < heigthOfGame; j++) {
        //     for (int i = 0; i < widthOfGame; i++) {
        //         if (isBlank(i, j)) {
        //             s0 += " ";
        //         } else if (isMined(i, j) && hasBeenClicked(i, j)) {
        //             s0 += "#";
        //         } else if (isMined(i, j)) {
        //             s0 += "*";
        //         } else {
        //             s0 += getNeighbooringMines(i, j);
        //         }
        //         s0 += " ";
        //     }
        //     s0 += "\n";
        // }
        // System.out.println(s0);

        String s = "";
        for (int j = 0; j < heigthOfGame; j++) {
            for (int i = 0; i < widthOfGame; i++) {
                if (isCovered(i, j)) {
                    s += "-";
                } else {
                    if (isBlank(i, j)) {
                        s += " ";
                    } else if (isMined(i, j) && hasBeenClicked(i, j)) {
                        s += "#";
                    } else if (isMined(i, j)) {
                        s += "*";
                    } else {
                        s += getNeighbooringMines(i, j);
                    }
                }
                s += " ";
            }
            s += "\n";
        }
        return s;
    }
}
