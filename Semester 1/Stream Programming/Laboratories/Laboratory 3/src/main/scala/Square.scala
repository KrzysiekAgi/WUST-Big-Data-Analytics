class Square (side: Float) extends Quadrangle(side) {

  override def calculateField(): Float = side*side

  override def calculatePerimeter(): Float = 4*side
}
