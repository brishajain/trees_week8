package edu.ttap.compression;

import java.io.IOException;

/**
 * The driver for the Grin decompression program.
 */
public class Grin {
    /** The magic number for .grin files. */
    public static final int MAGIC_NUMBER = 1846;

    /**
     * Decodes infile and writes the result to outfile.
     *
     * @param infile the file to decode
     * @param outfile the file to write to
     */
    public static void decode(String infile, String outfile) {
        try {
            BitInputStream in = new BitInputStream(infile);
            BitOutputStream out = new BitOutputStream(outfile);

            int magic = in.readBits(32);
            if (magic != MAGIC_NUMBER) {
                throw new IllegalArgumentException("Invalid .grin file.");
            }

            HuffmanTree tree = new HuffmanTree(in);
            tree.decode(in, out);

            in.close();
            out.close();
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read or write file.");
        }
    }

    /**
     * The entry point to the program.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java Grin <infile> <outfile>");
            return;
        }

        decode(args[0], args[1]);
    }
}