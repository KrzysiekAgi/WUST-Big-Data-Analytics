import com.github.vickumar1981.stringdistance._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object JaccardSimmilarity {

  def main(args: Array[String]): Unit = {
    val listOfJaccards: ArrayBuffer[(Double, String)] = ArrayBuffer()
    for (i <- 1 to 65) listOfJaccards += readFiles(i, args(0).toInt)
    val z = listOfJaccards.sorted
    z foreach println
  }

  def readFiles(bookNumber: Int, kShing: Int): (Double, String) = {
    val file1: List[String] = Source.fromFile("books/Book" ++ bookNumber.toString ++ ".txt").getLines().toList
    val file2: List[String] = Source.fromFile("books/Book" ++ (bookNumber+1).toString ++ ".txt").getLines().toList
    val kShingles: Int = kShing
    val jacc: Double = prepJaccard(file1, file2, kShingles)
    (jacc, " Book " ++ bookNumber.toString ++ " and book " ++ (bookNumber+1).toString)
  }

  def prepJaccard(f1: List[String], f2: List[String], kShingles: Int): Double = {
    val text1: String = f1 mkString ""
    val text2: String = f2 mkString ""
    val prepped_text1: String = text1
      .map(_.toLower)
      .replaceAll("[\\d:;,.?]", "")
      .trim.replaceAll(" +", " ").take(2000)
    val prepped_text2: String = text2
      .map(_.toLower)
      .replaceAll("[\\d:;,.?]", "")
      .trim.replaceAll(" +", " ").take(2000)

    JaccardScore.score(prepped_text1, prepped_text2, kShingles)
  }
}

