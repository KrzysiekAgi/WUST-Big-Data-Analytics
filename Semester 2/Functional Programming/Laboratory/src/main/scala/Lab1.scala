import scala.collection.MapView

object Lab1 {

  def main(args: Array[String]): Unit = {
    println(factorial(6))
    println(reverse(List(1,2,3,4,5)))
    println(fibonacci(6))
    println(euler(9))
    println(removeDuplicates(List(1,1,2,2,1)))
    println(listOfDuplicates(List(1,1,2,2,1)))
    println(generatePermutations(List(1,2,3,4,5)))
    println(countOccurrences("aaabbcccaa"))
    powerList(List(1,2)) foreach println
  }

  def factorial(n: Int): Int = {
    @scala.annotation.tailrec
    def loop(n: Int, acc: Int): Int =
      if (n==0) acc else loop(n-1, acc*n)
    loop(n, 1)
  }

  def reverse[A](list: List[A]): List[A] = {
    @scala.annotation.tailrec
    def loop(result: List[A], list: List[A]): List[A] = list match {
        case Nil => result
        case x :: xs => loop(x::result, xs)
      }
    loop(Nil, list)
  }

  def fibonacci(n: Int): Int = {
    @scala.annotation.tailrec
    def loop(n: Int, prev: Int, next: Int): Int = {
      if (n==0) prev else loop(n-1, next, prev+next)
    }
    loop(n, 0, 1)
  }

  def euler(n: Int): Int = {
      @scala.annotation.tailrec
      def gcd(a: Int, b: Int): Int = b match {
        case 0 => a
        case _ => gcd(b, a % b)
      }
    1 to n count(gcd(n, _) == 1)
  }

  def listOfPairs() = ???

  def removeDuplicates[A](list: List[A]): List[A] = {
    @scala.annotation.tailrec
    def loop(rest: List[A], stack: List[A]): List[A] = {
      (rest, stack) match {
        case (Nil, s) => s
        case (h :: t, Nil) => loop(t, List(h))
        case (h :: t, s :: _) => if (h == s) loop(t, stack) else loop(t, h :: stack)
      }
    }
    loop(list, Nil)
  }

  //TODO
  def listOfDuplicates[A](list: List[A]): List[List[A]] = {
    @scala.annotation.tailrec
    def loop(rest: List[A], listOfDuplicates: List[List[A]]): List[List[A]] = {
      rest match {
        case Nil => listOfDuplicates
        case h::t => if (h == t.head) loop(t, listOfDuplicates.appended(List(h, t.head))) else loop(t, listOfDuplicates)
      }
    }
    loop(list, Nil)
  }

  def countOccurrences(text: String): Map[Char, Int] = {
    text.toCharArray.foldLeft[Map[Char, Int]](Map.empty)((m, c) => m + (c -> (m.getOrElse(c, 0) + 1)))
  }

  def reverseCountConsecutiveOccurrences(list: List[(String, Int)]): String = ???

  def generateSubSets[A](list: List[A]): List[Set[A]] = {
    list.toSet.subsets.toList
  }

  def powerList[A](list: List[A]): IndexedSeq[List[A]] = {
    (0 to list.size) flatMap list.combinations
  }

  def personalPowerList[A](list: List[A]): List[List[A]] = {
    @scala.annotation.tailrec
    def power(list: List[A], acc: List[List[A]]): List[List[A]] = list match {
      case Nil => acc
      case h::t => power(t, acc ::: (acc map (h :: _)))
    }
    power(list, Nil::Nil)
  }

  def generatePermutations[A](list: List[A]): List[List[A]] = list match {
    case Nil => List(Nil)
    case x :: xs => generatePermutations(xs) flatMap { perm =>
        (0 to xs.length) map { num =>
          (perm take num) ++ List(x) ++ (perm drop num)
        }
    }
  }

}
