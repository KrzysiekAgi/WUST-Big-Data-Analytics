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

dens05 <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                             "Semester 1","Statistical Physics",
                             "Laboratories", "Task2", "results_dens05.csv"), header = TRUE, sep = '\t')

dens01$X <- NULL
dens02$X <- NULL
dens05$X <- NULL

densFull <- data.frame(dens01$steps, dens01$coefficient, dens02$coefficient, dens05$coefficient)
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
