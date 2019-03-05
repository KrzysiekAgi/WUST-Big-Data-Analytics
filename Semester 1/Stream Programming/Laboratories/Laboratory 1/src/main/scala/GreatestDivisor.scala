import scala.math.sqrt
import scala.util.control.Breaks

object GreatestDivisor {
  def main(args: Array[String]) {

    args.foreach(arg =>
      try {
        println(secondGreatestDivisorSlightlyFaster(arg.toInt))
      } catch {
        case exception: NumberFormatException =>
          println("Conversion not possible")
      }
    )
  }
  // greatest divisor of a number is the number itself
  def getDivisor(x: Int): Int = x

  def secondGreatestDivisor(x: Int): Int = {
    var div =0
    for(i <- 1 until x) if (x % i == 0) div = i
    div
  }

  def secondGreatestDivisorSlightlyFaster(x: Int): Int = {
    val loop = new Breaks
    var div=1
    loop.breakable {
      for(i <- 2 until sqrt(x).toInt + 1) {
        if(x % i == 0){
          div=x/i
          loop.break()
        }
      }
    }
    if(div==1) 1
    else div
  }
}
