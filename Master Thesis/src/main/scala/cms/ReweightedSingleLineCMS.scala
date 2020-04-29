package cms
import scala.collection.mutable.ArrayBuffer

class ReweightedSingleLineCMS(override val k: Int) extends SingleLineCMS(k=k) {


  override var sketch: ArrayBuffer[Float] = ArrayBuffer.fill(k)(1.0f)

  override def intersection(improvedSingeLineCMS: ImprovedSingeLineCMS, tauMin: Float): Set[Float] = ???

  override def union(secondSketch: ImprovedSingeLineCMS, tauMin: Float): Set[Float] = ???

  override def intersectionEstimation(secondSketch: ImprovedSingeLineCMS, tauMin: Float): Float = ???

  override def unionEstimation(secondSketch: ImprovedSingeLineCMS, tauMin: Float): Float = ???
}
