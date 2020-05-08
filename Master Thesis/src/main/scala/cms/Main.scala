package cms

object Main {
  
  // TODO inne losowanie elementów do strumieni?
  // TODO uśrednione wyniki wielu prób
  def main(args: Array[String]): Unit = {
    val printer = new Printer

    val numberOfElements: Int = (Math.pow(2,19)*1.5f).toInt
    val numberOfTries: Int = 30
    printer.print(Array("First line ->; True intersection;True union;True difference",
      "Next lines -> k;Intersection estimation;Union estimation;Difference estimation"))
    for (i <- 0 until numberOfTries) {
      println("Try no. " + i.toString)
      printer.print(("Try no." + i.toString) +: improvedEstimators(numberOfElements))
    }
  }

  def improvedEstimators(dsSize: Int): Array[String] = {
    val dsTuple = fillDataStream(dsSize)
    val A = dsTuple._1
    val B = dsTuple._2

    val ks = List(A.size/512, A.size/256, A.size/128, A.size/64, A.size/32)//, A.size/16, A.size/8, A.size/4)
    val retVal = new Array[String](ks.size + 1)

    val intersect = A.getData.toSet.intersect(B.getData.toSet).size.toString
    val union = A.getData.toSet.union(B.getData.toSet).size.toString
    val difference = A.getData.toSet.diff(B.getData.toSet).size.toString
    retVal(0) = ";" + intersect + ";" + union + ";" + difference


    val temp = new Array[String](4)
    for (i <- 1 to ks.size) {
      val k = ks(i-1)
      println(k)
      val sketchA = new ImprovedSingeLineCMS(k)
      val sketchB = new ImprovedSingeLineCMS(k)
      for (a <- A.getData.indices) sketchA.addElement(A.getData(a))
      for (b <- B.getData.indices) sketchB.addElement(B.getData(b))

      temp(0) = k.toString
      temp(1) = Math.round(sketchA.intersectionEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))).toString
      temp(2) = Math.round(sketchA.unionEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))).toString
      temp(3) = Math.round(sketchA.differenceEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))).toString
      retVal(i) = temp.mkString(";")
    }
    retVal
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

  def fill3DataStream(dsSize: Int): (DataStream, DataStream, DataStream) = {
    val A: DataStream = new DataStream(dsSize)
    val B: DataStream = new DataStream(dsSize)
    val C: DataStream = new DataStream(dsSize)
    val r = scala.util.Random
    var probability: Float = 0f
    for (_ <- 0 until math.round(dsSize)) {
      probability = r.nextFloat()
      val element = r.nextFloat()//Math.round(r.nextFloat() * 1000.00) / 1000.00f
      if (probability < 0.25f) A.addElement(element)
      else if (0.25f <= probability && probability < 0.5f) B.addElement(element)
      else if (0.5f <= probability && probability < 0.75f) C.addElement(element)
      else {
        A.addElement(element)
        B.addElement(element)
        C.addElement(element)
      }
    }
    (A,B,C)
  }

  def testIntersectionOfThreeSets(numberOfElements: Int): Unit = {
    var streams = fill3DataStream(numberOfElements)
    val A = streams._1
    val B = streams._2
    val C = streams._3
    val ks = List(numberOfElements/512, numberOfElements/256, numberOfElements/128, numberOfElements/64,
      numberOfElements/32, numberOfElements/16, numberOfElements/8, numberOfElements/4)
    for (k <- ks) {
      val sketchA = new ImprovedSingeLineCMS(k)
      val sketchB = new ImprovedSingeLineCMS(k)
      val sketchC = new ImprovedSingeLineCMS(k)
      for (a <- A.getData.indices) sketchA.addElement(A.getData(a))
      for (b <- B.getData.indices) sketchB.addElement(B.getData(b))
      for (c <- C.getData.indices) sketchC.addElement(C.getData(c))
      println("Intersection for k=" + k)
      println(sketchA.intersect3Sets(sketchB, sketchC, tauMin = Math.min(Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)), sketchC.getSketch(k-1))))
      println(A.getData.toSet.intersect(B.getData.toSet).intersect(C.getData.toSet).size)
    }
  }
}
