data("iris")
iris
m <- as.matrix(iris)
myData <- m[,1:4]

col1 <- as.numeric(myData[,1])
col2 <- as.numeric(myData[,2])
col3 <- as.numeric(myData[,3])
col4 <- as.numeric(myData[,4])
myData <- cbind(col1, col2, col3, col4)
myDataTest <- cbind(col1, col2, col3, col4)
myDataSub <- cbind(col1, col2, col3, col4)

meanCol1 <- mean(col1)
meanCol2 <- mean(col2)
meanCol3 <- mean(col3)
meanCol4 <- mean(col4)

for (i in 1:150){
  myDataSub[i,1] <- myData[i,1] - meanCol1
  myDataSub[i,2] <- myData[i,2] - meanCol2
  myDataSub[i,3] <- myData[i,3] - meanCol3
  myDataSub[i,4] <- myData[i,4] - meanCol4
}

covariance <- (t(myDataSub)%*%myDataSub)/(length(col1)-1)
covariance

