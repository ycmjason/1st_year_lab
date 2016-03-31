module LSystems where

import IC.Graphics

type Rule
  = (Char, String)

type Rules
  = [Rule]

type System
  = (Float, String, Rules)

cross, triangle, arrowHead, peanoGosper,
  dragon, snowflake, tree, bush :: System

type Vertex
  = (Float, Float)

type TurtleState
  = (Vertex, Float)

type Stack
  = [TurtleState]

type ColouredLine
  = (Vertex, Vertex, Colour)

--  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --  --
--  Functions for working with systems.

-- |Returns the rotation angle for the given system.
angle :: System -> Float
angle (a,_,_)=a 

-- |Returns the base string for the given system.
base :: System -> String
base (_,b,_)=b

-- |Returns the set of rules for the given system.
rules :: System -> Rules
rules (_,_,r)=r

-- Turtle functions
tsCoord :: TurtleState -> Vertex
tsCoord (a,_) = a

tsAngle :: TurtleState -> Float
tsAngle (_,a) = a
-- |Look up a character in the given set of rules.
--
--  Pre: the character exists in the set of rules.
lookupChar :: Char -> Rules -> String
lookupChar _ [] = []
lookupChar c ((ruleChar,ruleString):rules)
	| c == ruleChar = ruleString
	| otherwise		= lookupChar c rules

-- |Expand a command once using the given set of rules.
expandOne :: Rules -> String -> String
expandOne rules []		= []
expandOne rules (b:bs)	= lookupChar b rules++expandOne rules bs

