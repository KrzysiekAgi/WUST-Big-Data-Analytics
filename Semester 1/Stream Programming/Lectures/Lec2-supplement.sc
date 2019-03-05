// Class definition
class Car {
  var name = "Opel"
  var price = 5000
}

// Method definition
def max(x: Int, y: Int): Int = {
  if (x>y)
    x
  else
    y
}

// Object creation
val myCar = new Car

// Class with constructor as arguments
class Car1(var name: String, var price: Int){
  //class body
}

// Class with constructor as arguments with default values
class Car2(var name: String, var price: Int = 5000){
  //class body
}

// Alternative constructor
def this (var name: String) = this(name, 4000)

// Scope of protection
// From www.tutorialspoint.com
package society {
  package professional {
    class Executive {
      private[professional] var workDetails = null
      private[society] var friends = null
      private[this] var secrets = null

      def help(another : Executive) {
        println(another.workDetails)
        println(another.secrets) //ERROR
      }
    }
  }
}

// Inheritance
// From www.javapoint.com
class A{
  var salary1 = 10000
}

class B extends A{
  var salary2 = 20000
}

class C extends B{
  def show(){
    println("salary1 = "+salary1)
    println("salary2 = "+salary2)
  }
}

object MainObject{
  def main(args:Array[String]){{
    var c = new C()
    c.show()

  }
  }
