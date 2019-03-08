# Gaussian Elimination Method

#A<-matrix(c(3,1,2,6,4,4,3,4,7),3,3,TRUE)
A<-matrix(c(3,0,2,2,0,-2,0,1,1),3,3,TRUE)


rowDivide <- function(A,rowNumber, factor){
  A[rowNumber,] <- A[rowNumber,]/factor
  A
}

rowSubstract <- function(A,rowSubstractFrom,rowToSubstract,factor){
  A[rowSubstractFrom,] <- A[rowSubstractFrom,] - A[rowToSubstract,]*factor
  A
}

gauss <- function(A){
  #indexOfColumn - i
  #indexOfRow - j
  for(indexOfColumn in 1:(ncol(A)-1)){
    if(A[indexOfColumn,indexOfColumn]!=0){
      A<-rowDivide(A,indexOfColumn,A[indexOfColumn,indexOfColumn]) # TODO: fix case: divide by 0
      if (indexOfColumn != ncol(A)){
        for (indexOfRow in (indexOfColumn+1):nrow(A)){
          A<-rowSubstract(A, indexOfRow, indexOfColumn, A[indexOfRow,indexOfColumn])
        }
      }
    }else{
      for(indexOfRow in (indexOfColumn+1):nrow(A)){
        if(A[indexOfRow,indexOfColumn]!=0){
          #swap
          tmp <- A[indexOfRow,]
          A[indexOfRow,]<-A[indexOfColumn,]
          A[indexOfColumn,]<-tmp
        }else{
          return(NA)
        }
      }
    }
  }
  A<-rowDivide(A,nrow(A),A[nrow(A),ncol(A)])
  A
}

gauss(A)