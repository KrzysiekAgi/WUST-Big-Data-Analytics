import scala.math.sqrt
import scala.util.control.Breaks

object GreatestDivisor {
  def main(args: Array[String]){
   try{
     args.foreach(arg => secondGreatestDivisorSlightlyFaster(arg.toInt))
   }catch{
     case exception: NumberFormatException =>
       println("Conversion not possible")
   }
  }

  // greatest divisor of a number is the number itself
  def getDivisor(x: Int): Unit = println(x)

  def secondGreatestDivisor(x: Int): Unit = {
    var div =0
    for(i <- 1 until x) if (x % i == 0) div = i
    println(div)
  }

  def secondGreatestDivisorSlightlyFaster(x: Int): Unit = {
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
    if(div==1) println(1)
    else println(div)
  }
}
