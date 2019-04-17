AP <- AirPassengers
print(AP)
plot(AP)
class(AP)
start(AP)
end(AP)

raw.data <-AP[1:length(AP)]
class(raw.data)

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

fftAP <- fft(raw.data)
plot.frequency.spectrum(fftAP)

m <- mean(raw.data)

APsubtracted <- round(raw.data - m)

fftAPsubtracted <- fft(APsubtracted)
plot.frequency.spectrum(fftAPsubtracted)

APlogged <- log10(raw.data)
fftAPlogged <- fft(APlogged)
plot.frequency.spectrum(fftAPlogged)