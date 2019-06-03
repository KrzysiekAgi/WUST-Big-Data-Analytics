library(jpeg)
x <- readJPEG("gray.jpg")
dim(x)
ncol(x)
nrow(x)

r <- x[,,1]
g <- x[,,2]
b <- x[,,3]

x.r.svd <- svd(r)
x.g.svd <- svd(g)
x.b.svd <- svd(b)

rgb.svds <- list(x.r.svd, x.g.svd, x.b.svd)

for (j in seq.int(3, round(nrow(x), -2), length.out = 8)) {
  a <- sapply(rgb.svds, function(i) {x.compress <- i$u[,1:j] %*% diag(i$d[1:j]) %*% t(i$v[,1:j])}, simplify = 'array')
  writeJPEG(a, paste('C:\\Studia\\WUST-Big-Data-Analytics\\Semester 1\\Advanced Topics in Algebra\\Laboratories\\Lab6\\compr', '_svd_rank_', round(j,0), '.jpg', sep=''))
}