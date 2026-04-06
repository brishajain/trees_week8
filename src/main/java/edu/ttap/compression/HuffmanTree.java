package edu.ttap.compression;

import javax.imageio.IIOException;

/**
 * A HuffmanTree derives a space-efficient coding of a collection of byte
 * values.
 *
 * The huffman tree encodes values in the range 0--255 which would normally
 * take 8 bits.  However, we also need to encode a special EOF character to
 * denote the end of a .grin file.  Thus, we need 9 bits to store each
 * byte value.  This is fine for file writing (modulo the need to write in
 * byte chunks to the file), but Java does not have a 9-bit data type.
 * Instead, we use the next larger primitive integral type, short, to store
 * our byte values.
 */
public class HuffmanTree {

    /**
     * A single node in the Huffman tree.
     *
     * Leaf nodes store a value and have no children.
     * Interior nodes store left/right children and ignore the value field.
     */
    private static class Node {
        private int value;
        private Node zero;
        private Node one;
    

    /**
    * Constructs a leaf node with the given value.
    *
    * @param value the 9-bit value stored at this leaf
    */
    public Node(int value)
    {
        this.value = value;
        this.zero = null;
        this.one = null;
    }

    /**
    * Constructs an interior node with the given children.
    *
    * @param left the child reached by reading a 0
    * @param right the child reached by reading a 1
    */
    public Node(Node left, Node right)
    {
        this.zero = left;
        this.one = right;
    }

    /** 
     * Determines whether this node is a leaf. 
     * 
     * return @true if node has no children, false otherwise.
     */
    boolean isLeaf()
    {
        return zero == null && one == null;
    }

    }
   
    private Node root;
    /**
     * Constructs a new HuffmanTree from the given file.
     * 
     * @param in the input file (as a BitInputStream)
     */
    public HuffmanTree(BitInputStream in) {
       //how do we know when we've reached the end of the serialized huffmantree?
        root = HuffmanTreeH(in);
        
    }
    
    private Node HuffmanTreeH (BitInputStream in){
        int bit = in.readBit();
        Node cur;
        if (bit == -1) { 
            //throw error?
        }
         
        if (bit == 0){
            cur = new Node(in.readBits(9));
        } else{
            cur = new Node(null, null);
            cur.zero = HuffmanTreeH(in);
            cur.one = HuffmanTreeH(in);
        }
        return cur;
    }
    
    /**
     * Decodes a stream of huffman codes from a file given as a stream of
     * bits into their uncompressed form, saving the results to the given
     * output stream. Note that the EOF character is not written to out
     * because it is not a valid 8-bit chunk (it is 9 bits).
     * 
     * @param in the file to decompress.
     * @param out the file to write the decompressed output to.
     */
    public void decode(BitInputStream in, BitOutputStream out) {
       this.decodeH(root, in, out);
    }

    private void decodeH(Node cur, BitInputStream in, BitOutputStream out){
        int bit = in.readBit();
        if (bit == -1){
           // throw new IOException("reached end of pay load before could finish reading through tree");
        } //do something

        if(cur.isLeaf()){
            out.writeBits(cur.value, 9);
        }
        if(bit == 0) {
            decodeH(cur.zero, in, out);
        } else{
            decodeH(cur.one, in, out);
        } 

    }
}
