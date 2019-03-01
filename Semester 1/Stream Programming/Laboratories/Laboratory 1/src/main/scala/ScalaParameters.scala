object ScalaParameters {
  def main(args: Array[String]): Unit = {
    if (args.length == 0){
      println("Dude, add arguments")
    }
    args.foreach(arg => println(arg))
  }
}
