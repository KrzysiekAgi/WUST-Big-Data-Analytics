import scala.collection.mutable.ArrayBuffer

class PrimeNumber(val n: Int) {

  val primes = new ArrayBuffer[Int]


  def calculatePrimeNumbers(limit: Int) = {
    val composites = new Array[Boolean](limit)
    (2 to math.sqrt(limit).toInt).foreach(i => {
      if (!composites(i))
        (i * i until limit by i).foreach(composites(_) = true)
    })
    for {i <- 2 until limit
         if !composites(i)
    } primes.append(i)
  }

  def number(m: Int): Int = {
    if (0 <= m && m < primes.length) primes(m)
    else -1
  }

}
