public class RecursionProgram {

  public static void main(String[] args) {

    System.out.println("Please enter two positive numbers separated by a space: ");
    int x = IOUtil.readInt();
    int y = IOUtil.readInt();

    int gcd = RecursionLibrary.greatestCommonDivisor(x, y);

    System.out.println("The gcd of " + x + " and " + y + " is: " + gcd
        + ".");

    printDetails(x);
    printDetails(y);
  }

  private static void printDetails(int n) {
    if (RecursionLibrary.isPrime(n)) {
      System.out.println(n + " is prime.");
    }

    int ssds = RecursionLibrary.sumSquareDigits(n);
    System.out.println("The sum of the digits squared in " + n + " is: "
        + ssds);

    if (RecursionLibrary.isHappy(n)) {
      System.out.println(n + " is happy.");
    }
  }
}
