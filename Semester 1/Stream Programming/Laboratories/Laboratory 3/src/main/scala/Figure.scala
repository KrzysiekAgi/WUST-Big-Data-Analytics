abstract class Figure(var side: Float) {

  def calculateField() ={
    side*side
  }

  def calculatePerimeter() = {
    4*side
  }
}
