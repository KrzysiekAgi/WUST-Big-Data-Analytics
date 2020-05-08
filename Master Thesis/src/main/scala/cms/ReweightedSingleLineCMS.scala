package cms
import scala.collection.mutable.ArrayBuffer

class ReweightedSingleLineCMS(val k: Int) extends SingleLineCMS {

  var sketch: ArrayBuffer[Float] = ArrayBuffer.fill(k)(1.0f)

  def getSketch: ArrayBuffer[Float] = sketch

  def addElement(element: Float): this.type = {
    val distinctValues = sketch.distinct
    if (!distinctValues.contains(element) && element < sketch(k - 1)) {
      sketch.prepend(element)
      sketch.remove(k)
      sketch = sketch.sortWith(_ < _)
    }
    this
  }

  override def intersection(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = ???

  override def union(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = ???

  override def intersectionEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = ???

  override def unionEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = ???

  override def difference(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = ???

  override def differenceEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = ???
}
