package cms

import scala.collection.mutable.ArrayBuffer

class ImprovedSingeLineCMS(val k: Int) extends SingleLineCMS {

  override def getSketch: ArrayBuffer[Float] = sketch

  var sketch: ArrayBuffer[Float] = ArrayBuffer.fill(k)(1.0f)
  def addElement(element: Float): this.type = {
    if (!sketch.contains(element) && element < sketch(k - 1)) {
      sketch.prepend(element)
      sketch.remove(k)
      sketch = swapSort(sketch)
    }
    this
  }

  def swapSort(sketch: ArrayBuffer[Float]): ArrayBuffer[Float] = {
    var temp: Float = 0f
    for (i <- 0 until sketch.size - 1) {
      if (sketch(i) > sketch(i+1)) {
        temp = sketch(i+1)
        sketch(i+1) = sketch(i)
        sketch(i) = temp
      }
      else
        sketch
    }
    sketch
  }

  override def intersection(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = sketch.toSet.intersect(secondSketch.getSketch.toSet)

  override def union(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = {
    val firstSketchDiscarded: Set[Float] = this.sketch.filter(_ < tauMin).toSet
    val secondSketchDiscarded: Set[Float] = secondSketch.getSketch.filter(_ < tauMin).toSet
    firstSketchDiscarded.union(secondSketchDiscarded)
  }

  override def intersectionEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = {
    val intersectedSet: Set[Float] = intersection(secondSketch, tauMin)
    if (intersectedSet.contains(tauMin)) (intersectedSet.size - 1)/tauMin
    else intersectedSet.size/tauMin
  }

  override def unionEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = (union(secondSketch, tauMin).size - 1) / tauMin

  override def difference(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = {
    sketch.toSet.diff(secondSketch.getSketch.toSet)
  }

  def diffEst(secondSketch: SingleLineCMS, tauMin: Float): Float = {
    val diffEst = difference(secondSketch, tauMin)
    if (diffEst.contains(tauMin)) (diffEst.size - 1)/tauMin
    else diffEst.size/tauMin
  }

  def diffEstSimple(secondSketch: SingleLineCMS, tauMin: Float): Float = {
    val diffEst = difference(secondSketch, tauMin)
    (diffEst.size - 1)/tauMin
  }

  override def differenceEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = {
    Math.abs((sketch.size - 1)/tauMin - intersectionEstimation(secondSketch, tauMin))
  }

  def intersect3Sets(secondSketch: SingleLineCMS, thirdSketch: SingleLineCMS, tauMin: Float): Float = {
    val intersectedSet: Set[Float] = sketch.toSet.intersect(secondSketch.getSketch.toSet).intersect(thirdSketch.getSketch.toSet)
    if (intersectedSet.contains(tauMin)) (intersectedSet.size - 1)/tauMin
    else intersectedSet.size/tauMin
  }
}
