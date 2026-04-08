package edu.ttap.trees;
//import java.util.StringBuffer;
import java.util.List;
import java.util.ArrayList;
 
/**
* A generic binary tree implementation.
*/
public class Tree<T> {
    /**
     * A node of the binary tree.
     */
    public static class Node<T> {
        public T value;
        public Node<T> left;
        public Node<T> right;
 
        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
 
        /**
         * @param value the value of the node
         */
        Node(T value) {
            this(value, null, null);
        }
    }
 
    ///// From the reading...
 
    private Node<T> root;
 
    /**
     * Constructs a new, empty binary tree.
     */
    public Tree() {
        this.root = null;
    }
 
    /**
     * @return a sample binary tree for testing purposes
     */
    public static Tree<Integer> makeSampleTree() {
        Tree<Integer> tree = new Tree<Integer>();
        tree.root = new Node<>(
            5,
            new Node<>(2,
                new Node<>(1),
                new Node<>(3)
            ),
            new Node<>(8,
                new Node<>(7,
                    new Node<>(6),
                    null),
                new Node<>(9,
                    null,
                    new Node<>(10)))
        );
        return tree;
    }
 

    /**
     * @param node the root of the tree
     * @return the number elements found in this tree rooted at node
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }
 
    /** @return the number of elements in the tree */
    public int size() {
        return sizeH(root);
    }
 
    ///// Part 1: Contains
 
    /**
     * @param value the value to search for
     * @return true iff the tree contains <code>value</code>
     */
    public boolean contains(T value) {
        return containsHelper(value, root);
    }
 
    private boolean containsHelper(T value, Node<T> cur) {
        if(cur == null) {
            return false;
        } else {
            if (cur.value.equals(value)) {
                return true;
            } else {
                return containsHelper(value, cur.left) || containsHelper(value, cur.right);
            }
        }
    }
 
    ///// Part 2: Traversals
 
    /**
     *
     * @return the elements of this tree collected via an in-order traversal
     */
    public List<T> toListInorder() {
        List<T> record = new ArrayList<>();
        toListInorderH(record, root);
        return record;
    }
 
    /**
     * Constructs a list with elements in binary search tree in order
     * @param record list that the elements are being put in
     * @param cur the current node we are traversing over and pulling a value from
     */
    private void toListInorderH(List<T> record, Node<T> cur) {
        if(cur == null) {
            return;
        }
        toListInorderH(record, cur.left);
        record.add(cur.value);
        toListInorderH(record, cur.right);
    }
 
    /**
     * @return the elements of this tree collected via a pre-order traversal
     */
    public List<T> toListPreorder() {
        List<T> record = new ArrayList<>();
        toListPreorderH(record, root);
        return record;
    }

    /**
     * Constructs a list with elements in binary search tree in pre order
     * @param record list that the elements are being put in
     * @param cur the current node we are traversing over and pulling a value from
     */
    private void toListPreorderH(List<T> record, Node<T> cur) {
        if(cur == null) {
            return;
        }
        record.add(cur.value);
        toListPreorderH(record, cur.left);
        toListPreorderH(record, cur.right);
    }
 
    /**
     * @return the elements of this tree collected via a post-order traversal
     */
    public List<T> toListPostorder() {
        List<T> record = new ArrayList<>();
        toListPostorderH(record, root);
        return record;
    }

     /**
     * Constructs a list with elements in binary search tree in post-order
     * @param record list that the elements are being put in
     * @param cur the current node we are traversing over and pulling a value from
     */
    private void toListPostorderH(List<T> record, Node<T> cur) {
        if(cur == null) {
            return;
        }
        toListPostorderH(record, cur.left);
        toListPostorderH(record, cur.right);
        record.add(cur.value);
    }
 
    ///// Part 3: Stringifying Trees
  
    /**
     * @return a string represent of this tree in the form, "[x1, ..., xk]."
     * The order of the elements is left unspecified.
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("");
        buf = toStringH(root, buf);
        buf.delete(buf.length() - 2, buf.length());
        return "[" + buf + "]";
    }
    // The last two characters in any string we create are going to be ", " WE DONT WANT THIS
    private StringBuffer toStringH(Node<T> cur, StringBuffer buf) {
        if(cur == null) {
            return buf;
        }
        buf.append(String.valueOf(cur.value));
        buf.append(", ");
        toStringH(cur.left, buf);
        toStringH(cur.right, buf);

        return buf;
        
    }
 
    ///// Extra: Pretty Printing
   
    /**
     * @return a string represent of this tree in bulleted list form.
     */
    public String toPrettyString() {
        throw new UnsupportedOperationException();
    }
 
    /**
     * The main driver for this program
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Nothing to do. 'Run' via the JUnit tests instead!");
    }
}