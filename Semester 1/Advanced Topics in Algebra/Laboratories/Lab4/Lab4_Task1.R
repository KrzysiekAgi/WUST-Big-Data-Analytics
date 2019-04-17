library(tidyverse)

t <- seq(from = 0.01, to = 5, by = 0.01)
t

df <- data.frame()
someValues <- list()

for (n in 1:20) {
  y <- (1/n)*sin(2*pi*n*t)
  someValues[[n]] <- y
  plot(t, someValues[[n]])
}

plot(t, someValues[[1]])
plot(t, someValues[[1]]+someValues[[2]])

tmp <- someValues[[1]]
for (n in 2:20){
  tmp <- tmp + someValues[[n]]
  plot(t, tmp)
}