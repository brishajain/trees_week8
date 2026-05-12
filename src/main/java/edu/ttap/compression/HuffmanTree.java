package edu.ttap.compression;

/**
 * A HuffmanTree decodes files in the .grin format.
 */
public class HuffmanTree {
    /** The special end-of-file marker. */
    private static final int EOF = 256;

    /** Number of bits used to store tree values. */
    private static final int VALUE_BITS = 9;

    /** Number of bits used to write normal characters. */
    private static final int BYTE_BITS = 8;

    /**
     * A node in the Huffman tree.
     */
    private static class Node {
        private int value;
        private Node zero;
        private Node one;

        /**
         * Constructs a leaf node.
         *
         * @param value the value stored in this node
         */
        Node(int value) {
            this.value = value;
            this.zero = null;
            this.one = null;
        }

        /**
         * Constructs an interior node.
         *
         * @param zero the child reached by reading 0
         * @param one the child reached by reading 1
         */
        Node(Node zero, Node one) {
            this.value = -1;
            this.zero = zero;
            this.one = one;
        }

        /**
         * Checks whether this node is a leaf.
         *
         * @return true if this node is a leaf
         */
        boolean isLeaf() {
            return zero == null && one == null;
        }
    }

    /** The root of the Huffman tree. */
    private Node root;

    /**
     * Constructs a Huffman tree from the input stream.
     *
     * @param in the input stream
     */
    public HuffmanTree(BitInputStream in) {
        root = readTree(in);
    }

    /**
     * Reads a serialized Huffman tree.
     *
     * @param in the input stream
     * @return the root of the tree
     */
    private Node readTree(BitInputStream in) {
        int bit = in.readBit();

        if (bit == -1) {
            throw new IllegalArgumentException("Unexpected end of tree.");
        }

        if (bit == 0) {
            int value = in.readBits(VALUE_BITS);
            return new Node(value);
        }

        Node zero = readTree(in);
        Node one = readTree(in);
        return new Node(zero, one);
    }

    /**
     * Decodes the compressed payload.
     *
     * @param in the input stream
     * @param out the output stream
     */
    public void decode(BitInputStream in, BitOutputStream out) {
        boolean done = false;

        while (!done) {
            Node cur = root;

            while (!cur.isLeaf()) {
                int bit = in.readBit();

                if (bit == -1) {
                    throw new IllegalArgumentException("Unexpected end of payload.");
                }

                if (bit == 0) {
                    cur = cur.zero;
                } else {
                    cur = cur.one;
                }
            }

            if (cur.value == EOF) {
                done = true;
            } else {
                out.writeBits(cur.value, BYTE_BITS);
            }
        }
    }
}