A<-matrix(c(2,1,6,1,3,4,6,4,-2),3,3)
e<-eigen(A)
e$values
e$vectors

B<-matrix(c(1,4,2,5,3,6),2,3)
r<-svd(B)
r$d
r$u
r$v

D<-diag(r$d)
D
C<-r$u%*%diag(r$d)%*%t(r$v)
C
# B==C
# SVD: B=U*D*V^T