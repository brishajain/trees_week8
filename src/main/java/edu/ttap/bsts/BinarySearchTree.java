package edu.ttap.bsts;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A binary tree that satisifies the binary search tree invariant.
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    ///// From the reading

    /**
     * A node of the binary search tree.
     */
    private static class Node<T> {
        public T value;
        public Node<T> left;
        public Node<T> right;

        /**
         * @param value the value of the node
         * @param left the left child of the node
         * @param right the right child of the node
         */
        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * @param value the value of the node
         */
        public Node(T value) {
            this(value, null, null);
        }

        /**
         * @return if node is leaf return true
         */
        public boolean leaf(){
            if(this.left == null && this.right == null) 
                return true;
            else return false;
        }
    }

    private Node<T> root;

    /**
     * Constructs a new empty binary search tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * @param node the root of the tree
     * @return the number of elements in the specified tree
     */
    private int sizeH(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + sizeH(node.left) + sizeH(node.right);
        }
    }

    /**
     * @return the number of elements in this tree
     */
    public int size() {
        return sizeH(root);
    }

    
    ///// Part 1: Insertion

    /**
     * Inserts the given value into this binary search tree.
     * @param v the value to insert
     */
    public void insert(T v) {
        root = insertH(root, v);
    }

    private Node<T> insertH(Node<T> cur, T val){
        if (cur == null){ 
            return new Node<>(val, null, null); 
        }
        if(val.compareTo(cur.value) < 0){
            cur.left = insertH(cur.left, val);
        } else {
            cur.right = insertH(cur.right, val);
        }
        return cur;
    }

    ///// Part 2: Contains
   
    /**
     * @param v the value to find
     * @return true iff this tree contains <code>v</code>
     */
    public boolean contains(T v) {
        return containsHelper(v, root);
    }

private boolean containsHelper(T value, Node<T> cur) {
        if(cur == null) {
            return false;
        } else {
            if (cur.value.equals(value)) {
                return true;
            } else {
                if(value.compareTo(cur.value) < 0){
                     return containsHelper(value, cur.left);
                } else { 
                    return containsHelper(value, cur.right);
                }
            }
        }
    }
    ///// Part 3: Ordered Traversals

    /**
     * @return the (linearized) string representation of this BST
     */
    @Override
    public String toString() {
         StringBuffer buf = new StringBuffer("");
        buf = toStringH(root, buf);
        buf.delete(buf.length() - 2, buf.length());
    
        return "[" + buf + "]";
    }
     private StringBuffer toStringH(Node<T> cur, StringBuffer buf) {
        if(cur == null) {
            return buf;
        }
        toStringH(cur.left, buf);
        buf.append(String.valueOf(cur.value));
        buf.append(", ");
        toStringH(cur.right, buf);

        return buf;
        
    }    
    

    /**
     * @return a list contains the elements of this BST in-order.
     */
    public List<T> toList() {
        List<T> lst = new ArrayList<>();
        return toListH(root, lst);
    }
    private List<T> toListH(Node<T> cur, List<T> lst){
        if (cur == null){
            return lst;
        }
        
        toListH(cur.left, lst);
        lst.add(cur.value);
        toListH(cur.right, lst);
        
        return lst;
    }

    ///// Part 4: BST Sorting

    /**
     * @param <T> the carrier type of the lists
     * @param lst the list to sort
     * @return a copy of <code>lst</code> but sorted
     * @implSpec <code>sort</code> runs in ___ time if the tree remains balanced. 
     */
    public static <T extends Comparable<? super T>> List<T> sort(List<T> lst) {
         BinarySearchTree<T> tree = new BinarySearchTree<T>();
         for(int i = 0; i < lst.size(); i++){
            tree.insert(lst.get(i));
         }
        return tree.toList();
    }

    ///// Part 5: Deletion
  
    /*
     * The three cases of deletion are:
     * 1. the node to be deleted is a leaf, when cur.right AND cur.left == null
     * 2. the node to be deleted has only one child, when cur.right XOR cur.left == null
     * 3. the node to be delted has two children, when cur.right AND cur.left != null
     */

    /**
     * Modifies the tree by deleting the first occurrence of <code>value</code> found
     * in the tree.
     *
     * @param value the value to delete
     */
    public void delete(T value) {
        if(!this.contains(value)){
            throw new IOException("value does not exist in tree");
        }

        Node<T> toDelete = findNodeH(root, value);
        
        // case 1
        if(toDelete.leaf()){
            toDelete = null;
        }
        //case 2
        
        if(toDelete.left == null){
            toDelete = toDelete.right;
        }
        if(toDelete.right == null){
            toDelete = toDelete.left;
        } 

        // case 3
        
        if (sizeH(toDelete.left) > sizeH(toDelete.right)){
            insertNode(toDelete.left, toDelete.right);
            toDelete = toDelete.right;
        } else{
            insertNode(toDelete.right, toDelete.left);
            toDelete = toDelete.left; 
        }
    }

    private Node<T> findNodeH(Node<T> cur, T val){

        if (val.compareTo(cur.value) == 0){
            return cur;
        }

        if(val.compareTo(cur.value) < 0)
            cur.left = findNodeH(cur.left, val);
        else if(val.compareTo(cur.value) > 0)
            cur.right = findNodeH(cur.right, val); 

    }

    private void insertNode(Node<T> dontRecurse, Node<T> doRecurse){
        if(doRecurse == null){
            doRecurse = dontRecurse;
            return;
        } else if (dontRecurse.value.compareTo(doRecurse.value) < 0){
            insertNode(dontRecurse, doRecurse.left);
        } else {
            insertNode(dontRecurse, doRecurse.right);   
        }

    }


}
