import java.lang.Math._

class Circle(radius: Float) extends Figure(radius) {

  override def calculateField(): Float = (PI*radius*radius).toFloat

  override def calculatePerimeter(): Float = (2*PI*radius).toFloat
}
