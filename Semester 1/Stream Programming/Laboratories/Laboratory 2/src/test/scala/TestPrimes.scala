object TestPrimes {

  def main(args: Array[String]): Unit = {

    val primes = new PrimeNumber(args(0).toInt)

    if (args(0).toInt < 0) {
      println("Negative number cannot be prime")
    } else {
      primes.calculatePrimeNumbers(args(0).toInt)
      for (i <- 1 until args.length - 1) {
        try {
          val result = primes.number(args(i).toInt)
          if (result < 0) println("Wrong row index")
          else println(result)
        } catch {
          case exception: NumberFormatException =>
            println("Conversion not possible")
        }
      }
    }
  }
}
