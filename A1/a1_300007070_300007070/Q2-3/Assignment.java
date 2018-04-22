// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 1
// Question: 2.3

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
     * 		add the sample [(i, 2i), 5i] for 0&lt;=i&lt;=999
     * 		add the sample [(2i, i), 4i] for 0&lt;=i&lt;=999
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

        for (int i = 0; i < 10; i++) {
            System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
            System.out.println("Current cost: " + linearRegression.currentCost());
            System.out.println();
			linearRegression.gradientDescent(0.000000003, 1000);
        }
		System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
        System.out.println("Current cost: " + linearRegression.currentCost());
	}

	/**
     * In this second method, we will select a plane at random.
     * 	1) we select a line z = ax + by + c, with a, b and c
     * randomly selected between -100 and +100
     * 	2) we add 5000 samples randomly selected on the plane
     * with x and y both randomly selected between 50 and 4000.
     * For each sample we add a "noise"
     * randomly selected between -20 and +20 (that is, for
     * each randomly selected x and y we add the sample
     *[ (x,y), ax+by+c+noise).
     * where "noise" is randomly selected between -20 and 20
     *  4) we iterate gradient descent (find a number of iterations,
     * and a step alpha that seems to work, regularly printing
     * the target,  the current hypothesis and the current cost)
     */

	private static void randomPlane(){
        int samples = 5000;
		int dimension = 2;

        LinearRegression linearRegression = new LinearRegression(dimension, samples);

        double a = generator.nextDouble()*200 - 100;
        double b = generator.nextDouble()*200 - 100;
        double c = generator.nextDouble()*200 - 100;

        for (int i = 0; i < samples; i++) {
            double x1 = generator.nextDouble()*3950 + 50;
            double x2 = generator.nextDouble()*3950 + 50;
            double noise = generator.nextDouble()*40 - 20;

            double[] x = {x1, x2};
            double z = a*x[0] + b*x[1] + c + noise;

            linearRegression.addSample(x, z);
        }

		System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
        System.out.println("Current cost: " + linearRegression.currentCost());
        System.out.println("Aiming for: " + Double.toString(c) + " + " + Double.toString(a) + "x_1 + " + Double.toString(b) + "x_2");
        for (int i = 0; i < 8; i++) {
			System.out.println();
			linearRegression.gradientDescent(0.0000001, 32);
            System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
            System.out.println("Current cost: " + linearRegression.currentCost());
            System.out.println("Aiming for: " + Double.toString(c) + " + " + Double.toString(a) + "x_1 + " + Double.toString(b) + "x_2");
        }
	}

	/**
     * In this third method, we will follow the same approach
     * that the one followed in the method  randomPlane, but
     * this time we will have a variable number of dimensions,
     * specified by the parameter "dimension". We will
     * create 5000 samples of "dimension" dimension, where each
     * dimension will be ranmly selected between  -100 and +100,
     * and a randomly selected noise between -20 and +20 will be
     * added to the result.We will then iterate gradient descent
     * (find a number of iterations,
     * a number of updates to the instance of Display, and a
     * step alpha that seems to work (find a number of iterations,
     * and a step alpha that seems to work, regularly printing
     * the target,  the current hypothesis and the current cost)
     *
     * @param dimension the number of features
     */
	private static void randomDimension(int dimension){
        int samples = 5000;

        LinearRegression linearRegression = new LinearRegression(dimension, samples);

        double[] t = new double[dimension + 1];
        for (int i = 0; i < dimension + 1; i++) {
            t[i] = generator.nextDouble()*200 - 100;
        }

        for (int i = 0; i < samples; i++) {
            double[] x = new double[dimension];
            double r = t[0];
            double noise = generator.nextDouble()*40 - 20;

            for (int j = 0; j < dimension; j++) {
                x[j] = generator.nextDouble()*3950 + 50;
                r += t[j+1]*x[j];
            }

            linearRegression.addSample(x, r + noise);
        }

        // goal equation
        String h = Double.toString(t[0]);
		for (int i = 1; i < dimension + 1; i++) {
			h += " + " + Double.toString(t[i]) + "x_" + Integer.toString(i);
        }
        System.out.println("Aiming for: " + h + "\n");

		System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
        System.out.println("Current cost: " + linearRegression.currentCost());
        for (int i = 0; i < 8; i++) {
			System.out.println();
			linearRegression.gradientDescent(0.000000001, 1000);
            System.out.println("Current hypothesis: " + linearRegression.currentHypothesis());
            System.out.println("Current cost: " + linearRegression.currentCost());
        }

	}


	public static void main(String[] args) {

		StudentInfo.display();

		System.out.println("randomDimension");
		randomDimension(50);

	}

}
