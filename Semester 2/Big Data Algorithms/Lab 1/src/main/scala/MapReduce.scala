import org.apache.spark.SparkContext
import org.apache.spark.mllib.rdd.RDDFunctions._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

object MapReduce {

  val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("Big Data Algorithms")
    .getOrCreate()
  val sc: SparkContext = spark.sparkContext
  sc.setLogLevel("ERROR")
  val sqlContext: SQLContext = spark.sqlContext

  def main(args: Array[String]): Unit = {

    val rdd = sc.parallelize(generateData(500, 15), 5)
    import spark.implicits._
    val df = rdd.toDF("Ints").cache()

    import org.apache.spark.sql.functions._
    val distinct: DataFrame = df.distinct().cache()
    df.select(max($"Ints"), min($"Ints"), avg($"Ints"), countDistinct($"Ints")).crossJoin(distinct).show()

    val stopWordsRaw = sc.textFile("stop.txt")
    val stopWords = stopWordsRaw.collect.toSet
    val r = sc.textFile("books/Book1.txt")
      .flatMap( _.split("""[\s,.;:!?]+""") )
      .map(word => word.replaceAll("[,'.:;?]", ""))
      .filter(word => !stopWords.contains(word) && !word.isEmpty)
      .map(line => line.replaceAll("[\r\n0-9:.;?,]", ""))
      .map( _.toLowerCase )

    val genesisWords: Set[String] = r.collect.toSet
    for (w <- genesisWords.toIterator) {
      mostFreq(w, r).foreach{ case (word, (w2, c)) =>
        if (c > 1) println(s"'$word' is followed most frequently by '$w2' for $c times. ")
      }
    }
  }

  def mostFreq(word: String, rdd: RDD[String]): RDD[(String, (String, Int))] =
    rdd
      .sliding(2)
      .collect{ case Array(`word`, w2) => ((word, w2), 1) }
      .reduceByKey( _ + _ )
      .map{ case ((`word`, w2), c) => (word, (w2, c)) }
      .reduceByKey( (acc, x) => if (x._2 > acc._2) (x._1, x._2) else acc )

  def generateData(amount: Int, bound: Int): IndexedSeq[Int] = for(_ <- 1 to amount) yield scala.util.Random.nextInt(bound)
}
