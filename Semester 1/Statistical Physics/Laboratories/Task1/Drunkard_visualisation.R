sigmas <- c(9,22,31,38,44,54,70,84,99)
Ns <- c(100,500,1000,1500,2000,3000,5000,7000,10000)

log_sig <- log10(sigmas)
log_Ns <- log10(Ns)

df <- data.frame(log_Ns,log_sig)

library(ggplot2)

plot(Ns,sigmas)

model <- lm(log_sig~log_Ns)$coeff[2]
model # model=0.5184654 


plot <- ggplot(df, aes(x=log_Ns, y=log_sig)) +
  geom_point() +
  labs(x = "log of number of steps",
       y = "log of sigmas") +
  ggtitle("Drunkard Problem") +
  geom_smooth(method = "lm", color = "red") + 
  theme_bw()

round(model, digits = 3)

plot + annotate("text", x=2.5, y=1.8, label="Slope: ") + annotate("text", x=2.62, y=1.8, label=round(model, digits = 3))