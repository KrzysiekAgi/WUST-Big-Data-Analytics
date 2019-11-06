
object Lab1 {

  def main(args: Array[String]): Unit = {
    println(factorial(6))
    println(reverse(List(1, 2, 3, 4, 5)))
    println(fibonacci(6))
    println(euler(9))
    print(listOfPairs(List(1, 2, 3)))
    println(removeDuplicates(List(1, 1, 2, 2, 1)))
    println(listOfDuplicates(List(1, 1, 2, 2, 1)))
    println(generatePermutations(List(1, 2, 3, 4, 5)))
    println(countOccurrences("aaabbcccaa"))
    personalPowerList(List(1, 2, 3)) foreach println
    println(reverseCountConsecutiveOccurrences(List(("a", 3), ("b", 2), ("c", 3), ("a", 2))))
  }

  def factorial(n: Int): Int = {
    @scala.annotation.tailrec
    def loop(n: Int, acc: Int): Int =
      if (n == 0) acc else loop(n - 1, acc * n)

    loop(n, 1)
  }

  def reverse[A](list: List[A]): List[A] = {
    @scala.annotation.tailrec
    def loop(result: List[A], list: List[A]): List[A] = list match {
      case Nil => result
      case x :: xs => loop(x :: result, xs)
    }

    loop(Nil, list)
  }

  def fibonacci(n: Int): Int = {
    @scala.annotation.tailrec
    def loop(n: Int, prev: Int, next: Int): Int = {
      if (n == 0) prev else loop(n - 1, next, prev + next)
    }

    loop(n, 0, 1)
  }

  def euler(n: Int): Int = {
    @scala.annotation.tailrec
    def gcd(a: Int, b: Int): Int = b match {
      case 0 => a
      case _ => gcd(b, a % b)
    }

    1 to n count (gcd(n, _) == 1)
  }

  def listOfPairs(list: List[Any]): List[List[Any]] = {
    @scala.annotation.tailrec
    def loop(rest: List[Any], stack: List[List[Any]]): List[List[Any]] = rest match {
      case Nil => stack
      case h :: t => loop(t, stack.appended(List(h, t)))
    }

    loop(list, List(list, Nil))
  }

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

  def listOfDuplicates[A](list: List[A]): List[List[A]] = {
    @scala.annotation.tailrec
    def loop(rest: List[A], listOfDuplicates: List[List[A]]): List[List[A]] = {
      rest match {
        case Nil => listOfDuplicates
        case _ :: Nil => listOfDuplicates
        case h :: t => if (h == t.head) loop(t, listOfDuplicates.appended(List(h, t.head))) else loop(t, listOfDuplicates)
      }
    }

    loop(list, Nil)
  }

  //TODO
  def occurrences(text: String): List[(String, Int)] = {
    def loop(rest: List[String], acc: List[(String, Int)]): List[(String, Int)] = rest match {
      case Nil => acc
      case h :: t => ???
    }

    loop(text.split("").toList, Nil)
  }

  def countOccurrences(text: String): Map[Char, Int] = {
    text.toCharArray.foldLeft[Map[Char, Int]](Map.empty)((m, c) => m + (c -> (m.getOrElse(c, 0) + 1)))
  }

  //TODO
  def reverseCountConsecutiveOccurrences(data: List[(String, Int)]): String = ???

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
      case h :: t => power(t, acc ::: (acc map (h :: _)))
    }

    power(list, Nil :: Nil)
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
