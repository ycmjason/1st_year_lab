module MP where

import System.Environment

type FileContents = String

type Keyword      = String
type KeywordValue = String
type KeywordDefs  = [(Keyword, KeywordValue)]

separators :: String
separators
  = " \n\t.,:;!\"\'()<>/\\"

line :: String
line ="\n-----\n"

lookUp :: String -> [(String, a)] -> [a]
--lookUp _ []	= []
--lookUp searchFor ((x,y):xs)	| searchFor==x	= y:lookUp searchFor xs
--  							| otherwise		= lookUp searchFor xs
lookUp searchFor list = [y | (x,y)<-list, searchFor==x]

split :: [Char] -> String -> (String, [String])
-- separators = input separators, separators'=return list of separators
split separators [] = ([],[""]) 
split separators (x:xs)
			| elem x separators = (x:separators', "":(y:ys))
			| otherwise			= (separators', (x:y):ys)
					where
						 (separators', (y:ys)) = split separators (xs)

combine :: String -> [String] -> [String]
combine (s:ss) (x:xs)	= [x, [s]]++(combine ss xs)
combine ss []			= [ss]
combine [] xs			= xs

getKeywordDefs :: [String] -> KeywordDefs
getKeywordDefs [] = []
getKeywordDefs (l:ls)= (kw,kwv):getKeywordDefs ls
	where
		(a,b)=split " " l
		(kw:" ":bs)=combine a b
		kwv=concat bs


expand :: FileContents -> FileContents -> FileContents
expand text []	= []
expand text info= finalText
	where
		--obtain the keywordDefs from the info FIleContents
		(_,infoSplited)= split "\n" info
		isExtention = elem "#" infoSplited
		(_,sInfo)= split "#" info
		finalText=if isExtention
						then expandExtention text sInfo
						else expand' text infoSplited

		--define expand'
		expand' :: FileContents -> [String] -> FileContents
		expand' text infoSplited = textFinal
			where
				keywordDefs		= getKeywordDefs infoSplited 
				--splitting text
				(textSeparators, textWords)	= split separators text
				--replace the keywords
				textReplaced	= replaceWord textWords keywordDefs
				--combine textReplace with textSeparators
				textFinal		= concat(combine textSeparators textReplaced)

		--define expandExtention
		expandExtention :: FileContents -> [FileContents] -> FileContents
		expandExtention _ [] = ""
		expandExtention text (info:infos)=textFinal
			where
				(_,infoSp)= split "\n" info
				infoSplited		= [temp | temp<-infoSp, temp/=""]
				keywordDefs		= getKeywordDefs infoSplited 
				--splitting text
				(textSeparators, textWords)	= split separators text
				--replace the keywords
				textReplaced	= replaceWord textWords keywordDefs
				--combine textReplace with textSeparators
				textCur			= concat(combine textSeparators textReplaced)
				textFinal		= textCur ++ line ++ expandExtention text infos
				
		--define replaceWord
		replaceWord :: [String] -> KeywordDefs -> [String]
		replaceWord [] _ = []
		replaceWord ("":words) keywordDefs = "":replaceWord words keywordDefs
		replaceWord ((word@(firstChar:wordLeft)):words) keywordDefs
			| firstChar == '$'= (lookUp word keywordDefs)++replaceWord words keywordDefs
			| otherwise		  = word:(replaceWord words keywordDefs)


main :: IO ()
-- The provided main program which uses your functions to merge a
-- template and source file.
main = do
  args <- getArgs
  main' args

  where
    main' :: [String] -> IO ()
    main' [template, source, output] = do
      t <- readFile template
      i <- readFile source
      writeFile output (expand t i)
    main' _ = putStrLn ("Usage: runghc MP <template> <info> <output>")

