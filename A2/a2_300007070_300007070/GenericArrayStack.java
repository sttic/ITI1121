// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 2

public class GenericArrayStack<E> implements Stack<E> {

    private E[] stack;
    private int top;

    @SuppressWarnings("unchecked")

   // Constructor
    public GenericArrayStack( int capacity ) {
        stack = (E[]) new Object[capacity];
        top = 0;
    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() {
        return top == 0;
    }

    public void push( E elem ) {
        stack[top++] = elem;
    }

    public E pop() {
        E saved;

        top--;
        saved = stack[top];
        stack[top] = null;

        return saved;
    }

    public E peek() {
        return stack[top-1];
    }
}