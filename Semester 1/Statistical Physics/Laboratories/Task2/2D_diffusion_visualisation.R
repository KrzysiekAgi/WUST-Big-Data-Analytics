library(ggplot2)
library(dplyr)
library(gganimate)
library(gapminder)
library(shiny)
library(plotly)

dens01 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens01.csv"), header = TRUE, sep = '\t')
dens02 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens02.csv"), header = TRUE, sep = '\t')
dens03 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens03.csv"), header = TRUE, sep = '\t')
dens04 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens04.csv"), header = TRUE, sep = '\t')
dens05 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens05.csv"), header = TRUE, sep = '\t')
dens06 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens06.csv"), header = TRUE, sep = '\t')
dens07 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens07.csv"), header = TRUE, sep = '\t')
dens08 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens08.csv"), header = TRUE, sep = '\t')
dens09 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens09.csv"), header = TRUE, sep = '\t')

dens01$X <- NULL
dens02$X <- NULL
dens03$X <- NULL
dens04$X <- NULL
dens05$X <- NULL
dens06$X <- NULL
dens07$X <- NULL
dens08$X <- NULL
dens09$X <- NULL

d01 <- mean(dens01$coefficient)
d02 <- mean(dens02$coefficient)
d03 <- mean(dens03$coefficient)
d04 <- mean(dens04$coefficient)
d05 <- mean(dens05$coefficient)
d06 <- mean(dens06$coefficient)
d07 <- mean(dens07$coefficient)
d08 <- mean(dens08$coefficient)
d09 <- mean(dens09$coefficient)
coefficients <- c(d01,d02,d03,d04,d05,d06,d07,d08)

densFull <- data.frame(dens01$steps, dens01$coefficient, 
                       dens02$coefficient, dens03$coefficient,
                       dens04$coefficient, dens05$coefficient,
                       dens06$coefficient)
densFull



pl <- ggplot() + 
  geom_point(data = densFull, aes(x=dens01.steps, y=dens01.coefficient, shape = "1")) +
  geom_point(data = densFull, aes(x=dens01.steps, y=dens02.coefficient, shape="2")) +
  geom_point(data = densFull, aes(x=dens01.steps, y=dens05.coefficient, shape="3")) +
  xlim(2,50) +
  ylim(0,0.3) +
  labs(x = "Liczba kroków", y = "Wspólczynnik", title = "2D diffusion") +
  #scale_colour_manual(values=c("red", "blue", "green"), labels=c("0.1","0.2","0.5")) +
  scale_shape_manual(values = c(1,2,6), labels=c("0.1","0.2","0.5"), name = "Wartosc gestosci") +
  theme_bw()

pl

densities <- c(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8)

data <- data.frame(densities,coefficients)
data

plot(C,D, xlab = "Density", ylab = "Coefficient", main = "Coefficient for different densities")



abline(lm(D~C))

ggplot() +
  geom_point(data = data, aes(x=C, y=D)) + 
  theme_bw() + 
  labs(x = "Density",  y = "Coefficient", title = "Coefficient for different densities")