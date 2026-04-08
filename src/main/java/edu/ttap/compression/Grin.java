package edu.ttap.compression;

import java.io.IOException;

/**
 * The driver for the Grin compression program.
 */
public class Grin {
    /**
     * Decodes the .grin file denoted by infile and writes the output to the
     * .grin file denoted by outfile.
     * @param infile the file to decode
     * @param outfile the file to ouptut to
     */

    public static final int MAGIC_NUMBER = 1846;

    public static void decode(String infile, String outfile) throws IllegalArgumentException {
        try{
            BitInputStream in = new BitInputStream(infile);
            BitOutputStream out = new BitOutputStream(outfile);
            
            if(in.readBits(32) != MAGIC_NUMBER) {
                throw new IllegalArgumentException("Invalid .grin file");
            }

            HuffmanTree tree = new HuffmanTree(in);
            tree.decode(in, out);
            
            in.close();
            out.close();

        } catch (IOException e){
            return;
        } 
    }

    
    /**
     * The entry point to the program.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {
        if(args.length != 2) {
            throw new IllegalArgumentException("Incorrect number of command line arguments");
        }  
        try {
        decode(args[0], args[1]); 
        System.out.println("Usage: java Grin <infile> <outfile>");
        } catch (IllegalArgumentException b) {
            return;
        } 
    }
}
