library(tidyverse)


as_tibble(iris)
tibble(x = 1:5, y = 1, z = x ^ 2 + y)
vignette("tibble")

df <- data.frame(abc = 1, xyz = "a")
tib <- tibble(abc = 1, xyz = "a")
df
tib
df$x
df[, "xyz"]
df[, c("abc", "xyz")]
tib$x

typeof(mtcars)
carsdf <- data.frame(mtcars)
carstib <- tibble(mtcars)

carsdf$mpg
carstib$mtcars$ala


###################################################################################################

ggplot(mpg, aes(displ, hwy, colour = class)) + 
  geom_point()

diamonds %>%
  filter(color == "J") %>% 
  ggplot(aes(x = carat, y = price)) +
  geom_point(size = 1) +
  geom_smooth(method = "lm", color = "red") + 
  geom_smooth(method = "loess", se = FALSE) +
  theme_bw() 

##################################################################################################

starwars %>% 
  filter(species == "Human") %>% 
  group_by(homeworld) %>% 
  summarise(avg = mean(height))

##################################################################################################

library("FactoMineR")
library("factoextra")

data("diamonds")
diamonds
diamonds.active <- diamonds[1:100,5:10]
diamonds.active

res.pca <- PCA(diamonds.active, graph = TRUE)
print(res.pca)

eig.val <- get_eigenvalue(res.pca)
eig.val

fviz_eig(res.pca, addlabels = TRUE, ylim = c(0, 50))

var <- get_pca_var(res.pca)

# Coordinates
head(var$coord)
# Cos2: quality on the factore map
head(var$cos2)
# Contributions to the principal components
head(var$contrib)

fviz_pca_var(res.pca, col.var = "black")

library("corrplot")
corrplot(var$cos2, is.corr=FALSE)

# Total cos2 of variables 
fviz_cos2(res.pca, choice = "var", axes = 1:2)

set.seed(123)
res.km <- kmeans(var$coord, centers = 3, nstart = 25)
grp <- as.factor(res.km$cluster)
# Color variables by groups
fviz_pca_var(res.pca, col.var = grp, 
             palette = c("#0073C2FF", "#EFC000FF", "#868686FF"),
             legend.title = "Cluster")


fviz_pca_ind(res.pca)
fviz_pca_ind(res.pca, col.ind = "cos2", 
             gradient.cols = c("#00AFBB", "#E7B800", "#FC4E07"),
             repel = TRUE # Avoid text overlapping (slow if many points)
)

fviz_pca_ind(res.pca, pointsize = "cos2", 
             pointshape = 21, fill = "#E7B800",
             repel = TRUE # Avoid text overlapping (slow if many points)
)

