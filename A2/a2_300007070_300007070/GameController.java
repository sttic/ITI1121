// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 2

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {
    private GameModel gameModel;
    private GameView gameView;

    /**
     * Constructor used for initializing the controller. It creates the game's view
     * and the game's model instances
     *
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {
        gameModel = new GameModel(width, height, numberOfMines);
        gameView = new GameView(gameModel, this);
    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // if user clicked a dot
        if (src instanceof DotButton) {
            DotButton dot = (DotButton)src;

            int i = dot.getColumn();
            int j = dot.getRow();
            DotInfo df = gameModel.get(i, j);

            if (!df.hasBeenClicked()) {
                gameModel.step();
                play(i, j);
                if (df.getNeighbooringMines() == 0) {
                    clearZone(df);
                }
            }

            String coord = "coord=(" + i + ", " + j + ")";
            String mined = "mined=" + df.isMined();
            String neighbouringMines = "neighbouringMines=" + df.getNeighbooringMines();
			String uncovered = "uncovered=";

            System.out.println("DotInfo: " + coord + ", " + mined + ", " + neighbouringMines);

        // if user clicked on bottom reset or quit button
        } else if (src instanceof JButton) {
            JButton control = (JButton)src;
            System.out.println(control.getText());

            if (src == gameView.reset) {
                reset();
            } else if (src == gameView.quit) {
                System.exit(0);
            }
        }

        gameView.update();
        System.out.println(gameModel.toString());
    }

    /**
     * resets the game
     */
    private void reset(){
        gameModel.reset();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares.
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */

    private void play(int width, int heigth){
        gameModel.click(width, heigth);
        gameModel.uncover(width, heigth);

        String[] options = {"Reset", "Quit"};
        int value = -1;

        // LOSS
        if (gameModel.isMined(width, heigth)) {
            gameModel.uncoverAll();
            gameView.update();

            System.out.println("You lost on step " + gameModel.getNumberOfSteps());
            value = JOptionPane.showOptionDialog(null, "BOOM! You clicked a mine in " + gameModel.getNumberOfSteps() + " steps.", "You lost", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        // WIN
        } else if (gameModel.isFinished()) {
            gameModel.uncoverAll();
            gameView.update();

            System.out.println("You won on step " + gameModel.getNumberOfSteps());
            value = JOptionPane.showOptionDialog(null, "YAY! You won in " + gameModel.getNumberOfSteps() + " steps.", "You won", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }

        // POP-UP
        if (value == 0) {
            reset();
        } else if (value == 1) {
            System.exit(0);
        }
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered''
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {
        GenericArrayStack<DotInfo> stack = new GenericArrayStack<DotInfo>(gameModel.getWidth()*gameModel.getHeigth());

        stack.push(initialDot);

        while(!stack.isEmpty()) {
            DotInfo d = stack.pop();
            int i = d.getX();
            int j = d.getY();

            for (int a = i-1; a <= i+1; a++) {
                for (int b = j-1; b <= j+1; b++) {
                    if (a >= 0 && a < gameModel.getWidth() && b >= 0 && b < gameModel.getHeigth()) {
                        if (gameModel.isCovered(a, b)) {
                            play(a, b);
                            if (gameModel.getNeighbooringMines(a, b) == 0) {
                                stack.push(gameModel.get(a, b));
                            }
                        }
                    }
                }
            }

        }

    }

}
