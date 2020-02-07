import org.apache.spark.SparkContext
import org.apache.spark.graphx.{Edge, Graph, GraphLoader}
import org.apache.spark.sql.{SQLContext, SparkSession}

object Graphs {

  val spark: SparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("Big Data Algorithms")
    .getOrCreate()
  val sc: SparkContext = spark.sparkContext
  sc.setLogLevel("ERROR")
  val sqlContext: SQLContext = spark.sqlContext

  def main(args: Array[String]): Unit = {
    //reverseGraph()
    inOutDegrees()
    //clusteringCoeff()
  }

  def reverseGraph(): Unit = {
    val value = "value"
    val dist = "distance"
    val default = "empty"

    //create vertices
    val vertices = Array((1L,value), (2L, value), (3L, value), (4L, value), (5L, value))
    val vRDD = sc.parallelize(vertices)

    //create edges
    val edges = Array(Edge(1L,3L,dist),Edge(1L,4L,dist),Edge(1L,5L,dist),Edge(2L,1L,dist),Edge(2L,3L,dist),
      Edge(3L,4L,dist),Edge(3L,5L,dist),Edge(4L,1L,dist),Edge(4L,2L,dist),Edge(5L,4L,dist),Edge(5L,5L,dist))
    val eRDD = sc.parallelize(edges)

    //create a graph
    val gr = Graph(vRDD, eRDD, default)
    val secondGraph = gr.reverse
    secondGraph.vertices.collect.foreach(println)
    secondGraph.edges.collect.foreach(println)
  }

  def inOutDegrees(): Unit = {
    import spark.implicits._
    val stanfordGraph = GraphLoader.edgeListFile(sc, "web-Stanford.txt")
    val in = stanfordGraph.inDegrees.toDF("Vertex","In Degree").cache()
    val out = stanfordGraph.outDegrees.toDF("VertexOut","Out Degree").cache()
    in.join(out, $"Vertex"===$"VertexOut").drop($"VertexOut").show(20)

    val amountOfDegrees = stanfordGraph.vertices.count()
    import org.apache.spark.sql.functions._
    in.select(sum($"In Degree")/amountOfDegrees).show(20)
    out.select(sum($"Out Degree")/amountOfDegrees).show(20)
  }

  def clusteringCoeff(): Unit = {
    val stanfordGraph = GraphLoader.edgeListFile(sc, "web-Stanford.txt", canonicalOrientation = true)
    val triCounts = stanfordGraph.triangleCount().vertices
    triCounts.take(20).foreach(println)
    import org.apache.spark.sql.functions.avg
    import spark.implicits._
    triCounts.toDF("Vertex", "Clustering").select(avg($"Clustering")).show()
  }
}
