public class LoopArraysLibrary {

    public static int fib(int n) {
        assert n>=0;
        switch( n ){
            case 0:
                return 0;
            case 1:
                return 1;
            default:
                int prePrevious = 0;
                int previous    = 1;
                int current     = 1;
                for(int i = 2 ; i < n ; i++){
                    prePrevious = previous;
                    previous    = current ;
                    current     = previous + prePrevious;
                }
                return current;
        }
    }

    public static int maximum(int[] ms) {
        assert ms.length>0;
        int max=ms[0];
        for(int m : ms){
            if( m > max )
                max = m;
        }
        return max;
    }

    public static int[] frequencyTable(double minimum, double maximum,
            int numBuckets, double[] data) {
        double interval         = (maximum - minimum) / numBuckets;
        double interval_start   = minimum;
        double interval_end     = interval_start + interval;
        int[] f = new int[numBuckets];
        for(int i = 0 ; i < numBuckets; i++){
            for(double datum : data){
                if(interval_start <= datum && datum < interval_end){
                    f[i] += 1;
                }
            }
            interval_start +=interval;
            interval_end   +=interval;
        }

        return f;
    }

    public static double[][] matrixMultiply(double[][] m, double[][] n) {
        //Pre: m and n are retengular matrices
        assert (m[0].length == n.length);
        double[][] mn = new double[m.length][n[0].length];
        for(int i = 0; i < mn.length; i++)
            for(int j = 0; j < mn[i].length; j++)
                for(int k = 0; k < n.length; k++)
                    mn[i][j] += m[i][k]*n[k][j];
        
        return mn;
    }

   /* 
   //I think this method is a lot much readable,
   //yet it seems to have the wrong complexity?
    private static double dotProduct(double[] v, double[] w){
        //Pre: vectors v and m have same length
        assert v.length==w.length;
        double sum = 0;
        for(int i = 0; i < v.length; i++)
            sum+=v[i]*w[i];
        
        return sum;
    }

    private static double[] getMatrixColumn(double[][] m, int n){
        assert (n>=0 && n<m[0].length);
        double[] vector=new double[m.length];
        for(int i = 0; i < m.length; i++)
            vector[i] = m[i][n];
        
        return vector;
    }

    public static double[][] matrixMultiply(double[][] m, double[][] n) {
        //Pre: m and n are retengular matrices
        assert (m[0].length == n.length);
        double[][] mn = new double[m.length][n[0].length];
        for(int i = 0; i < m.length; i++)
            for(int j = 0; j < n[i].length; j++)
                mn[i][j] = dotProduct(m[i],getMatrixColumn(n,j));

        return mn;
    }
    */
}
