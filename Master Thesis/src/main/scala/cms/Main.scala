package cms

object Main {

  def main(args: Array[String]): Unit = {
    improvedEstimators(2048)
    reweightedEstimators(2048)
  }

  def improvedEstimators(dsSize: Int): Unit = {
    val dsTuple = fillDataStream(dsSize)
    val A = dsTuple._1
    val B = dsTuple._2

    val ks = List(dsSize/512, dsSize/256, dsSize/128, dsSize/64, dsSize/32, dsSize/16, dsSize/8, dsSize/4, dsSize/2)
    for (k <- ks) {
      val sketchA = new ImprovedSingeLineCMS(k)
      val sketchB = new ImprovedSingeLineCMS(k)
      for (a <- A.getData.indices) sketchA.addElement(A.getData(a))
      for (b <- B.getData.indices) sketchB.addElement(B.getData(b))

      println("Intersection for k=" + k)
      println(Math.round(sketchA.intersectionEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))))
      println(A.getData.toSet.intersect(B.getData.toSet).size)
      println("Union for k=" + k)
      println(Math.round(sketchA.unionEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))))
      println(A.getData.toSet.union(B.getData.toSet).size)
      println("=======================")
    }
  }

  def reweightedEstimators(dsSize: Int): Unit = {
    val dsTuple = fillDataStream(dsSize)
    val A = dsTuple._1
    val B = dsTuple._2

    val ks = List(dsSize/512, dsSize/256, dsSize/128, dsSize/64, dsSize/32, dsSize/16, dsSize/8, dsSize/4, dsSize/2)
    for (k <- ks) {
      val sketchA = new ReweightedSingleLineCMS(k)
      val sketchB = new ReweightedSingleLineCMS(k)
      for (a <- A.getData.indices) sketchA.addElement(A.getData(a))
      for (b <- B.getData.indices) sketchB.addElement(B.getData(b))

      println("Intersection for k=" + k)
      println(Math.round(sketchA.intersectionEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))))
      println(A.getData.toSet.intersect(B.getData.toSet).size)
      println("Union for k=" + k)
      println(Math.round(sketchA.unionEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))))
      println(A.getData.toSet.union(B.getData.toSet).size)
      println("=======================")
    }
  }

  def fillDataStream(dsSize: Int): (DataStream, DataStream) = {
    val A: DataStream = new DataStream(dsSize)
    val B: DataStream = new DataStream(dsSize)
    val r = scala.util.Random
    var probability: Float = 0f
    for (_ <- 0 until math.round(dsSize)) {
      probability = r.nextFloat()
      val element = r.nextFloat()//Math.round(r.nextFloat() * 1000.00) / 1000.00f
      if (probability < 1.0f/3.0f) A.addElement(element)
      else if (probability > 2.0f/3.0f) B.addElement(element)
      else {
        A.addElement(element)
        B.addElement(element)
      }
    }
    (A,B)
  }
}
