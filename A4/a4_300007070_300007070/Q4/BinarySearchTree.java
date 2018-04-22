// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 4

/**
*  @author Marcel Turcotte
*/

import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<E>> {

    private static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;
        private Node(T value) {
            this.value = value;
            left = null;
            right = null;
        }

    }

    private Node<E> root = null;

    /**
    * Inserts an object into this BinarySearchTree.
    *
    * @param elem item to be added
    * @return true if the object has been added and false otherwise
    */

    public boolean add(E elem) {

        // pre-condtion:

        if (elem == null) {
            throw new NullPointerException();
        }

        // special case:

        if (root == null) {
            root = new Node<E>(elem);
            return true;
        }

        // general case:

        return add(elem, root);
    }

    // Helper method

    private boolean add(E elem, Node<E> current) {

        boolean result;
        int test = elem.compareTo(current.value);

        if (test == 0) {
            result = false; // already exists, not added
        } else if (test < 0) {
            if (current.left == null) {
                current.left = new Node<E>(elem);
                result = true;
            } else {
                result = add(elem, current.left);
            }
        } else {
            if (current.right == null) {
                current.right = new Node<E>(elem);
                result = true;
            } else {
                result = add(elem, current.right);
            }
        }
        return result;
    }

    /**
     * Counts the number of elements within a range (inclusive).
     *
     * @param low Lowest value that is counted.
     *
     * @param high Highest value that is counted.
     *
     * @return The number of values within the low and high range.
     */
    public int count(E low, E high) {
        if (root == null || low == null || high == null) {
            throw new NullPointerException();
        }

        return count(root, low, high);
    }

    /**
     * Counts the number of elements within a range (inclusive).
     *
     * @param current Node at current location in tree.
     *
     * @param low Lowest value that is counted.
     *
     * @param high Highest value that is counted.
     *
     * @return The number of values within the low and high range.
     */
    private int count(Node<E> current, E low, E high) {
        boolean lesser = low.compareTo(current.value) > 0;
        boolean greater = high.compareTo(current.value) < 0;
        boolean inRange = !lesser && !greater;

        boolean noLeft = current.left == null;
        boolean noRight = current.right == null;

        if (noLeft && noRight) {
            if (inRange)
                return 1;
            return 0;
        } else if (noLeft) {
            if (inRange)
                return count(current.right, low, high) + 1;
            return count(current.right, low, high);
        } else if (noRight) {
            if (inRange)
                return count(current.left, low, high) + 1;
            return count(current.left, low, high);
        } else if (inRange) {
            return count(current.left, low, high) + count(current.right, low, high) + 1;
        } else {
            if (greater)
                return count(current.left, low, high);
            return count(current.right, low, high);
        }
    }

    public String toString() {
        return toString(root);
    }

    private String toString(Node<E> current) {

        if (current == null) {
            return "()";
        }

        return "(" + toString(current.left) + current.value + toString(current.right) + ")";
    }

}
