# Gaussian Elimination Method

A<-matrix(c(3,1,2,6,4,4,3,4,7),3,3,TRUE)

rowDivide <- function(A,rowNumber, factor){
  A[rowNumber,] <- A[rowNumber,]/factor
  A
}

rowSubstract <- function(A,rowSubstractFrom,rowToSubstract,factor){
  A[rowSubstractFrom,] <- A[rowSubstractFrom,] - A[rowToSubstract,]*factor
  A
}


for(indexOfColumn in 1:(ncol(A)-1)){
  A<-rowDivide(A,indexOfColumn,A[indexOfColumn,indexOfColumn]) # TODO: fix case: divide by 0
  if (indexOfColumn != ncol(A)){
    for (indexOfRow in (indexOfColumn+1):nrow(A)){
      A<-rowSubstract(A, indexOfRow, indexOfColumn, A[indexOfRow,indexOfColumn])
    }
  }
}
A<-rowDivide(A,nrow(A),A[nrow(A),ncol(A)])
A

