// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 3

public class Solution {

    private Cube[] cubes;
    private int numberOfCalls;

    public Solution(Cube[] cubes) {
        this.cubes = new Cube[cubes.length];
        for (int i = 0; i < cubes.length; i++) {
            this.cubes[i] = cubes[i];
        }

        numberOfCalls = 0;
    }

    public Solution(Solution other, Cube c) {
        if (c == null) {
            throw new NullPointerException("Cube can not be null");
        }

        if (other == null) {
            cubes = new Cube[]{c};
            numberOfCalls = 0;
        } else {
            cubes = new Cube[other.size() + 1];
            for (int i = 0; i < other.size(); i++) {
                cubes[i] = other.cubes[i].copy();
            }
            cubes[other.size()] = c.copy();

            numberOfCalls = other.getNumberOfCalls();
        }

    }

    public int size() {
        return cubes.length;
    }

    public Cube getCube(int pos) {
        return cubes[pos];
    }

    public boolean isValid() {
        numberOfCalls++;

        for (int i = 0; i < size(); i++) {
            for (int j = i+1; j < size(); j++) {
                if (cubes[i].getFront() == cubes[j].getFront() ||
                    cubes[i].getRight() == cubes[j].getRight() ||
                    cubes[i].getBack() == cubes[j].getBack() ||
                    cubes[i].getLeft() == cubes[j].getLeft()
                ) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isValid(Cube next) {
        numberOfCalls++;

        for (int i = 0; i < size(); i++) {
            if (cubes[i].getFront() == next.getFront() ||
                cubes[i].getRight() == next.getRight() ||
                cubes[i].getBack() == next.getBack() ||
                cubes[i].getLeft() == next.getLeft()
            ) {
                return false;
            }
        }
        return true;
    }


    public int getNumberOfCalls() {
        return numberOfCalls;
    }

    public void resetNumberOfCalls() {
        numberOfCalls = 0;
    }

    public String toString() {
        String s = "[" + cubes[0].toString();
        for (int i = 1; i < size(); i++) {
            s += ", " + cubes[i].toString();
        }
        return s + "]";
    }
}