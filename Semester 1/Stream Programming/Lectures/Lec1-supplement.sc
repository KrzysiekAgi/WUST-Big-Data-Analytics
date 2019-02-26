// arithmetic
1+2

// simple function
def square(x: Int) = x*x

// function with more lines, take two ints, returns an int
def max(x: Int, y: Int): Int = {
  if (x>y)
    x
  else
    y
}

// call function
max(2,7)

// function with no arguments or return values
def greet() = println("Hello User!")

greet()

// loop
// if doesn't have to be in parenthesis
var i=0
while (i<5){
  if (i!=0)
    println(i)
  i+=1
}