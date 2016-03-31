module Recursion where

-- Precondition on all integers: they're all non-negative.

isPrime :: Int -> Bool
isPrime n | n==0 || n==1 = False
	  | otherwise	 = isPrime'  2
	    where
		isPrime' :: Int -> Bool
		isPrime' p | (p+1) >= n	= True
			     | n `mod` p == 0	= False
			     | otherwise	= isPrime' (p+1)

nextPrime :: Int -> Int
nextPrime n | isPrime(n+1) = n+1
	    | otherwise    = nextPrime(n+1) 


modPow :: Int -> Int -> Int -> Int
-- Pre: 1 <= m <= sqrt(maxint)
modPow x y n | y == 0		= 1 `mod` n
	     | y `mod` 2 == 0	= (modPow x yd2 n)^2 `mod` n
	     | otherwise	= (x `mod` n * modPow x (y-1) n) `mod` n
	where
	  yd2 = y `div` 2


isCarmichael :: Int -> Bool
isCarmichael n 
	| isPrime n = False
	| otherwise = isCarmichael' 2
		where
		   isCarmichael' :: Int -> Bool
		   isCarmichael' a
			| a==n = True
			| modPow a n n == a = isCarmichael' (a+1)
			| otherwise = False


primeFactors :: Int -> [ Int ]
-- Pre: x >= 1
primeFactors x = primeFactors' x 2
    where
        primeFactors' :: Int -> Int -> [Int]
        primeFactors' x a | a > x           = []
                          | isPrime x       = [x]
                          | xma == 0        = a:primeFactors' xda  a
                          | otherwise       = primeFactors' x (nextPrime a)
            where
                xma = x `mod` a
                xda = x `div` a

sumDigits :: Int -> Int
sumDigits n | n<10      = n
            | otherwise = n `mod` 10 + sumDigits (n `div` 10)


sumAllDigits :: [ Int ] -> Int
sumAllDigits [] =0
sumAllDigits (x:xs) = sumDigits x+(sumAllDigits xs )

nextSmithNumber :: Int -> Int
nextSmithNumber n | sumDigits(n+1) == sumAllDigits (primeFactors(n+1)) && not (isPrime (n+1))= n+1
                  | otherwise                                         = nextSmithNumber (n+1)
