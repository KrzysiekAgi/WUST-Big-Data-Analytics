import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext, SparkSession}

object WordCount {

  val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("Big Data Algorithms")
    .getOrCreate()
  val sc: SparkContext = spark.sparkContext
  val sqlContext: SQLContext = spark.sqlContext

  def main(args: Array[String]): Unit = {

    task1(3)

  }

  def task1(aFew : Int) : Unit = {
    val textFile = sc.textFile("books/Book5.txt")
    val stopWordsRaw = sc.textFile("stop.txt")
    val stopWords = stopWordsRaw.collect.toSet
    val counts = textFile
      .flatMap(line => line.split(" "))
      .map(word => word.toLowerCase)
      .map(word => word.replaceAll("[,'.:;?]", ""))
      .filter(word => !stopWords.contains(word) && !word.isEmpty)
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .filter(word => word._2 > 10)
      .map(item => item.swap)
      .sortByKey(ascending = false, 1)

    //Task said - remove a few dozen of words...
    val numbers = counts.take(aFew * 12)
    //numbers.foreach(pair => println(pair))
    val countsRemoved = counts
      .filter(word => !numbers.contains(word))
      .filter(word => word._1 != 1)
    counts foreach println
    counts.saveAsTextFile("Book5")
  }
}
