// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 3

public class Solve {
    private static final StudentInfo info = new StudentInfo();
    private static final Cube[] ORIGINAL = new Cube[]{
        new Cube(new Color[]{Color.BLUE, Color.GREEN, Color.WHITE, Color.GREEN, Color.BLUE, Color.RED}),
        new Cube(new Color[]{Color.WHITE, Color.GREEN, Color.BLUE, Color.WHITE, Color.RED, Color.RED}),
        new Cube(new Color[]{Color.GREEN, Color.WHITE, Color.RED, Color.BLUE, Color.RED, Color.RED}),
        new Cube(new Color[]{Color.BLUE, Color.RED, Color.GREEN, Color.GREEN, Color.WHITE, Color.WHITE})
    };

    public static Queue<Solution> generateAndTest() {
        Cube[] cubes = new Cube[ORIGINAL.length];
        for (int i = 0; i < ORIGINAL.length; i++) {
            cubes[i] = ORIGINAL[i].copy();
        }

        Queue<Solution> solutions = new LinkedQueue<Solution>();
        Solution s = new Solution(cubes);

        int cases = 24;

        for (int i = 0; i < cases; i++) {
            s.getCube(0).next();

            for (int j = 0; j < cases; j++) {
                if (!s.getCube(1).hasNext()) {
                    s.getCube(1).reset();
                }
                s.getCube(1).next();

                for (int k = 0; k < cases; k++) {
                    if (!s.getCube(2).hasNext()) {
                        s.getCube(2).reset();
                    }
                    s.getCube(2).next();

                    for (int l = 0; l < cases; l++) {
                        if (!s.getCube(3).hasNext()) {
                            s.getCube(3).reset();
                        }
                        s.getCube(3).next();

                        if (s.isValid()) {
                            System.out.println(s.toString());
                            solutions.enqueue(s);
                        }
                    }
                }
            }
        }

        System.out.println("Number of isValid() calls: " + s.getNumberOfCalls());
        return solutions;
    }

    public static Queue<Solution> breadthFirstSearch() {
        Cube[] cubes = new Cube[ORIGINAL.length];
        for (int i = 0; i < ORIGINAL.length; i++) {
            cubes[i] = ORIGINAL[i].copy();
        }

        Queue<Solution> open = new LinkedQueue<Solution>();
        Queue<Solution> solutions = new LinkedQueue<Solution>();
        int count = 0;

        Cube cube = cubes[0].copy();
        while (cube.hasNext()) {
            cube.next();
            Solution s = new Solution(null, cube.copy());
            open.enqueue(s);
        }

        while (!open.isEmpty()) {
            Solution current = open.dequeue();
            current.resetNumberOfCalls();
            cube = cubes[current.size()].copy();

            while (cube.hasNext()) {
                cube.next();

                if (current.isValid(cube)) {
                    Solution s = new Solution(current, cube.copy());
                    if (s.size() == 4) {
                        System.out.println(s.toString());
                        solutions.enqueue(s);
                    } else {
                        open.enqueue(s);
                    }
                }
            }

            count += current.getNumberOfCalls();
        }

        System.out.println("Number of isValid() calls: " + count);
        return solutions;
    }

    public static void main(String[] args) {
        info.display();

        long start, stop;

        System.out.println("generateAndTest:");
        start = System.currentTimeMillis();

        generateAndTest();

        stop = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (stop - start) + " milliseconds");

        System.out.println("breadthFirstSearch:");
        start = System.currentTimeMillis();

        breadthFirstSearch();

        stop = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (stop - start) + " milliseconds");
    }
}