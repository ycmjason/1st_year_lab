public class RecursionLibrary {
    public static int greatestCommonDivisor(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        if(y==0){
            return x;
        }
        return greatestCommonDivisor(y, (x%y));
    }

    public static boolean isPrime(int n) {
        if(n>0){
            return !isDivisibleTillSqrtOfN(n,n-1);
        }else
            return false;
    }
    private static boolean isDivisibleTillSqrtOfN(int n,int m){
        assert m > 0 && n > 0 : "arguments should be positive ints";
        if( m < Math.sqrt(n) )
            return false;
        else if ( n % m == 0 )
            return true;
        else
            return false || isDivisibleTillSqrtOfN(n,m-1);
    }

    public static int sumSquareDigits(int n) {
        assert n >= 0 : "the arguments should not be negative";
        if( n == 0 )
            return 0;
        else{
            int m = n % 10;
            return m * m + sumSquareDigits(n / 10);
        }
    }

    public static boolean isHappy(int n) {
        if(n==1) return true;
        return isHappyWithHoldingNumber(sumSquareDigits(n),n,0,0);
    }
    private static boolean isHappyWithHoldingNumber(int n,
                                                    int r,
                                                    int t,
                                                    int nt){
        if( n == 1 ) return true;
        if( n == r ) return false;
        if( t > 0 )
            return isHappyWithHoldingNumber(sumSquareDigits(n),r,t-1,nt);
        else
            return isHappyWithHoldingNumber(sumSquareDigits(n),
                                            n,
                                            nt+1,
                                            nt+1);
    }
}
