// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 1
// Question: 2.1

/**
 * The class  <b>Assignment</b> is used to
 * test our LinearRegression class. It uses the
 * provided class <b>Display</b> to show the results
 *
 * @author gvj (gvj@eecs.uottawa.ca)
 *
 */

public class Assignment {


	/**
     * Random generator
     */
	private static java.util.Random generator = new java.util.Random();

    /**
     * In this first method, we are simply using sample points that are
     * on a straight plane. We will use the plane z= x + 2x.
     * In his method,
     * 	1) we create an instance of LinearRegression.
     * 	2) we add 2,000 samples from the plane z= x + 2x as follows:
     * 		add the sample [(i, 2i), 5i] for 0<=i<=999
     * 		add the sample [(2i, i), 4i] for 0<=i<=999
     *  3) We create an instance of Display
     *  4) we iterate gradient descent 10,000, printing out the
     * current hypothesis and the current cost every 1,000
     * iterations, using a step alpha of 0.000000003
     */
    private static void setPlane(){
        int samples = 2000;
        int dimension = 2;

        LinearRegression linearRegression = new LinearRegression(dimension, samples);

        for (int i = 0; i < samples/2; i++) {
            double[] x1 = {i, 2*i};
            double y1 = 5*i;
            linearRegression.addSample(x1, y1);

            double[] x2 = {2*i, i};
            double y2 = 4*i;
            linearRegression.addSample(x2, y2);
        }

		System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
        System.out.println("Current cost: " + linearRegression.currentCost());
        for (int i = 0; i < 10; i++) {
			System.out.println();
			linearRegression.gradientDescent(0.000000003, 1000);
            System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
            System.out.println("Current cost: " + linearRegression.currentCost());
        }
	}




	public static void main(String[] args) {

		StudentInfo.display();

		System.out.println("setPlane");
		setPlane();


	}

}
