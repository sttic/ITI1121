// Author: Tommy Deng
// Student number: 300007070
// Course: ITI 1121-C00
// Assignment: 4

import java.util.*;

public class SinglyLinkedList<E> implements List<E>, Iterable<E> {

    /**
     * Counts the number of elements between two other element values (inclusive).
     *
     * @param fromElement Element value to start counting.
     *
     * @param toElement Element value to stop counting.
     *
     * @return The number of elements between fromElement and toElement values.
     */
    public int count(E fromElement, E toElement) {
        if (fromElement == null) {
            return 0;
        } else if (fromElement.equals(toElement)) {
            return 1;
        }

        return count(head, false, fromElement, toElement);
    }

    /**
     * Counts the number of elements between two other element values (inclusive) using a recursive algorithm.
     *
     * @param current Node at current position.
     *
     * @param found If the first fromElement has been found yet.
     *
     * @param fromElement Element value to start counting from.
     *
     * @param toElement Element value to stop counting from.
     *
     * @return The number of elements between fromElement and toElement values.
     */
    private int count(Node<E> current, boolean found, E fromElement, E toElement) {
        if (current == null) {
            return 0;
        } else if (found) {
            if (current.value.equals(toElement)) {
                return 1;
            }
            return count(current.next, true, fromElement, toElement) + 1;
        } else {
            if (current.value.equals(fromElement)) {
                return count(current.next, true, fromElement, toElement) + 1;
            }
            return count(current.next, false, fromElement, toElement);
        }
    }


    private static class Node<T> {
        private T value;
        private Node<T> next;
        private Node(T value, Node<T> next ) {
            this.value = value;
            this.next = next;
        }
    }

    private class LinkedListIterator implements Iterator<E> {

        private Node<E> currentIterator;

        public LinkedListIterator() {
            currentIterator = null;
        }

        public E next() {
            if(currentIterator == null) {
                currentIterator = head;
            } else {
                currentIterator = currentIterator.next;
            }
            if(currentIterator == null)
            throw new NoSuchElementException("Iterator at the end or list empty");
            return currentIterator.value;
        }

        public boolean hasNext(){
            if(currentIterator == null)
            return !isEmpty();
            else
            return (currentIterator.next != null);
        }

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<E> iterator(){
        return new LinkedListIterator();
    }

    private Node<E> head;
    private int size;
    private Node<E> tail;

    public SinglyLinkedList() {
        head = tail = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(E o) {

        if(o == null) {
            throw new NullPointerException("Can't add null reference to the list");
        }
        Node<E> newNode = new Node<E>( o, null );
        if ( head == null ) {
            head = newNode;
            tail = head;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void add( E o ) {

        if(o == null) {
            throw new NullPointerException("Can't add null reference to the list");
        }
        Node<E> newNode = new Node<E>( o, null );
        if ( head == null ) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void add( int pos, E o) {

        if(o == null) {
            throw new NullPointerException("Can't add null reference to the list");
        }
        if ( pos < 0 || pos > size) {
            throw new IndexOutOfBoundsException( Integer.toString( pos ) );
        }


        if(pos == 0){
            addFirst( o );
        } else if(pos == size){
            add( o );
        } else  {

            Node<E> p = head;
            for(int i = 0; i < pos - 1; i++) {
                p = p.next;
            }

            p.next = new Node<E>( o, p.next );
            size++;
        }
    }

    public E removeFirst() {

        if(isEmpty())
        throw new IllegalStateException("Empty list!");

        E savedValue = head.value;
        size--;
        if(size > 0){
            head = head.next;
        } else {
            head = tail = null;
        }
        return savedValue;
    }

    public E removeLast() {

        if(isEmpty())
        throw new IllegalStateException("Empty list!");

        E savedValue= tail.value;

        if ( head.next == null ) {
            head = tail = null;
        } else {
            Node<E> p = head;
            while ( p.next != tail ) {
                p = p.next;
            }
            tail = p;
            tail.next = null;
        }
        size--;
        return savedValue;
    }


    public boolean remove( E o ) {

        if ( head == null )
        return false;

        if ( head.value.equals( o ) ) {
            removeFirst();
            return true;
        } else {
            Node<E> p = head;
            while ( p.next != null && ! p.next.value.equals( o ) ) {
                p = p.next;
            }
            if ( p.next == null ) {
                return false;
            } else {
                p.next = p.next.next;
                if(p.next == null) {
                    tail = p;
                }
                size--;
                return true;
            }
        }
    }

    public E get( int pos ) {

        if(isEmpty())
        throw new IllegalStateException("Empty list!");

        if ( pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException( Integer.toString( pos ) );
        }

        Node<E> p = head;

        for ( int i=0; i<pos; i++ ) {
            p = p.next;
        }

        return p.value;
    }


    public E remove( int pos ) {

        if(isEmpty())
        throw new IllegalStateException("Empty list!");

        if ( pos < 0 || pos >= size ) {
            throw new IndexOutOfBoundsException( Integer.toString( pos ) );
        }

        Node<E> toBeRemoved;

        if ( pos == 0 ) {
            return removeFirst();
        } else if (pos == size-1) {
            return removeLast();
        }
        else {
            Node<E> p = head;

            for ( int i=0; i<( pos-1); i++ ) {
                p = p.next;
            }
            toBeRemoved = p.next;
            p.next = p.next.next;
        }
        size--;
        return toBeRemoved.value;
    }


    public boolean equals(SinglyLinkedList<E> otherList){

        if((otherList == null) || (size != otherList.size()))
        return false;

        Node<E> thisCursor = head;
        Node<E> otherCursor = otherList.head;

        for(int i = 0; i < size; i++){
            if(!thisCursor.value.equals(otherCursor.value))
            return false;
            thisCursor = thisCursor.next;
            otherCursor = otherCursor.next;
        }

        return true;
    }




    public String toString(){
        StringBuffer res = new StringBuffer();
        res.append("[");
        if(!isEmpty()){
            Node<E> cursor = head;
            res.append(cursor.value);
            while(cursor.next != null){
                cursor = cursor.next;
                res.append(" " + cursor.value);
            }
        }
        res.append("]");
        return res.toString();
    }
}
