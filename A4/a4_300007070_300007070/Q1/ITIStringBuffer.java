import java.util.Iterator;

public class ITIStringBuffer {

    /**
     * Current String represention of the buffer.
     */
    private String str;

    /**
     * Singly linked list of type String.
     */
    private SinglyLinkedList<String> buffer;

    /**
     * If the buffer has changed since the last toString().
     */
    private boolean modified;

    /**
     * Number of total characters in buffer.
     */
    private int size;


    /**
     * Object's constructor. The constructor initializes the instance variables.
     */
    public ITIStringBuffer() {
        str = "";
        buffer = new SinglyLinkedList<String>();
        modified = false;
        size = 0;
    }

    /**
     * Object's constructor. The constructor initializes the instance variables.
     *
     * @param firstString String that is added upon creation of the object.
     */
    public ITIStringBuffer(String firstString){
        this();
        append(firstString);
    }

    /**
     * Adds a string to the SinglyLinkedList buffer.
     * Updates instance variables modified and size to reflect buffer.
     *
     * @param nextString String to be added to buffer.
     */
    public void append(String nextString){
        buffer.add(nextString);
        modified = true;
        size += nextString.length();
    }

    /**
     * Converts the SinglyLinkedList (holding seperate strings) to one connected string.
     * Creates a character array with each character of every string in the buffer to construct the final string.
     *
     * @return String Representation of buffer.
     */
    public String toString() {
        if (modified) {
            int i = 0;
            char[] ch = new char[size];
            Iterator<String> iter = buffer.iterator();

            while(iter.hasNext()) {
                char[] c = iter.next().toCharArray();
                for (int j = 0; j < c.length; j++) {
                    ch[i] = c[j];
                    i++;
                }
            }

            str = new String(ch);
            modified = false;
        }

        return str;
    }

}
