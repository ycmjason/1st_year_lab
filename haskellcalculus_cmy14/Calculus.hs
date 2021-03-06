module Calculus where

import Data.Maybe

data UnOp = Neg | Sin | Cos | Log
          deriving (Eq, Ord, Show)

data BinOp = Add | Mul | Div
           deriving (Eq, Ord, Show)

data Exp = Val Double | Id String | UnApp UnOp Exp | BinApp BinOp Exp Exp
         deriving (Eq, Ord, Show)

type Env = [(String, Double)]



lookUp :: Eq a => a -> [(a, b)] -> b
lookUp x l = fromJust (lookup x l)

eval :: Exp -> Env -> Double
eval (Val x) env = x
eval (Id x)  env = lookUp x env
--
eval (UnApp unOp exp) env
    | unOp == Neg = 0-(evaled)
    | unOp == Sin = sin (evaled)
    | unOp == Cos = cos (evaled)
    | unOp == Log = log (evaled)
        where
            evaled= eval exp env
--
eval (BinApp binOp exp exp') env
    | binOp == Add  = evaled + evaled' 
    | binOp == Mul  = evaled * evaled' 
    | binOp == Div  = evaled / evaled' 
        where
            evaled  = eval exp  env
            evaled' = eval exp' env

diff :: Exp -> String -> Exp
diff (Val x) env = Val 0
diff (Id x)  env
    | x==env    = Val 1
    | otherwise = Val 0
--
diff (UnApp unOp exp) env
    | unOp == Neg = UnApp Neg diffed
    | unOp == Sin = BinApp Mul (UnApp Cos exp) diffed 
    | unOp == Cos = UnApp Neg(BinApp Mul(UnApp Sin exp) diffed)
    | unOp == Log = BinApp Div diffed exp
        where
            diffed= diff exp env
--
diff (BinApp binOp exp exp') env
    | binOp == Add  = BinApp Add diffed diffed' 
    | binOp == Mul  = BinApp Add 
                        (BinApp Mul exp diffed') 
                        (BinApp Mul diffed exp')
    | binOp == Div  = BinApp Div
                        (BinApp Add
                            (BinApp Mul diffed exp')
                            (UnApp Neg (BinApp Mul exp diffed')))
                        (BinApp Mul exp' exp')
        where
            diffed  = diff exp  env
            diffed' = diff exp' env

fact :: Int -> Double
fact n = fromIntegral (product [1..n])

maclaurin :: Exp -> Double -> Int -> Double
maclaurin exp x n   = maclaurin' exp 0
    where
        maclaurin' exp m
            | m == n    = 0
            | otherwise = (eval exp [("x", 0)] * (x^m)) / (fact m)  + maclaurin' (diff exp "x") (m+1)

showExp :: Exp -> String
showExp = error "TODO: implement showExp"

---------------------------------------------------------------------------
-- Test cases from the spec.

e1, e2, e3, e4, e5, e6 :: Exp

-- > 5*x
e1 = BinApp Mul (Val 5.0) (Id "x")

-- > x*x + y - 7
e2 = BinApp Add (BinApp Add (BinApp Mul (Id "x") (Id "x")) (Id "y"))
                (UnApp Neg (Val 7.0))

-- > x-y^2/(4*x*y-y^2)::Exp
e3 = BinApp Add (Id "x")
            (UnApp Neg (BinApp Div (BinApp Mul (Id "y") (Id "y"))
            (BinApp Add (BinApp Mul (BinApp Mul (Val 4.0) (Id "x")) (Id "y"))
                        (UnApp Neg (BinApp Mul (Id "y") (Id "y"))))))

-- > -cos x::Exp
e4 = UnApp Neg (UnApp Cos (Id "x"))

-- > sin (1+log(2*x))::Exp
e5 = UnApp Sin (BinApp Add (Val 1.0)
                           (UnApp Log (BinApp Mul (Val 2.0) (Id "x"))))

-- > log(3*x^2+2)::Exp
e6 = UnApp Log (BinApp Add (BinApp Mul (Val 3.0) (BinApp Mul (Id "x") (Id "x")))
                           (Val 2.0))

----------------------------------------------------------------------
-- EXTENSION: Uncomment and complete these...

-- instance Num Exp where

-- instance Fractional Exp where

-- instance Floating Exp where


-- instance (Eq a, Num a) => Num (Maybe a) where

-- instance (Eq a, Fractional a) => Fractional (Maybe a) where

-- diff2 :: Exp -> String -> Maybe Exp



-- The following makes it much easier to input expressions, e.g. sin x, log(x*x) etc.

x, y :: Exp
x = Id "x"
y = Id "y"
