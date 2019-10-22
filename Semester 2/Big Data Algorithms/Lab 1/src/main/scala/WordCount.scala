import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  val conf: SparkConf = new SparkConf()
    .setMaster("local[*]")
    .setAppName("WordCount")
  val sc: SparkContext = new SparkContext(conf)
  System.setProperty("hadoop.home.dir", "")

  def main(args: Array[String]): Unit = {

    task1(3)
    //task2()

  }

  def task1(aFew : Int) : Unit = {
    val textFile = sc.textFile("Bible.txt")
    val stopWordsRaw = sc.textFile("stop.txt")
    val stopWords = stopWordsRaw.collect.toSet
    val counts = textFile
      .flatMap(line => line.split(" "))
      .map(word => word.toLowerCase)
      .map(word => word.replaceAll("[,'.:;?]", ""))
      .filter(word => !stopWords.contains(word) && !word.isEmpty)
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .filter(word => word._2 > 100)
      .map(item => item.swap)
      .sortByKey(ascending = false, 1)

    //Task said - remove a few dozen of words...
    val numbers = counts.take(aFew * 12)
    numbers.foreach(pair => println(pair))
    val countsRemoved = counts
      .filter(word => !numbers.contains(word))
      .filter(word => word._1 != 1)
    countsRemoved.saveAsTextFile("countsBibleBigger20")
    //countsRemoved.collect()
    //countsRemoved.foreach(word => println(word))
  }

  def task2() : Unit = {

  }
}
