module Quadratic where

import Data.Char (ord, chr)

quad :: Int -> Int -> Int -> Int -> Int
-- Returns evaluated quadratic expression.
quad a b c x 
    = a*x^2+b*x+c 

quadIsZero :: Int -> Int -> Int -> Int -> Bool
-- Returns True if a quadratic expression evaluates to zero;
-- False otherwise
quadIsZero a b c x = quad a b c x==0 

quadraticSolver :: Float -> Float -> Float -> (Float,Float)
-- Returns the two roots of a quadratic equation with
-- coefficients a, b, c
quadraticSolver a b c = ((-b+sqrt(b^2-4*a*c))/(2*a),(-b-sqrt(b^2-4*a*c))/(2*a))
 
realRoots :: Float -> Float -> Float -> Bool
-- Returns True if the quadratic equation has real roots;
-- False otherwise
realRoots a b c=b^2-4*a*c>=0

bigger, smaller :: Int -> Int -> Int
-- Returns first argument if it is larger than the second, the second argument
-- otherwise
bigger a b = if a>b then a else b

-- Opposite of bigger
smaller a b = if a<b then a else b

biggestOf3, smallestOf3 :: Int -> Int -> Int -> Int
-- Returns the largest/smallest of three Ints
biggestOf3 a b c = let d=bigger a b in if d>c then d else c

-- Ditto smallest of three
smallestOf3 a b c = let d=smaller a b in if d<c then d else c



isADigit :: Char -> Bool
-- Returns True if the character represents a digit '0'..'9';
-- False otherwise
isADigit a = a>='0' && a<='9' 

-- False otherwise
isAlphabetic :: Char -> Bool
-- Returns True if the character represents an alphabetic
-- character either in the range 'a'..'z' or in the range 'A'..'Z';
isAlphabetic a = (a>='a' && a<='z') || (a>='A' && a<='Z')

digitCharToInt :: Char -> Int
-- Returns the integer [0..9] corresponding to the given character.
-- Pre: a should be a char [0..9]
digitCharToInt a | isADigit a = ord a - ord '0' 
		 | otherwise  = error "Your input is not a number."

toUpperCase :: Char -> Char
-- Returns the upper case character corresponding to the input.
-- Uses guards by way of variety.
toUpperCase a | a>='a' && a<='z' = chr (ord a - ord 'a'+ ord 'A')
              | otherwise        = a
