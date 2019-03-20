library(ggplot2)
library(dplyr)
library(gganimate)
library(gapminder)
library(shiny)
library(plotly)


mtcars
diamonds
data()

sampleData <- read.csv(file.path("C:","Studia","WUST-Big-Data-Analytics",
                                 "Semester 1","Advanced Topics in Algebra",
                                 "Laboratories", "Lab3","sample.csv"), header = FALSE, sep = "\t")
sampleData


#aes(x, y, color, size, alpha)

ggplot(diamonds, aes(x = carat, y = price, color = clarity)) +
  geom_point() +
  labs(x = 'carats', 
       y = 'price') +
  scale_y_log10() +
  theme_bw()


diamonds %>%
  filter(color == "J") %>% 
  ggplot(aes(x = carat, y = price)) +
  geom_point(size = 1, color = "grey") +
  geom_smooth(method = "lm", color = "red") + 
  geom_smooth(method = "loess", se = FALSE) +
  theme_bw()

p <- diamonds %>%
  filter(clarity %in% c("VS1", "VS2", "VVS1")) %>%
  ggplot(aes(x = cut, fill = clarity)) +
  geom_bar(position = "dodge") +
  coord_flip() +
  theme_bw()


ggplot(diamonds, aes(x=clarity)) +
  geom_bar(color = "blue", fill = "darkblue") +
  theme_bw()

ggplotly(p)


library(animation)

saveGIF({
  for(i in 1:100){
    curve(sin(x), from = -5 + (i * 0.05), to = 5 + (i * 0.05), col = "red", ylab = "")
    curve(cos(x), from = -5 + (i * 0.05), to = 5 + (i * 0.05), add = TRUE, col = "blue", ylab = "")
    legend("topright", legend = c("sin(x)", "cos(x)"), fill = c("red", "blue"), bty = "n")
  }
}, interval = 0.1, ani.width = 550, ani.height = 350)



library(gapminder)
p <- ggplot(
  gapminder, 
  aes(x = gdpPercap, y=lifeExp, size = pop, colour = country)) +
  geom_point(show.legend = FALSE, alpha = 0.7) +
  scale_color_viridis_d() +
  scale_size(range = c(2, 12)) +
  scale_x_log10() +
  labs(x = "GDP per capita", y = "Life expectancy")

p + transition_time(year) +
  labs(title = "Year: {frame_time}") +
  #view_follow()
  facet_wrap(~continent) +
  shadow_wake(wake_length = 0.1, size = FALSE, alpha = TRUE)


s <- gapminder %>% 
  filter(country == "China") %>% 
  ggplot(aes(x = gdpPercap, y=lifeExp)) +
  geom_line(show.legend = FALSE, alpha = 0.7) +
  scale_color_viridis_d() +
  scale_size(range = c(2, 12)) +
  scale_x_log10() +
  labs(x = "GDP per capita", y = "Life expectancy")

s + transition_reveal(year)

q <- gapminder %>% 
  filter(country %in% c("China","Afghanistan","Poland")) %>% 
  ggplot(aes(x = gdpPercap, y=lifeExp, group = country, color = factor(country))) +
  geom_line(show.legend = TRUE, alpha = 1, size = 2) +
  scale_color_viridis_d() +
  scale_size(range = c(2, 12)) +
  scale_x_log10() +
  labs(x = "GDP per capita", y = "Life expectancy")

q + transition_time(year) + 
  labs(title = "Year: {frame_time}")
