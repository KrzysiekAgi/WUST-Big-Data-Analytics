import scala.math.sqrt

class Hexagon(side: Float) extends Figure(side) {

  override def calculateField(): Float = ((3*sqrt(3)*side*side)/2).toFloat

  override def calculatePerimeter(): Float = 6*side
}
