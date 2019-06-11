set.seed(225970)
x <- rnorm(25)
A <- matrix(x, 5,5)
B <- t(A)%*%A
ev <- eigen(B)
max.e <- max(ev$values)
max.e
ev
max.e.v <- B[,1]

x0 <- c(5,7,3,5,9)
L0 <- max(abs(x0))
x <- x0/L0

err <- c()


for (i in 1:20) {
  p <- B%*%x
  L <- max(abs(p))
  x <- p/L
  err <- append(err, max.e - L)
}

difference <- max.e - L
print(max.e.v)
print(x)

plot(err)
