class Pentagon (side: Float,
                var distFromCentre: Float) extends Figure(side) {

  override def calculateField(): Float = 5/2 * side * distFromCentre

  override def calculatePerimeter(): Float = 5*side

}
