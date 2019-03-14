import scala.collection.mutable.ArrayBuffer

class PascalTriangleRow(val n: Int) {

  var rowElements = new ArrayBuffer[Int]

  def calculate(n: Int, k: Int = 0): Unit = {
    var element: Int = 1
    for (k <- 0 to n) {
      rowElements.append(element)
      element = (element * ((n - k).toFloat / (k + 1).toFloat)).toInt
    }
    println(rowElements)
  }

  def factor(m: Int): Int = {
    if (0 <= m && m < n) rowElements(m)
    else -1
  }

}
