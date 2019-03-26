object TestGeometry {

  def main(args: Array[String]): Unit = {

    println("Rhombus")
    val rhombus = new Rhombus(4,5)
    println(rhombus.calculateField())
    println(rhombus.calculatePerimeter())

    println("Square")
    val square = new Square(6)
    println(square.calculateField())
    println(square.calculatePerimeter())

    println("Rectangle")
    val rectangle = new Rectangle(4,5)
    println(rectangle.calculateField())
    println(rectangle.calculatePerimeter())

    println("Circle")
    val circle = new Circle(3)
    println(circle.calculateField())
    println(circle.calculatePerimeter())

    println("Hexagon")
    val hexagon = new Hexagon(6)
    println(hexagon.calculateField())
    println(hexagon.calculatePerimeter())

    println("Pentagon")
    val pentagon = new Pentagon(4,5)
    println(pentagon.calculateField())
    println(pentagon.calculatePerimeter())
  }
}
