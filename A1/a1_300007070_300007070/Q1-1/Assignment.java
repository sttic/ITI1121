// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 1
// Question: 1.1

/**
 * The class  <b>Assignment</b> is used to
 * test our LinearRegression class. It uses the
 * provided class <b>Display</b> to show the results
 *
 * @author gvj (gvj@eecs.uottawa.ca)
 *
 */

import java.util.Random;

public class Assignment {





	/**
     * In this first method, we are simply using sample points that are
     * on a straight line, namely y = x;
     * In his method,
     * 	1) we create an instance of LinearRegression.
     * 	2) we add 1,000 samples (from 0 to 999) from the line y=x
     *  3) We create an instance of Display
     *  4) we iterate gradient descent 5,000, updating the instance
     * of Display every 100 iteration, using a step alpha of 0.000000003
     */

    private static void setLine(){
        int points = 1000;
        LinearRegression linearRegression;
        linearRegression = new LinearRegression(points);

        for (int i = 0; i < points; i++) {
            linearRegression.addSample((double)i, (double)i);
        }

        Display graph;
        graph = new Display(linearRegression);


		System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
        System.out.println("Current cost: " + linearRegression.currentCost());
		graph.update();
        for (int i = 0; i < 50; i++) {
            linearRegression.gradientDescent(0.000000003, 100);
            System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
            System.out.println("Current cost: " + linearRegression.currentCost());
            graph.update();
        }



	}



	public static void main(String[] args) {

	    StudentInfo.display();

		System.out.println("setLine");
		setLine();

	}

}
