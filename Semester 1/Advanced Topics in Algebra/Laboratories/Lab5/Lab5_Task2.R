# Prepare data
data()

beaver1C <- beaver1$temp 
beaver1F <- (9/5)*beaver1$temp + 32
beaver2C <- beaver2$temp
beaver2F <- (9/5)*beaver1$temp + 32

beavers <- data.frame(beaver1C[1:100], beaver1F[1:100], beaver2C[1:100], beaver2F[1:100])

# Calculate covariance
columnMeans <- c(mean(beavers$beaver1C.1.100.), 
                 mean(beavers$beaver1F.1.100.), 
                 mean(beavers$beaver2C.1.100.),
                 mean(beavers$beaver2F.1.100.))

subtracted <- scale(beavers, columnMeans, FALSE)

covarianceMatrix <- (t(subtracted)%*%subtracted)/99
covarianceMatrix

# Check with cov()
cov(beavers)

# Calculate eigenvectors of covariance matrix
ev <- eigen(covarianceMatrix)
eigenValue <- ev$values
eigenVectors <- ev$vectors
eigenValue
eigenVectors

# Check with prcomp()
prcomp(cov(beavers))
