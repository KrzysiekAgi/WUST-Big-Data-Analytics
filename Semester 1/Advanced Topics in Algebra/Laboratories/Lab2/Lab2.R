# Exercise 1
# 1) vectors
vec1 = c(0,1,2,3)
vec2 = c(4,5,2,7)

vec3 = vec1 + vec2
vec4 = 5*vec1
vec5 = vec1%*%vec2

# 2) matrices
A <- matrix(c(1,2,3,4,5,6,7,8,9), nrow=3, ncol=3, byrow=TRUE)
B <- matrix(c(1,2,3,4,5,6,7,8,9), nrow=3, ncol=3)
C<- A+B #add
D<-A%*%B #dot product
tB<-t(B) #transpose

# 3) elementary operations
A <- matrix(c(1,2,3,4,5,6,7,8,9), nrow=3, ncol=3, byrow=TRUE)
B <- matrix(c(1,2,3,4,5,6,7,8,9), nrow=3, ncol=3)
rowSums(A)
colSums(A)
rowMeans(B)
colMeans(B)
E<-cbind(A,B)
F<-rbind(A,B)

# Exercise 2
A<-matrix(c(3,0,2,2,0,-2,0,1,1),3,3,TRUE)
solve(A)

# Exercise 3
qr(A)$rank

A<-matrix(c(3,0,2,2,0,-2,0,1,1),3,3,TRUE)
b<-A[1,]
b
