package dictionary;

import java.io.FileNotFoundException;

import java.io.File;
import java.io.PrintStream;

import java.util.Random;

public class Main {

    private static final int MAX_SIZE = 500;
    private static final int REPITITIONS = 500;

    public static void main(String[] args) throws FileNotFoundException {
      File ollDat = new File("./OrderedLinkedList.dat");
      File bstDat = new File("./BinarySearchTree.dat");

      PrintStream ollDatPS = new PrintStream(ollDat);
      PrintStream bstDatPS = new PrintStream(bstDat);

      InsertComplexities ic = new InsertComplexities(new Random());
      int[] ollComplexities = ic.getInsertComplexities(new OrderedLinkedList<InsertComplexities.InstrumentedKey, Integer>(), MAX_SIZE, REPITITIONS);
      int[] bstComplexities = ic.getInsertComplexities(new BinarySearchTree<InsertComplexities.InstrumentedKey, Integer>(), MAX_SIZE, REPITITIONS);

      for(int i = 0; i < MAX_SIZE; i++){
        ollDatPS.printf("%d\t%d", i, ollComplexities[i]);
        ollDatPS.println();
      }

      for(int i = 0; i < MAX_SIZE; i++){
        bstDatPS.printf("%d\t%d", i, bstComplexities[i]);
        bstDatPS.println();
      }
    }

}
