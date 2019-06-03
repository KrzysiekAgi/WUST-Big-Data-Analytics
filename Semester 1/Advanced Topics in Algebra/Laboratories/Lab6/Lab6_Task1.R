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

beaversData <- scale(beavers, columnMeans, FALSE)

ev <- eigen(cov(beavers))
ev$values

sv <- svd(beaversData)
sv$d

for (i in sv$d){
  print(i*i / (dim(beavers)[1] -1) )
}

