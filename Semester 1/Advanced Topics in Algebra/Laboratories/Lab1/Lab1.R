# Arithmetic
1^3/2
10^3/2 #500
10^(3/2) #~31.7
5%%2
a<-1
a<-a+10
rm(a)

# Vectors operation
vec1<-c(3,5)
a = sum(vec1)
vec2<-rnorm(10, mean=1, sd=3) # Random number, normal distribution
mean(vec2)
plot(vec2)
help(plot)

# Create matrix (vector of values (columnwise), rows, columns)
A<-matrix(c(2,3,-2,1,2,2),3,2)
is.matrix(A)
is.vector(A)

# Multiplication
c<-3
c*A
D<-matrix(c(2,-2,1,2,3,1),2,3)
A 
D
C<-D%*%A #matrix product
C
C<-A%*%D
C

D<-matrix(c(2,1,3),1,3)
D

# Transposiong a matrix
AT<-t(A)
AT
ATT<-t(AT)
ATT

# Unit and zero vector
U<-matrix(1,3,1)
Z<-matrix(0, nrow = 3, ncol = 1)

# Unit and zero matrix
U<-matrix(1,3,2)
Z<-matrix(0,3,2)

# Diagonal matrix
S<-matrix(c(2,3,-2,1,2,2,4,2,3),3,3)
S
D<-diag(S)
D<-diag(diag(S))

# Identy matrix
I<-matrix(c(1,1,1))

# Inverse
A<-matrix(c(4,4,-2,2,6,2,2,8,4),3,3)
AI<-solve(A)
AI
A%*%AI

# Determinant
C<-matrix(c(2,1,6,1,3,4,6,4,-2),3,3)
d<-det(C)

# Number of rows and columns
dim(C)
nrow(C)
ncol(C)

# Concatenation
B<-matrix(c(1,3,2,1,4,2),3,2)
D<-cbind(A,B)
E<-rbind(A,C)
