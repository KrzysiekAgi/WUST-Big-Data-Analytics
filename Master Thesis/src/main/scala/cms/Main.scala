package cms

object Main {
  
  def main(args: Array[String]): Unit = {
    val printer = new Printer

    //generateDataStreams(3, 2048)

    val numberOfElements: Int = Math.pow(2,16).toInt
    val numberOfTries: Int = 20

    improvedEstimators(numberOfElements, 0.3333f)

    printer.print(Array("First line ->; True intersection; True union; True difference",
      "Next lines -> k;Intersection estimation; Union estimation; Difference estimation"))
    for (i <- 0 until numberOfTries) {
      println("Try no. " + i.toString)
      printer.print(("Try no." + i.toString) +: improvedEstimators(numberOfElements, 0.333f))
    }
  }

  def differences(dsSize: Int): Array[String] = {
    val dsTuple = fillDataStream(dsSize, 0.333f)
    val A = dsTuple._1
    val B = dsTuple._2

    val ks = List(A.size/512, A.size/256, A.size/128, A.size/64, A.size/32)//, A.size/16, A.size/8, A.size/4)
    val retVal = new Array[String](ks.size + 1)

    val difference = A.getData.toSet.diff(B.getData.toSet).size.toString
    retVal(0) = ";" + difference
    val temp = new Array[String](4)
    for (i <- 1 to ks.size) {
      val k = ks(i-1)
      println(k)
      val sketchA = new ImprovedSingeLineCMS(k)
      val sketchB = new ImprovedSingeLineCMS(k)
      for (a <- A.getData.indices) sketchA.addElement(A.getData(a))
      for (b <- B.getData.indices) sketchB.addElement(B.getData(b))

      temp(0) = k.toString
      temp(1) = Math.round(sketchA.differenceEstimation(sketchB, tauMin = Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))).toString
      temp(2) = Math.round(sketchA.diffEst(sketchB, Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))).toString
      temp(3) = Math.round(sketchA.diffEstSimple(sketchB, Math.min(sketchA.getSketch(k-1), sketchB.getSketch(k-1)))).toString
      retVal(i) = temp.mkString(";")
    }
    retVal
  }

  def improvedEstimators(dsSize: Int, probabilityOfCommon: Float): Array[String] = {
    val dsTuple = fillDataStream(dsSize, probabilityOfCommon)
    val A = dsTuple._1
    val B = dsTuple._2

    val ks = List(A.size/512, A.size/256, A.size/128, A.size/64, A.size/32, A.size/16)//, A.size/8, A.size/4)
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


  def fillDataStream(dsSize: Int, probabilityOfCommon: Float): (DataStream, DataStream) = {
    val A: DataStream = new DataStream(dsSize)
    val B: DataStream = new DataStream(dsSize)
    val r = scala.util.Random
    var probability: Float = 0f
    for (_ <- 0 until math.round(dsSize)) {
      probability = r.nextFloat()
      val element = r.nextFloat()
      if (probability < (1 - probabilityOfCommon)*0.5f) A.addElement(element) //<0.4
      else if (probability > 0.5 + probabilityOfCommon*0.5f) B.addElement(element) // >0.6
      else {
        A.addElement(element)
        B.addElement(element)
      }
    }
    (A,B)
  }

  def generateDataStreams(numberOfDatastreams: Int, dsSize: Int): Array[DataStream] = {
    val datastreams: Array[DataStream] = Array.ofDim(numberOfDatastreams)
    val r = scala.util.Random
    for (i <- datastreams.indices) {
      datastreams(i) = new DataStream(dsSize)
      for (_ <- 0 until datastreams(i).size) {
        //datastreams(i).addElement(Math.round(r.nextFloat() * 1000.00) / 1000.00f)
        datastreams(i).addElement(r.nextFloat())
      }
    }
    datastreams
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
