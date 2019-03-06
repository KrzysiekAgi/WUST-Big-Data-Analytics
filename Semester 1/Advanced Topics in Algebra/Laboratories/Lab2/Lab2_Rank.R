# Rank of Matrix

A<-matrix(c(3,1,2,6,4,4,3,4,7),3,3,TRUE)
numberOfEmptyRows<-0

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
for (i in 1:nrow(A)){
  r<-sum(i)
  if (r==0){
    numberOfEmptyRows <- numberOfEmptyRows+1
  }
}
A

if(numberOfEmptyRows==0){
  return <- min(nrow(A),ncol(A))
}
print(return)

# check if there are no empty rows
# return min(nrow(A),ncol(A))