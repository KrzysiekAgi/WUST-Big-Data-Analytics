package cms

import scala.collection.mutable.ArrayBuffer

class ImprovedSingeLineCMS(override val k: Int) extends SingleLineCMS(k=k) {

  override def intersection(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Set[Float] = sketch.toSet.intersect(secondSketch.getSketch.toSet)

  override def union(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Set[Float] = {
    val firstSketchDiscarded: Set[Float] = this.sketch.filter(_ < tauMin).toSet
    val secondSketchDiscarded: Set[Float] = secondSketch.getSketch.filter(_ < tauMin).toSet
    firstSketchDiscarded.union(secondSketchDiscarded)
  }

  override def intersectionEstimation(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Float = {
    val intersectedSet: Set[Float] = intersection(secondSketch, tauMin)
    if (intersectedSet.contains(tauMin)) (intersectedSet.size - 1)/tauMin
    else intersectedSet.size/tauMin
  }

  override def unionEstimation(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Float = (union(secondSketch, tauMin).size - 1) / tauMin

  def getSketch: ArrayBuffer[Float] = sketch

  override var sketch: ArrayBuffer[Float] = ArrayBuffer.fill(k)(1.0f)
}
