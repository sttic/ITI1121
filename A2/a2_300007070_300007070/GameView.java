// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 2

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {

    private GameModel gameModel;

    private DotButton[][] dots;

    private JPanel grid, control;
    private JLabel counter;
    public JButton reset, quit;

    /**
     * Constructor used for initializing the Frame
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController) {
        super("Minesweeper -- ITI1121 Version (Tommy Deng - 300007070)");
        this.gameModel = gameModel;

        int width = gameModel.getWidth();
        int height = gameModel.getHeigth();

        dots = new DotButton[width][height];

        // PANEL FOR DOTS
        grid = new JPanel(new GridLayout(height, width, 0, 0));
        grid.setBorder(new EmptyBorder(5, 20, 5, 20));
        grid.setBackground(Color.WHITE);
        // add all the buttons for the grid
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                dots[i][j] = new DotButton(i, j, getIcon(i, j));
                dots[i][j].setBorder(BorderFactory.createEmptyBorder());
                dots[i][j].addActionListener(gameController);
                grid.add(dots[i][j]);
            }
        }

        // LABEL & BUTTON
        counter = new JLabel("Number of steps: 0");

        reset = new JButton("Reset");
        reset.setBackground(Color.WHITE);
        reset.addActionListener(gameController);

        quit = new JButton("Quit");
        quit.setBackground(Color.WHITE);
        quit.addActionListener(gameController);

        // PANEL FOR CONTROLS (the label, reset button, and quit button)
        control = new JPanel();
        control.setBackground(Color.WHITE);
        control.add(counter);
        control.add(reset);
        control.add(quit);

        // FRAME
        add(control, BorderLayout.SOUTH);
        add(grid, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }

    /**
     * update the status of the board's DotButton instances based
     * on the current game model, then redraws the view
     */

    public void update(){
        counter.setText("Number of steps: " + gameModel.getNumberOfSteps());
        for (int i = 0; i < gameModel.getWidth(); i++) {
            for (int j = 0; j < gameModel.getHeigth(); j++) {
                dots[i][j].setIconNumber(getIcon(i, j));;
            }
        }
    }

    /**
     * returns the icon value that must be used for a given dot
     * in the game
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */
    private int getIcon(int i, int j){
        if (gameModel.get(i, j).isCovered()) {
            return 11;
        } else if (gameModel.get(i, j).isMined()) {
            if (gameModel.get(i, j).hasBeenClicked()) {
                return 10;
            }
            return 9;
        }

        return gameModel.get(i, j).getNeighbooringMines();
    }


}
