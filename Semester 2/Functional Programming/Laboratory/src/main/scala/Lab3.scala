case class MultiChildrenTree[+A](value: A, children: List[MultiChildrenTree[A]]) {
  def this(value: A) = this(value, List())
  def nodeCount: Int = children.foldLeft(1)(_ + _.nodeCount)
  def height: Int = children.foldLeft(1)(_ max _.nodeCount)
  def contains[B](c: B): Boolean = {
    if (this.value==c) true
    else children.exists(_.value == c)
  }
}

abstract sealed class Tree[+A]() {
  def value: A
  def left: Tree[A]
  def right: Tree[A]
  def size: Int
  def isEmpty: Boolean

  def contains[B >: A](x: B)(implicit f: B => Ordered[B]): Boolean = {
    @scala.annotation.tailrec
    def loop(t: Tree[A], c: Option[A]) : Boolean = {
      if (t.isEmpty) check(c)
      else if (x < t.value) loop(t.left, c)
      else loop(t.right, Some(t.value))
    }
    def check(c: Option[A]): Boolean = c match {
      case Some(y) if x==y => true
      case _ => false
    }
    loop(this, None)
  }

  def height(): Int = if (isEmpty) 0 else 1 + left.height() max right.height()

  def fold[B](n: B)(op: (B, A) => B): B =  {
    def loop(t: Tree[A], a: B): B =
      if (t.isEmpty) a
      else loop(t.right, op(loop(t.left, a), t.value))

    loop(this, n)
  }

  def fail(m: String) = throw new NoSuchElementException(m)
}

case object Leaf extends Tree[Nothing] {
  def value: Nothing = fail("An empty tree.")
  def left: Tree[Nothing] = fail("An empty tree.")
  def right: Tree[Nothing] = fail("An empty tree.")
  def size: Int = 0

  def isEmpty: Boolean = true
}

case class Branch[A](value: A, left: Tree[A], right: Tree[A], size: Int)
                    (implicit f: A => Ordered[A]) extends Tree[A] {
  def isEmpty: Boolean = false
}

object Tree {
  def empty[A]: Tree[A] = Leaf
  def make[A](value: A, l: Tree[A] = Leaf, r: Tree[A] = Leaf)(implicit f: A => Ordered[A]): Tree[A] =
    Branch(value, l, r, l.size + r.size + 1)
  def fromSortedArray[A](a: Array[A])(implicit f: A => Ordered[A]): Tree[A] = {
    def loop(l: Int, r: Int): Tree[A] =
      if (l == r) Tree.empty
      else {
        val p = (l + r) / 2
        Tree.make(a(p), loop(l, p), loop(p + 1, r))
      }

    loop(0, a.length)
  }
}

object MultiChildrenTree {
  def apply[T](value: T) = new MultiChildrenTree(value, List())
  def apply[T](value: T, children: List[MultiChildrenTree[T]]) = new MultiChildrenTree(value, children)
}

object Lab3 {

  def main(args: Array[String]): Unit = {
    val arr = Array(1,2,3,4,5,6,7,8,9)
    val tree = Tree.fromSortedArray(arr)
    println(tree)
    println(tree.contains(3))
    println(tree.contains(0))
    println(tree.height())
    println(tree.size)
    println(tree.fold(0)(_+_))
    
    val mt = MultiChildrenTree('a', List(MultiChildrenTree('f', List(MultiChildrenTree('g'))),
      MultiChildrenTree('c'), MultiChildrenTree('b', List(MultiChildrenTree('d'), MultiChildrenTree('e')))))
    println(mt)
    println(mt.nodeCount)
    println(mt.height)
    println(mt.contains('c'))
    println(mt.contains('z'))
  }
}