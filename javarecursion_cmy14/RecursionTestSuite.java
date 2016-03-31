public class RecursionTestSuite {

  public static void main(String[] args) {
    System.out.println("Running tests...");

    gcdTests();
    isPrimeTests();
    sumSquareDigitsTests();
    isHappyTests();

    System.out.println("...tests complete.");
  }

  public static void gcdTests() {
    checkGCD(12, 16, 4);
    checkGCD(3456, 66, 6);
    checkGCD(1, -1, 1);
    // TODO: add test cases for greatest common divisor here.
  }

  public static void isPrimeTests() {
    checkIsPrime(9, false);
    checkIsPrime(7919, true);
    // TODO: add test cases for isPrime here.
  }

  public static void sumSquareDigitsTests() {
    checkSumSquareDigits(10, 1);
    checkSumSquareDigits(103, 10);
    // TODO: add test cases for sumSquareDigits here.
  }

  public static void isHappyTests() {
    checkIsHappy(397, true);
    checkIsHappy(123, false);
    // TODO: add test cases for isHappy here.
  }


  private static void checkGCD(int x, int y, int expected) {
    int actual = RecursionLibrary.greatestCommonDivisor(x, y);
    if (actual != expected) {
      System.out.println("greatestCommonDivisor(" + x + ", " + y + "); expected: "
          + expected + ", got: " + actual);
    }
  }

  private static void checkIsPrime(int value, boolean expected) {
    boolean actual = RecursionLibrary.isPrime(value);
    if (actual != expected) {
      System.out.println("isPrime(" + value + "); expected: " + expected
          + ", got: " + actual);
    }
  }

  private static void checkSumSquareDigits(int n, int expected) {
    int actual = RecursionLibrary.sumSquareDigits(n);
    if (actual != expected) {
      System.out.println("sumSquareDigits(" + n + "); expected: "
          + expected + ", got: " + actual);
    }
  }

  private static void checkIsHappy(int n, boolean expected) {
    boolean actual = RecursionLibrary.isHappy(n);
    if (actual != expected) {
      System.out.println("isHappy(" + n + "); expected: " + expected
          + ", got: " + actual);
    }
  }

}
