package cms

import scala.collection.mutable.ArrayBuffer

abstract class SingleLineCMS(val k: Int) {

  var sketch: ArrayBuffer[Float]

  def addElement(element: Float): this.type = {
    val distinctValues = sketch.distinct
    if(!distinctValues.contains(element) && element < sketch(k-1)) {
      sketch.prepend(element)
      sketch.remove(k)
      sketch = sketch.sortWith(_<_)
    }
    this
  }

  abstract def intersection(improvedSingeLineCMS: ImprovedSingeLineCMS, tauMin: Float): Set[Float]
  abstract def union(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Set[Float]
  abstract def intersectionEstimation(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Float
  abstract def unionEstimation(secondSketch: ImprovedSingeLineCMS, tauMin: Float = 0.5f): Float
}
