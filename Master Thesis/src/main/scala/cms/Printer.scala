package cms

import java.io.{File, FileWriter, PrintWriter}

class Printer {

  def print(text: Array[String]): Unit = {
    val pw = new FileWriter("results.csv", true)
    for(line <- text){
      pw.write(line)
      pw.write("\n")
    }
    pw.close()
  }

}
