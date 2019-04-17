s = seq(1:10)
left = sum(s*s)
right = sum(Mod(fft(s))*Mod(fft(s)))/length(s)
left
right