object Lab2 {

  def main(args: Array[String]): Unit = {
    val testList = List.range(1,100)

    val proofMapWorks = map(testList)(Math.pow(_,2))
    println(proofMapWorks.toString())

    val sumOfSquares = testList.filter(_%2==0).foldLeft(0.0)(_ + Math.pow(_,2))
    println(sumOfSquares)

    val amountOfPrimes = testList.foldLeft(0)((acc, n) => if (isPrime(n)) acc+1 else acc+0)
    println(amountOfPrimes)

    println(approx(15))

  }

  def map[A,B](l: List[A])(f: A => B): List[B] =
    l.foldRight(Nil:List[B])((h,t) => f(h) :: t)

  def isPrime(n: Int): Boolean = ! ((2 until n-1) exists (n % _ == 0))

  def approx(n: Int): Double = {
    (0 to n).map(i => 1.0/Lab1.factorial(i)).sum
  }
}