-- |Expand a command `n' times using the given set of rules.
expand :: Rules -> String -> Int -> String
expand _ [] _		= []
expand _ base 0		= base
expand rules base n	= expand rules (expandOne rules base) (n-1)

-- change degree to radian
radian :: Float -> Float
radian theta = theta*pi/180

--define cosine and sine for degrees
cosine	= cos.radian
sine	= sin.radian
-- |Move a turtle.
--
--  * 'F' moves distance 1 in the current direction.
--  * 'L' rotates left according to the given angle.
--  * 'R' rotates right according to the given angle.
move :: Char -> TurtleState -> Float -> TurtleState
move 'F' ((currentX,currentY),currentDirection) theta
	= ((newX, newY),currentDirection)
		where
			newX=currentX+cosine currentDirection
			newY=currentY+sine currentDirection
move opt (vertex,currentDirection) theta
	| opt =='L' = (vertex, currentDirection+theta)	
	| opt =='R' = (vertex, currentDirection-theta)	

-- |Trace lines drawn by a turtle using the given colour, following the
--  commands in the string and assuming the given initial angle of rotation.
{-
--this is my first version of trace
trace :: String -> Float -> Colour -> [ColouredLine]
trace commands theta colour = trace' commands ((0,0),90) [] 
	where
		trace' :: String -> TurtleState -> [TurtleState] -> [ColouredLine]
		trace' [] _ _  = []
		--
		trace' ('[':cs) ts tsStacks
			= trace' cs ts (ts:tsStacks)
		-- 
		trace' (']':cs) ts (tss:tsss)
			= trace' cs tss tsss 
		--
		trace' (c:cs) ts tsStacks
			| c == 'F'
				= (tsCoord ts,tsCoord newTs,colour):trace' cs newTs tsStacks
			| otherwise
				= trace' cs newTs tsStacks
				where
					newTs = move c ts theta 
-}
--tail recursive method
trace :: String -> Float -> Colour -> [ColouredLine]
trace commands theta colour = trace' commands ((0,0),90) [] []
	where
		trace' :: 	String -> TurtleState -> [TurtleState] ->
					[ColouredLine] -> [ColouredLine]
		trace' [] _ _ acc = acc
		--
		trace' ('[':cs) ts tsStacks acc
			= trace' cs ts (ts:tsStacks) acc
		-- 
		trace' (']':cs) ts (tss:tsss) acc
			= trace' cs tss tsss acc
		--
		trace' (c:cs) ts tsStacks acc
			| c == 'F'
				= trace' cs newTs tsStacks ((tsCoord ts,tsCoord newTs,colour):acc)
			| otherwise
				= trace' cs newTs tsStacks acc
				where
					newTs = move c ts theta 
-- this point on are the extentions and just for fun implementations

-- this is working
-- trace with changing from a colour to another colour
-- >drawLSystem2Colour tree 6 green red -> http://goo.gl/uLnTBb
trace2Colour :: String -> Float -> Colour -> Colour -> [ColouredLine]
trace2Colour commands theta fromC toC = trace' commands ((0,0),90) [] [] fromC
	where
		trace' :: 	String -> TurtleState -> [TurtleState] ->
					[ColouredLine] -> Colour -> [ColouredLine]
		trace' [] _ _ acc _ = acc
		--
		trace' ('[':cs) ts tsStacks acc colour
			= trace' cs ts (ts:tsStacks) acc colour
		-- 
		trace' (']':cs) ts (tss:tsss) acc colour
			= trace' cs tss tsss acc colour
		trace' (c:cs) ts tsStacks acc colour
			| c == 'F'
				= trace' cs newTs tsStacks (newLine:acc) newC
			| otherwise
				= trace' cs newTs tsStacks acc colour
				--
				where
					newTs	= move c ts theta 
					newLine = (tsCoord ts,tsCoord newTs,colour)
					newC	= changeColour colour toC steps
					steps	= (length (filter ( =='F' ) cs))
		changeColour :: Colour -> Colour -> Int -> Colour
		changeColour (fR,fG,fB) (tR,tG,tB) steps 
			= newC
			where
				rDiff = (tR-fR)/(fromIntegral steps)
				gDiff = (tG-fG)/(fromIntegral steps)
				bDiff = (tB-fB)/(fromIntegral steps)
				newC  = (fR+rDiff,fG+gDiff,fB+bDiff)
-- this is working
-- improved trace with changing from a list of colours, i.e. will reach to every colour in the given list
-- >drawLSystemColours tree 6 [red,blue,magenta,green,....] -> http://goo.gl/ykmcE3
traceColours :: String -> Float -> [Colour] -> [ColouredLine]
traceColours commands theta colours = trace' commands ((0,0),90) [] [] colours
	where
		trace' :: 	String -> TurtleState -> [TurtleState] ->
					[ColouredLine] -> [Colour] -> [ColouredLine]
		trace' [] _ _ acc _ = acc
		--
		trace' ('[':cs) ts tsStacks acc colours
			= trace' cs ts (ts:tsStacks) acc colours
		-- 
		trace' (']':cs) ts (tss:tsss) acc colours
			= trace' cs tss tsss acc colours
		trace' (c:cs) ts tsStacks acc allC@(colour:colours)
			| c == 'F'
				= trace' cs newTs tsStacks (newLine:acc) newC
			| otherwise
				= trace' cs newTs tsStacks acc allC
				--
				where
					newTs	= move c ts theta 
					newLine = (tsCoord ts,tsCoord newTs,colour)
					newC	= changeColour allC steps
					steps	= length (filter ( =='F' ) cs)
		changeColour :: [Colour] -> Int -> [Colour]
		changeColour [c] _ = [c]
		changeColour ((fR,fG,fB):(tR,tG,tB):cols) steps 
			| foldl1 (&&) (map (<0.3) (map abs [rDiff,gDiff,bDiff]))
				= (tR,tG,tB):cols
			| otherwise
				= newC:(tR,tG,tB):cols
			where
				rDiff = (tR-fR)
				gDiff = (tG-fG)
				bDiff = (tB-fB)
				divis = (fromIntegral steps)/fromIntegral ((length cols+2))
				newC  = (fR+rDiff/divis,fG+gDiff/divis,fB+bDiff/divis)
--  Some test systems.

cross
  = ( 90
    , "M-M-M-M"
    , [ ('M', "M-M+M+MM-M-M+M")
      , ('+', "+")
      , ('-', "-")
      ]
    )

triangle
  = ( 90
    , "-M"
    , [ ('M', "M+M-M-M+M")
      , ('+', "+")
      , ('-', "-")
      ]
    )

arrowHead
  = ( 60
    , "N"
    , [ ('M', "N+M+N")
      , ('N', "M-N-M")
      , ('+', "+")
      , ('-', "-")
      ]
    )

peanoGosper
  = ( 60
    , "M"
    , [ ('M', "M+N++N-M--MM-N+")
      , ('N', "-M+NN++N+M--M-N")
      , ('+', "+")
      , ('-', "-")
      ]
    )

dragon
  = ( 45
    , "MX"
    , [ ('M', "A")
      , ('X', "+MX--MY+")
      , ('Y', "-MX++MY-")
      , ('A', "A")
      , ('+', "+")
      , ('-', "-")
      ]
    )

snowflake
  = ( 60
    , "M--M--M"
    , [ ('M', "M+M--M+M")
      , ('+', "+")
      , ('-', "-")
      ]
    )

tree
  = ( 45
    , "M"
    , [ ('M', "N[-M][+M][NM]")
      , ('N', "NN")
      , ('[', "[")
      , (']', "]")
      , ('+', "+")
      , ('-', "-")
      ]
    )

bush
  = ( 22.5
    , "X"
    , [ ('X', "M-[[X]+X]+M[+MX]-X")
      , ('M', "MM")
      , ('[', "[")
      , (']', "]")
      , ('+', "+")
      , ('-', "-")
      ]
    )

mapper :: Rules
mapper
  = [ ('M', "F")
    , ('N', "F")
    , ('X', "")
    , ('Y', "")
    , ('A', "")
    , ('[', "[")
    , (']', "]")
    , ('+', "L")
    , ('-', "R")
    ]

lSystem :: System -> Int -> String
lSystem (_, base, rs) n
  = expandOne mapper (expand rs base n)

drawLSystem :: System -> Int -> Colour -> IO ()
drawLSystem system n colour
    = drawLines (trace (lSystem system n) (angle system) colour)


-- they are working :)
-- view the result here: http://goo.gl/uLnTBb
drawLSystem2Colour :: System -> Int -> Colour -> Colour -> IO ()
drawLSystem2Colour system n colour colour'
	= drawLines (trace2Colour (lSystem system n) (angle system) colour colour')

-- view the result here: http://goo.gl/ykmcE3
drawLSystemColours :: System -> Int -> [Colour] -> IO ()
drawLSystemColours system n colour
	= drawLines (traceColours (lSystem system n) (angle system) colour)
