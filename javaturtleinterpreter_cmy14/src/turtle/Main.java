package turtle;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.File;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{
      Scanner commandsScanner;
      PrintStream outputStream = new PrintStream(System.out);
      TurtleInterpreter interpreter;

      if(args.length > 0){
        File file = new File(args[0]);
        assert (file.isFile()): "File doesn't exist.";
        commandsScanner = new Scanner(file);

        if(args.length >= 2){
          outputStream = new PrintStream(args[1]);
        }

      }else{
        commandsScanner = new Scanner(System.in);
      }

      interpreter = new TurtleInterpreter(commandsScanner, outputStream);
      interpreter.execute();

    }
}
