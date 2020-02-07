import scala.collection.mutable.ArrayBuffer

object SumCountMinSketches {

//  val spark: SparkSession = SparkSession.builder()
//    .master("local[*]")
//    .appName("Set Operations on Count-Min Sketches")
//    .getOrCreate()
//  val sc: SparkContext = spark.sparkContext
//  sc.setLogLevel("ERROR")
//  val sqlContext: SQLContext = spark.sqlContext

  def main(args: Array[String]): Unit = {
    //    val usersDF = spark.read.format("json").load("src/resources/people.json")
    //    usersDF.show(10)
    //    val cm = usersDF.stat.countMinSketch("age", 0.1, 0.9, 37)
    //    val z = CountMinSketch.create(2,10,1)

    val lorem: Array[String] = "Aut pariatur voluptas culpa culpa possimus labore reiciendis. Laboriosam tenetur fugit voluptatem omnis. Asperiores voluptate sapiente est alias aut reiciendis. Veritatis tempore ut tenetur. Ut esse beatae corrupti.".split(" ")
    val ipsum: Array[String] = "Ad non quia unde qui alias quod culpa voluptatem. Velit soluta amet voluptas. Quod quia fuga repellendus voluptatum modi quo velit voluptatem. Doloribus ratione dolorum aut et illum consequatur perspiciatis.".split(" ")

    val sketch1: CountMinSketch = new CountMinSketch(0.0000001, 0.005, 40)
    for (i <- lorem.indices) sketch1.update(lorem(i))
    println("sketch 1")
    //sketch1.countMatrix(0).foreach(print(_))
    sketch1.display()
    println("\n==============================================================")

    val sketch2: CountMinSketch = new CountMinSketch(0.0000001, 0.005, 40)
    for (i <- ipsum.indices) sketch2.update(ipsum(i))
    println("sketch2")
    //sketch2.countMatrix(0).foreach(print(_))
    sketch2.display()
    println("\n--------------------------------------------------------")


    val sketchMinOfOneRow = for (i <- sketch1.countMatrix(0).indices) yield sketch1.countMatrix(0)(i) min sketch2.countMatrix(0)(i)
    //sketchMinOfOneRow.foreach(print(_))


    val minOfTwoSketches = minTwoArrays(sketch1, sketch2)
    println("min of both sketches")
    println(minOfTwoSketches.deep.mkString("\n"))
    println("\n--------------------------------------------------------")


    println("sketch of both")
    val sketch3: CountMinSketch = new CountMinSketch(0.0000001, 0.005, 40)
    for (i <- lorem.indices) sketch3.update(lorem(i))
    for (i <- ipsum.indices) sketch3.update(ipsum(i))
    sketch3.display()

  }

  def minTwoArrays(countMinSketch1: CountMinSketch, countMinSketch2: CountMinSketch): Array[Array[Int]] = {
    val retVal: ArrayBuffer[Array[Int]] = new ArrayBuffer[Array[Int]]()
    val temp: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    for (i <- countMinSketch1.countMatrix.indices) {
      for (ii <- countMinSketch1.countMatrix(i).indices) temp.append(countMinSketch1.countMatrix(i)(ii) min countMinSketch2.countMatrix(i)(ii))
      retVal.append(temp.toArray)
      temp.clear()
    }
    retVal.toArray
  }
}
