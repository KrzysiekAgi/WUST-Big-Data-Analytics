dane <- read.csv("http://if.pwr.edu.pl/~mjarema/data1.csv")
dane
plot(dane)
ft <- fft(dane$y)
plot(Mod(ft))

ft1 <- matrix(0,1,72)
ft1 <- ft1[1,]

#biggest values
print(Mod(ft))
#2.026182 , 7
#1.272890 , 13

#1.272890 ,60
#2.026182, 67
#end of biggest values
ft1[7] <- 2.026182
ft1[13] <- 1.272890
ft1[60] <- 1.272890
ft1[67] <- 2.026182
plot(Mod(ft1))

ft1_fft <- fft(ft1, inverse = TRUE)
ft1_fft_divided <- ft1_fft/72
plot(Re(ft1_fft_divided))
plot(Im(ft1_fft_divided))
# is 0.03 nearly 0? 