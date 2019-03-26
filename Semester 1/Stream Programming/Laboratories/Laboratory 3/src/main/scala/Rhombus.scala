class Rhombus (side: Float, var height: Float) extends Quadrangle(side) {

  override def calculateField(): Float = side * height

  override def calculatePerimeter(): Float = 4*side

}
