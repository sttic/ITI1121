public class Q4 {

    public static void main(String[] args) {

        StudentInfo.display();

        BinarySearchTree<Integer> bTree;
        bTree = new BinarySearchTree<Integer>();

        bTree.add(4);
        bTree.add(2);
        bTree.add(3);
        bTree.add(1);
        bTree.add(5);
        bTree.add(6);
        bTree.add(8);
        bTree.add(9);
        bTree.add(7);

        System.out.println(bTree.count(3,6));
    }
}
