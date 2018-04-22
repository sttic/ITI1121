import javax.swing.*;
import java.awt.Graphics;
import java.util.*;


/**
 * The class  <b>Display</b> is used to illustrate 
 * 1 variable linear regression.
 * Usage: An instance of the class "LinearRegresion"
 * must be provided to the constructor. That object
 * LinearRegression is assumed that have its set of 
 * sample and the corresponding values already filled in.
 * Once the object Display is created, optionally a "target"
 * line can be provided using the method "setTarget". This
 * can be useful to see where the hypthesis is suppose to converge.
 * Whenever the the method ``update'' is called, the set of samples 
 * and the current hypthesis (and the target, if set) will be
 * drawn. This call is blocking, the user will have to enter
 * ``return'' for the computation to proceed.
 * This class relies on various getter methods of the class
 * LinearRegresion to access the current values.
 * 
 *
 * @author gvj (gvj@eecs.uottawa.ca)
 *
 */

public class Display extends JFrame {

    private static Scanner sc = new Scanner(System.in);

    private double[] x,y;
    private int xMin, xMax, yMin, yMax;
    private double targetA, targetB;
    private boolean targetSet;

    private LinearRegression linearRegression;
    private JPanel panel;
    private JLabel currentIteration;


    /** 
     * Object's contructor
     * 
     * @param linearRegression an instance of LinearRegression.
     * the samples and samplesvalues of that instance are assumed
     * to be populated.
     *
     */
    public Display(LinearRegression linearRegression) {
        super("Single Variable Linear Regression");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.linearRegression = linearRegression;
        targetSet = false;
        x = y = null;


        JPanel southPanel = new JPanel();
        currentIteration = new JLabel("Iteration 0 -- Current hypothesis: " + linearRegression.currentHypothesis() + " -- Cost: N/A");
        southPanel.setLayout(new java.awt.FlowLayout());
        southPanel.add(currentIteration);
        add(southPanel, java.awt.BorderLayout.SOUTH);

        setBounds(20, 20, 1100, 600);
        setVisible(true);       
    }


   /** 
     * Optional method to identify a target line
     * of the form y = ax + b. If set, the target line
     * will be draw, showing the progress of the hypothesis
     * towards that line
     * 
     * @param a From y = ax + b
     * @param b From y = ax + b
     *
     */
 
     public void setTarget(double a, double b) {
        targetSet = true;
        targetA = a;
        targetB = b;
    }

   /** 
     * This method will draw the set of samples stored in the 
     * variable linearRegression, as well as the current hypthesis
     * for it and the target line, if set.
     * It will then wait for a return from the user, blocking
     * the calling method
     */

      public void update() {
        if(x == null) {
            init();
        }
        repaint();
        System.out.println("Press return to continue....");
        sc.nextLine();
    }


    private void init(){
        xMin = xMax = yMin = yMax = 0;
        x = linearRegression.getSamples();
        y = linearRegression.getSamplesValues();
        
        for(int i =0; i < x.length; i++) {
            xMin = Math.min(xMin, (int)x[i]);
            xMax = Math.max(xMax, (int)x[i]);
            yMin = Math.min(yMin, (int)y[i]);
            yMax = Math.max(yMax, (int)y[i]);
        }

    }

    private int deltaX = 20;
    private int deltaY = 40;

    private int getX(int x){
        int dataWidth = (int)(xMax-xMin);
        int screenWidth = getWidth() - 2*deltaX;
        return (int) ((x-xMin)*screenWidth/dataWidth) + deltaX;
    }

    private int getY(int y){
        int dataHeight = (int)(yMax-yMin);
        int screenHeight = getHeight() - 2*deltaY;
        return getHeight() - (int) ((y-yMin)*screenHeight/dataHeight) - deltaY;
    }


    public void paint(Graphics g){
        super.paint(g);
        
        if(x == null) {
            init();
        }
        currentIteration.setText("Iteration " + linearRegression.getIteration() 
            + " -- hypothesis : " + linearRegression.currentHypothesis() 
            + " -- Error: " + linearRegression.currentCost());
       // axes
        g.drawLine(getX(xMin), getY(0), getX(xMax),getY(0));
        g.drawLine(getX(0), getY(yMin), getX(0),getY(yMax));


        // samples
        g.setColor(java.awt.Color.GRAY);

        for(int i =0 ; i < x.length; i++) {
            g.drawOval(getX((int)(x[i]-1)),getY((int)(y[i]-1)),2,2);
        }
     

        // target
        if(targetSet) { 
            g.drawLine(getX(xMin), getY((int)(targetA*(xMin)+targetB)),
              getX(xMax), getY((int)(targetA*(xMax)+targetB)));
        }
 
        //hypothesis
        g.setColor(java.awt.Color.RED);

        g.drawLine(getX(xMin), 
            getY((int)(linearRegression.getTheta1()*(xMin)+linearRegression.getTheta0())),
            getX(xMax), 
            getY((int)(linearRegression.getTheta1()*(xMax)+linearRegression.getTheta0())));
       
    }
}