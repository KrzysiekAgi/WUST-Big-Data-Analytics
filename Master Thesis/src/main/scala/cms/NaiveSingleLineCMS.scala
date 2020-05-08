package cms
import scala.collection.mutable.ArrayBuffer

class NaiveSingleLineCMS(val k: Int) extends SingleLineCMS {

  var sketch: ArrayBuffer[Float] = ArrayBuffer.fill(k)(1.0f)

  override def getSketch: ArrayBuffer[Float] = sketch

  override def addElement(element: Float): NaiveSingleLineCMS.this.type = ???

  override def intersection(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = ???

  override def union(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = ???

  override def intersectionEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = ???

  override def unionEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = ???

  override def difference(secondSketch: SingleLineCMS, tauMin: Float): Set[Float] = ???

  override def differenceEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float = ???
}
