package cms

import scala.collection.mutable.ArrayBuffer

class SingeLineCMS(var k: Int) {

  private var sketch: ArrayBuffer[Float] = ArrayBuffer.fill(k)(1.0f)
  var i = 0

  def addElement(element: Float): this.type = {
    i+=1
    val distinctValues = sketch.distinct
    if(!distinctValues.contains(element) && element < sketch(k-1)) {
      sketch.prepend(element)
      sketch.remove(k)
      sketch = sketch.sortWith(_<_)
    }
    this
  }

  private def intersection(secondSketch: SingeLineCMS, tauMin: Float = 0.5f): Set[Float] = sketch.toSet.intersect(secondSketch.getSketch.toSet)

  private def union(secondSketch: SingeLineCMS, tauMin: Float = 0.5f): Set[Float] = {
    val firstSketchDiscarded: Set[Float] = this.sketch.filter(_ < tauMin).toSet
    val secondSketchDiscarded: Set[Float] = secondSketch.getSketch.filter(_ < tauMin).toSet
    firstSketchDiscarded.union(secondSketchDiscarded)
  }

  def intersectionEstimation(secondSketch: SingeLineCMS, tauMin: Float = 0.5f): Float = {
    val intersectedSet: Set[Float] = intersection(secondSketch, tauMin)
    if (intersectedSet.contains(tauMin)) (intersectedSet.size - 1)/tauMin
    else intersectedSet.size/tauMin
  }

  def unionEstimation(secondSketch: SingeLineCMS, tauMin: Float = 0.5f): Float = (union(secondSketch, tauMin).size - 1) / tauMin

  def getSketch: ArrayBuffer[Float] = sketch
}
