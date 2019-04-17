x <- seq(0,12,0.01)
y <- abs((x%%6)-3)

plot(x,y)

plot.frequency.spectrum <-
  function(X.k, xlimits=c(0,length(X.k)/2)) {
    #horizontal axis from zero to Nyquist frequency
    #we plot complex modules of data vs. index numbers
    plot.data  <-cbind(0:(length(X.k)-1), Mod(X.k))
    
    # TODO: why this scaling is necessary?
    plot.data[2:length(X.k),2] <-2*plot.data[2:length(X.k),2] 
    plot(plot.data, t="h", lwd=2, main="", 
         xlab="Frequency (number of cycles in the time range)",
         ylab="Strength", xlim=xlimits, ylim=c(0,max(Mod(plot.data[,2]))))
  }

tt <-seq(from=0, to=10, by=0.01)#time in sec
freq <-2.5     #wholecycles per second
mydata<-sin(2*pi*freq* tt)
ft.mydata <-fft(mydata)
plot.frequency.spectrum(ft.mydata)

yfft <- fft(y)
plot.frequency.spectrum(yfft, xlimits = c(0,30))

noise <- rnorm(100, mean=0, sd=1)
noiseFFT <- fft(noise)
plot.frequency.spectrum(noiseFFT)
