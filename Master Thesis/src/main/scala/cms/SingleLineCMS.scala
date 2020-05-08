package cms

import scala.collection.mutable.ArrayBuffer

trait SingleLineCMS {

  def addElement(element: Float): this.type

  def getSketch: ArrayBuffer[Float]

  def intersection(secondSketch: SingleLineCMS, tauMin: Float): Set[Float]

  def union(secondSketch: SingleLineCMS, tauMin: Float = 0.5f): Set[Float]

  def intersectionEstimation(secondSketch: SingleLineCMS, tauMin: Float = 0.5f): Float

  def unionEstimation(secondSketch: SingleLineCMS, tauMin: Float = 0.5f): Float

  def difference(secondSketch: SingleLineCMS, tauMin: Float): Set[Float]

  def differenceEstimation(secondSketch: SingleLineCMS, tauMin: Float): Float
}