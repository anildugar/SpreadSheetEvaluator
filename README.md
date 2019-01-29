# SpreadSheetEvaluator
It is a Java based Console Application where the output of the cat command is piped into the java Application 
which will read the contents of the input file and parse and evaluate the expressions within the spreadsheet.

It will validate 
  - Whether the first line contains valid row and column values.
  - Whether the number of lines in the file are equal to row*column + 1.
  - It will also detect if there is any invalid cell within the spreadsheet based on total row and column values.
  - It will also detect cyclic dependency within the spreadsheet cell.
