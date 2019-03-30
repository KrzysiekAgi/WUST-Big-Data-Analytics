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


p <- ggplot() +
  geom_point(data = dens05, aes(x=steps, y=coefficient), color = "blue") +
  geom_point(data = dens01, aes(x=steps, y=coefficient), color = "red") +
  geom_point(data = dens02, aes(x=steps, y=coefficient), color = "green") +
  xlim(2,50) +
  ylim(0,0.3) +
  labs(x = "Liczba kroków", y = "Wspólczynnik", title = "2D diffusion") +
  legend()
  theme_bw()

p
  
