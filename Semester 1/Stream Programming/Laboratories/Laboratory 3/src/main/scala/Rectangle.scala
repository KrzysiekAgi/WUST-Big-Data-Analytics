class Rectangle (side: Float,
                 side2: Float) extends Quadrangle(side, side2) {

  override def calculateField(): Float = side*side2

  override def calculatePerimeter(): Float = 2*side + 2*side2

}
