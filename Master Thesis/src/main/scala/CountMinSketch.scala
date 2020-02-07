import scala.collection.immutable
import scala.util.hashing.MurmurHash3

//TODO: Better hash functions?

/**
 * CountMinSketch
 *
 * @constructor create a new count min sketch
 * @param delta probability of failure
 * @param epsilon approximation factor
 * @param k a positive integer that sets the number of top items counted
 *
 * Example
 *   10**-7, 0.005, 40
 */
class CountMinSketch(delta: Double, epsilon:Double, k:Int) {

  import CountMinSketch._

  if (delta <= 0 || delta >= 1) throw new IllegalArgumentException
  if (epsilon <= 0 || delta >= 1) throw new IllegalArgumentException
  if (k < 1) throw new IllegalArgumentException

  val w: Int = Math.ceil( 10 / epsilon).toInt
  val d: Int = Math.ceil( Math.log(1 / delta)).toInt

  val hashFunctions: immutable.IndexedSeq[Int => Int] = (0 until d).map(_ => generateHashFunction())
  val countMatrix: Array[Array[Int]] = Array.ofDim[Int](d,w)

  /**
   * update
   *
   * Updates count of key in sketch
   *
   * @param key value to update
   * @param increment optional value to increment by
   */
  def update(key: String, increment: Int = 1) {
    for ((hashFunction, row) <- hashFunctions.zipWithIndex) {
      val column = hashFunction(Math.abs(MurmurHash3.stringHash(key)))
      countMatrix(row)(column) += increment
    }
  }

  /**
   * get
   *
   * @param key value to look up
   * @return count of key in sketch
   */
  def get(key: String): Int = {
    val z = hashFunctions.zipWithIndex
    (z foldLeft 1) { (acc, pac) => Math.min(acc, countMatrix(pac._2)(pac._1(Math.abs(MurmurHash3.stringHash(key)))))}
  }

  /**
   * generateHashFunction
   *
   * Returns hash function from a family of pairwise-independent hash functions
   *
   * @return hash function
   */
  def generateHashFunction(): Int => Int = {
    val a = Math.random() * BIG_PRIME - 1
    val b = Math.random() * BIG_PRIME - 1
    x: Int => ((a * x + b) % BIG_PRIME % w).toInt
  }

  /**
   * prints the countMatrix
   */
  def display(): Unit = println(countMatrix.deep.mkString("\n"))
}


object CountMinSketch {
  val BIG_PRIME = 9223372036854775783L
}
