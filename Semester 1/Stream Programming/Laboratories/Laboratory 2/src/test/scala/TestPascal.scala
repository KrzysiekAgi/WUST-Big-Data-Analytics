object TestPascal {

  def main(args: Array[String]): Unit = {

    val pascal = new PascalTriangleRow(args(0).toInt)

    if (args(0).toInt < 0) {
      println("Wrong row index")
    } else {
      pascal.calculate(args(0).toInt)
      for (i <- 1 until args.length - 1) {
        try {
          val result = pascal.factor(args(i).toInt)
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
