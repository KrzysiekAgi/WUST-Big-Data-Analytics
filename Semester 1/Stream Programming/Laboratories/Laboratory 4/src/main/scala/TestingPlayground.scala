import scala.collection.mutable.Map

object TestingPlayground {

  def main(args: Array[String]): Unit = {

    val streamCars: Stream[Any] = new Car("Audi") #:: new Car("Toyota") #::
                                  new Book("Harry Potter") #:: new Employee ("Jackson Pollock") #::
                                  new Car("Volkswagen") #:: new Book("Bible") #::
                                  new Employee("Pablo Picasso") #:: new Car("Hyundai") #::
                                  new Employee("Vincent van Gogh") #:: Stream.empty

    var values = Map[Any, Int]()
    val misraGries = new MisraGries(2)
    values = misraGries.misraGries(streamCars)
    println("")
  }
}
