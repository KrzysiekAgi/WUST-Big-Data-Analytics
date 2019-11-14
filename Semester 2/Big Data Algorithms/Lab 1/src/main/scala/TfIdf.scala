import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SQLContext, SparkSession}

// Credits to Arseniy Tashoyan for his article about implementing TF-IDF in Spark, which helped a lot in solving this exercise

object TfIdf {

  val spark: SparkSession = SparkSession.builder()
    .master("local[4]")
    .appName("Big Data Algorithms")
    .config("spark.executor.memory", "70g")
    .config("spark.driver.memory", "50g")
    .config("spark.memory.offHeap.enabled",value = true)
    .config("spark.memory.offHeap.size","16g")
    .getOrCreate()
  val sc: SparkContext = spark.sparkContext
  val sqlContext: SQLContext = spark.sqlContext
  System.setProperty("hadoop.home.dir", "C:\\spark-2.4.4-bin-hadoop2.7")

  def main(args: Array[String]): Unit = {
    prepareTfIdf()
  }

  def addDocId(documents: DataFrame): DataFrame = documents.withColumn("doc_id", monotonically_increasing_id())

  def unfoldDocs(documents: DataFrame): DataFrame = {
    val columns = documents.columns.map(col) :+
      (explode(col("document")) as "token")
    documents.select(columns: _*)
  }

  def addTf(unfoldedDocs: DataFrame): DataFrame = unfoldedDocs
    .groupBy("doc_id", "token")
    .agg(count("document") as "tf")

  def addDf(unfoldedDocs: DataFrame): DataFrame = unfoldedDocs
    .groupBy("token")
    .agg(countDistinct("doc_id") as "df")

  def addIdf(tokensWithDf: DataFrame, docCount: Long): DataFrame = {
    val calcIdfUdf = udf { df: Long => calcIdf(docCount, df) }
    tokensWithDf.withColumn("idf", calcIdfUdf(col("df")))
  }

  def joinTfIdf(tokensWithTf: DataFrame, tokensWithDfIdf: DataFrame): DataFrame = tokensWithTf
    .join(tokensWithDfIdf, Seq("token"), "left")
    .withColumn("tf_idf", col("tf") * col("idf"))

  def prepareTfIdf() : Unit = {
    val textFile = sc.wholeTextFiles("Bible.txt")
    val documentsRDD: RDD[Seq[String]] = textFile
      .flatMap(book => book._2.split("Book"))
      .map(_.split(" ").toSeq)
      .map(book => book.filter(line => line.nonEmpty))
      .map(book => book.map(line => line.replaceAll("[\r\n0-9:.;?,]", "")))
      .filter(book => book.nonEmpty)

    import spark.implicits._
    val documents = documentsRDD.toDF("document")
      .withColumn("doc_id", monotonically_increasing_id())

    val docsWithId = addDocId(documents)
    val unfoldedDocs = unfoldDocs(docsWithId)
    val tokensWithTf = addTf(unfoldedDocs)
    val tokensWithDf = addDf(unfoldedDocs)
    val tokensWithDfIdf = addIdf(tokensWithDf, documents.count())
    val tfIdf = joinTfIdf(tokensWithTf, tokensWithDfIdf)

    val result1 = tfIdf.join(docsWithId, Seq("doc_id"), "left")
    val result = result1.cache()

    for (i <- 1 to 65) {
      result
        .select("doc_id", "token", "tf_idf")
        .where("doc_id = " ++ i.toString)
        .orderBy(desc("tf_idf"))
        .limit(20)
        .rdd.coalesce(1).map(_.toString()).saveAsTextFile(i.toString ++ "tfidf")
    }
  }

  def calcIdf(docCount: Long, df: Long): Double =
    math.log((docCount.toDouble + 1) / (df.toDouble + 1))
}
