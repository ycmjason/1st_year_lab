public class LoopArraysProgram{
    public static void main(String[] args){
        System.out.println("Please enter the size of your data (n) "+
                            "set followed by n numbers to visualise:");
        int n = IOUtil.readInt();
        double[] data = new double[n];
        for(int i = 0; i < n; i++)
            data[i] = IOUtil.readDouble();
        System.out.println("Please enter a minimum, maximum and number"+
                            " of buckets to print a histogram with:");
        double min = IOUtil.readDouble();
        double max = IOUtil.readDouble();
        int numBuckets = IOUtil.readInt();
        int[] fTable = LoopArraysLibrary.frequencyTable(min, max, numBuckets, data);
        drawHisto(fTable);
    }
    public static void drawHisto(int[] fTable){
        int height = LoopArraysLibrary.maximum(fTable);
        int width  = fTable.length;

        for(int y = height; y >= 0; y--){
            for(int x = -1; x < width; x++){
                if(x==-1 && y==0)
                    System.out.print("+");
                else if(x==-1)
                    System.out.print("|");
                else if(y==0)
                    System.out.print("-");
                else if(fTable[x]==y && y>0)
                    System.out.print("#");
                else
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
