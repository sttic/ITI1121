// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 3

public class Cube {

    // order of faces: UP, FRONT, RIGHT, BACK, LEFT, DOWN
    private Color[] faces;
    private Color[] original;
    private int step;

    public Cube(Color[] faces) {
        this.faces = new Color[faces.length];
        original = new Color[faces.length];

        for (int i = 0; i < faces.length; i++) {
            this.faces[i] = original[i] = faces[i];
        }

        step = 0;
    }

    public Cube(Cube other) {
        faces = original = new Color[other.faces.length];

        for (int i = 0; i < faces.length; i++) {
            faces[i] = other.faces[i];
            original[i] = other.original[i];
        }

        step = other.step;
    }

    public Cube copy() {
        return new Cube(this);
    }

    public Color getUp() {
        return faces[0];
    }

    public Color getFront() {
        return faces[1];
    }

    public Color getRight() {
        return faces[2];
    }

    public Color getBack() {
        return faces[3];
    }

    public Color getLeft() {
        return faces[4];
    }

    public Color getDown() {
        return faces[5];
    }

    public String toString() {
        return "[" + getUp() + ", " + getFront() + ", " + getRight() + ", " + getBack() + ", " + getLeft() + ", " + getDown() + "]";
    }

    public boolean hasNext() {
        return step < 24;
    }

    public void next() {
        if (step >= 24) {
            throw new IllegalStateException("The next() state for the cube does not exist");
        }

        if (step%4 != 0) {
            Rotate();
        } else if (step == 0) {
            Identity();
        } else if (step == 4 || step == 8 || step == 20) {
            RightRoll();
        } else if (step == 12 || step == 16) {
            LeftRoll();
        }
        step++;
    }

    public void reset() {
        for (int i = 0; i < faces.length; i++) {
            faces[i] = original[i];
        }

        step = 0;
    }

    private void Rotate() {
        // LEFT->FRONT , FRONT->RIGHT , RIGHT->BACK , BACK->LEFT
        Color temp = faces[4];

        faces[4] = faces[3];
        faces[3] = faces[2];
        faces[2] = faces[1];
        faces[1] = temp;

    }

    private void RightRoll() {
        // LEFT->UP, UP->RIGHT, RIGHT->DOWN, DOWN->LEFT
        Color temp = faces[4];

        faces[4] = faces[5];
        faces[5] = faces[2];
        faces[2] = faces[0];
        faces[0] = temp;

    }

    private void LeftRoll() {
        // RIGHT->UP, UP->LEFT, LEFT->DOWN, DOWN->RIGHT
        Color temp = faces[2];

        faces[2] = faces[5];
        faces[5] = faces[4];
        faces[4] = faces[0];
        faces[0] = temp;

    }

    private void Identity() {
        reset();
    }

}