abstract class Quadrangle(side: Float = 1,
                          var side2: Float = 1,
                          var side3: Float = 1,
                          var side4: Float = 1) extends Figure(side) {

  override def calculateField(): Float = super.calculateField()

  override def calculatePerimeter(): Float = {
    side + side2 + side3 + side4
  }
}
